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

/**
 * Estado UI para las pantallas de login y registro.
 *
 * Contiene todos los datos necesarios para renderizar las pantallas de autenticación
 * y manejar los estados de carga y error.
 *
 * @property email Email ingresado por el usuario
 * @property password Contraseña ingresada por el usuario
 * @property isLoading Estado de carga durante operaciones asíncronas
 * @property errorMessage Mensaje de error a mostrar al usuario, null si no hay error
 * @property isLoginSuccessful Indica si el login/registro fue exitoso
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

/**
 * ViewModel para gestionar la autenticación de usuarios (login y registro).
 *
 * Maneja toda la lógica de autenticación con Firebase Authentication y
 * almacena los datos de usuario en Firestore. Expone un estado UI reactivo
 * que las pantallas pueden observar.
 */
class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(LoginUiState())
    
    /** Estado UI observable para las pantallas de login/registro */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el email en el estado UI.
     *
     * @param newEmail Nuevo valor del email ingresado
     */
    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    /**
     * Actualiza la contraseña en el estado UI.
     *
     * @param newPassword Nuevo valor de la contraseña ingresada
     */
    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    /**
     * Registra un nuevo usuario en Firebase Authentication y Firestore.
     *
     * Valida que los campos estén completos, crea el usuario en Authentication,
     * y guarda información adicional en Firestore. Actualiza el estado UI
     * con el resultado de la operación.
     */
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

    /**
     * Autentica un usuario existente con email y contraseña.
     *
     * Valida que los campos estén completos y realiza el login en
     * Firebase Authentication. Actualiza el estado UI con el resultado.
     */
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

    /**
     * Guarda la información básica del usuario en Firestore.
     *
     * Crea un documento en la colección "usuarios" con el email,
     * username (derivado del email), y timestamp de creación.
     *
     * @param userId ID del usuario autenticado en Firebase Auth
     * @param email Email del usuario
     */
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

/**
 * ViewModel para gestionar el perfil del usuario.
 *
 * Carga y actualiza la información del perfil del usuario actual,
 * incluyendo username, email, bio, y foto de perfil. Los datos se
 * almacenan y sincronizan con Firestore.
 */
class PerfilViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _username = MutableStateFlow("Cargando...")
    /** Nombre de usuario o email del perfil actual */
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    /** Email del usuario actual */
    val email: StateFlow<String> = _email.asStateFlow()

    private val _bio = MutableStateFlow("")
    /** Biografía o descripción del usuario */
    val bio: StateFlow<String> = _bio.asStateFlow()

    private val _profilePicture = MutableStateFlow("")
    /** URL o cadena Base64 de la foto de perfil */
    val profilePicture: StateFlow<String> = _profilePicture.asStateFlow()

    init {
        loadUserProfile()
    }

    /**
     * Carga el perfil del usuario actual desde Firestore.
     *
     * Obtiene los datos del documento del usuario en la colección "usuarios"
     * y actualiza los StateFlows correspondientes. Si no hay usuario autenticado
     * o falla la carga, establece valores por defecto.
     */
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

    /**
     * Actualiza la biografía del usuario en Firestore.
     *
     * @param newBio Nueva biografía a guardar
     */
    fun updateBio(newBio: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .update("bio", newBio)
            .addOnSuccessListener {
                _bio.value = newBio
            }
    }

    /**
     * Actualiza la foto de perfil del usuario.
     *
     * Comprime y convierte la imagen a Base64 antes de guardarla en Firestore.
     * La imagen se redimensiona a un máximo de 400px para optimizar el almacenamiento.
     *
     * @param imageUri URI de la imagen seleccionada
     * @param context Context necesario para acceder al ContentResolver
     */
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
