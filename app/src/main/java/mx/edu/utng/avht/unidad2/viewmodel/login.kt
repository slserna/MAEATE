package mx.edu.utng.avht.unidad2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ------------------------------------------------------
// UI STATE PARA LOGIN / REGISTRO
// ------------------------------------------------------
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // -----------------------------
    // Actualización de campos
    // -----------------------------
    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    // -----------------------------
    // REGISTRO DE USUARIO
    // -----------------------------
    fun onRegisterClick() {
        val email = uiState.value.email
        val password = uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Llena todos los campos")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                // Guardamos el usuario
                saveUserToFirestore(userId, email)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccessful = true
                )
            }
            .addOnFailureListener { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message
                )
            }
    }

    // -----------------------------
    // LOGIN DE USUARIO
    // -----------------------------
    fun onLoginClick() {
        val email = uiState.value.email
        val password = uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Llena todos los campos")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true)

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccessful = true
                )
            }
            .addOnFailureListener { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message
                )
            }
    }

    // -----------------------------
    // GUARDAR EN FIRESTORE
    // -----------------------------
    private fun saveUserToFirestore(userId: String, email: String) {

        val userData = mapOf(
            "email" to email,
            "username" to email.substringBefore("@"),
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("usuarios")
            .document(userId)
            .set(userData)
    }
}

// ------------------------------------------------------
// VIEWMODEL DEL PERFIL
// ------------------------------------------------------

class PerfilViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _username = MutableStateFlow("Cargando...")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio.asStateFlow()

    private val _profilePicture = MutableStateFlow("")
    val profilePicture: StateFlow<String> = _profilePicture.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        if (user == null) {
            _username.value = "Usuario"
            _email.value = ""
            _bio.value = ""
            _profilePicture.value = ""
            return
        }

        val userId = user.uid

        db.collection("usuarios")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                _username.value = doc.getString("username") ?: doc.getString("email") ?: "Usuario"
                _email.value = doc.getString("email") ?: ""
                _bio.value = doc.getString("bio") ?: ""
                _profilePicture.value = doc.getString("profilePicture") ?: ""
            }
            .addOnFailureListener {
                _username.value = "Usuario"
                _email.value = ""
                _bio.value = ""
                _profilePicture.value = ""
            }
    }

    fun updateBio(newBio: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .update("bio", newBio)
            .addOnSuccessListener {
                _bio.value = newBio
            }
    }

    fun updateProfilePicture(imageUri: android.net.Uri, context: android.content.Context) {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val base64Image = withContext(kotlinx.coroutines.Dispatchers.IO) {
                    val inputStream = context.contentResolver.openInputStream(imageUri)
                    val originalBitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    if (originalBitmap == null) return@withContext null

                    val maxDimension = 400
                    val ratio = kotlin.math.min(
                        maxDimension.toDouble() / originalBitmap.width,
                        maxDimension.toDouble() / originalBitmap.height
                    )
                    val width = (originalBitmap.width * ratio).toInt()
                    val height = (originalBitmap.height * ratio).toInt()
                    val resizedBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, width, height, true)

                    val outputStream = java.io.ByteArrayOutputStream()
                    resizedBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 70, outputStream)
                    val byteArray = outputStream.toByteArray()

                    "data:image/jpeg;base64," + android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
                }

                if (base64Image != null) {
                    db.collection("usuarios")
                        .document(userId)
                        .update("profilePicture", base64Image)
                        .addOnSuccessListener {
                            _profilePicture.value = base64Image
                        }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
