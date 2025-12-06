package mx.edu.utng.avht.unidad2.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utng.avht.unidad2.data.ContentModel
import java.util.UUID
import kotlinx.coroutines.tasks.await

data class ContentUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val selectedImageUri: Uri? = null
)

class ContentViewModel(application: android.app.Application) : androidx.lifecycle.AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ContentUiState())
    val uiState: StateFlow<ContentUiState> = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    // private val storage = FirebaseStorage.getInstance() // Removed

    private val _posts = MutableStateFlow<List<ContentModel>>(emptyList())
    val posts: StateFlow<List<ContentModel>> = _posts.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            firestore.collection("contents")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        _uiState.value = _uiState.value.copy(errorMessage = e.message)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        val postsList = snapshot.toObjects(ContentModel::class.java)
                        _posts.value = postsList
                    }
                }
        }
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.value = _uiState.value.copy(selectedImageUri = uri)
    }

    private fun compressAndEncodeImage(uri: Uri): String? {
        return try {
            val context = getApplication<android.app.Application>().applicationContext
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (originalBitmap == null) return null

            // Resize
            val maxDimension = 800
            val ratio = Math.min(
                maxDimension.toDouble() / originalBitmap.width,
                maxDimension.toDouble() / originalBitmap.height
            )
            val width = (originalBitmap.width * ratio).toInt()
            val height = (originalBitmap.height * ratio).toInt()
            val resizedBitmap = android.graphics.Bitmap.createScaledBitmap(originalBitmap, width, height, true)

            // Compress
            val outputStream = java.io.ByteArrayOutputStream()
            resizedBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 60, outputStream)
            val byteArray = outputStream.toByteArray()

            // Encode
            val base64String = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
            "data:image/jpeg;base64,$base64String"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uploadContent(title: String, description: String, lat: Double, lng: Double) {
        val user = auth.currentUser
        if (user == null) {
            _uiState.value = _uiState.value.copy(errorMessage = "Usuario no autenticado")
            return
        }

        if (title.isBlank() || description.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Título y descripción son requeridos")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val imageUri = _uiState.value.selectedImageUri
                var imageUrl = ""

                if (imageUri != null) {
                    // Encode to Base64
                    val base64Image = kotlinx.coroutines.Dispatchers.IO.let {
                        kotlinx.coroutines.withContext(it) {
                            compressAndEncodeImage(imageUri)
                        }
                    }
                    
                    if (base64Image != null) {
                        imageUrl = base64Image
                    } else {
                        throw Exception("Error al procesar la imagen")
                    }
                }

                // 1. Obtener el username y foto de perfil de Firestore
                val userDoc = firestore.collection("usuarios").document(user.uid).get().await()
                val username = userDoc.getString("username") ?: user.email ?: "Usuario"
                val userProfilePic = userDoc.getString("profilePicture") ?: ""

                val content = ContentModel(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    lat = lat,
                    lng = lng,
                    userId = user.uid,
                    userName = username,
                    userProfilePicture = userProfilePic,
                    timestamp = System.currentTimeMillis()
                )

                firestore.collection("contents").document(content.id).set(content).await()

                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
                android.util.Log.e("ContentViewModel", "Error subiendo contenido", e)
            }
        }
    }

    fun toggleLike(content: ContentModel) {
        val user = auth.currentUser ?: return
        val userId = user.uid
        val isLiked = content.likedBy.contains(userId)

        val docRef = firestore.collection("contents").document(content.id)

        if (isLiked) {
            docRef.update(
                "likesCount", com.google.firebase.firestore.FieldValue.increment(-1),
                "likedBy", com.google.firebase.firestore.FieldValue.arrayRemove(userId)
            )
        } else {
            docRef.update(
                "likesCount", com.google.firebase.firestore.FieldValue.increment(1),
                "likedBy", com.google.firebase.firestore.FieldValue.arrayUnion(userId)
            )
        }
    }

    fun addComment(contentId: String, text: String) {
        val user = auth.currentUser ?: return
        if (text.isBlank()) return

        viewModelScope.launch {
            try {
                // Get username
                val userDoc = firestore.collection("usuarios").document(user.uid).get().await()
                val username = userDoc.getString("username") ?: user.email ?: "Usuario"

                val comment = mx.edu.utng.avht.unidad2.data.CommentModel(
                    id = UUID.randomUUID().toString(),
                    userId = user.uid,
                    userName = username,
                    text = text,
                    timestamp = System.currentTimeMillis()
                )

                val contentRef = firestore.collection("contents").document(contentId)
                
                contentRef.collection("comments").document(comment.id).set(comment).await()
                contentRef.update("commentsCount", com.google.firebase.firestore.FieldValue.increment(1))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun fetchCommentsForPost(contentId: String, callback: (List<mx.edu.utng.avht.unidad2.data.CommentModel>) -> Unit) {
        firestore.collection("contents")
            .document(contentId)
            .collection("comments")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(3)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    val comments = snapshot.toObjects(mx.edu.utng.avht.unidad2.data.CommentModel::class.java)
                    callback(comments)
                }
            }
    }

    fun fetchUserPosts(userId: String, callback: (List<ContentModel>) -> Unit) {
        firestore.collection("contents")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    android.util.Log.e("ContentViewModel", "Error fetching user posts", e)
                    callback(emptyList())
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    val userPosts = snapshot.toObjects(ContentModel::class.java)
                        .sortedByDescending { it.timestamp } // Ordenar localmente
                    callback(userPosts)
                }
            }
    }

    fun deletePost(postId: String, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        firestore.collection("contents")
            .document(postId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Error desconocido")
            }
    }
}
