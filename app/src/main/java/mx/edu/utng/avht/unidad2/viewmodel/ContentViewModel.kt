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

/**
 * Estado UI para la gestión de contenido.
 *
 * @property isLoading Indica si hay una operación en progreso
 * @property isSuccess Indica si la última operación fue exitosa
 * @property errorMessage Mensaje de error si algo falló, null si no hay error
 * @property selectedImageUri URI de la imagen seleccionada para subir
 */
data class ContentUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val selectedImageUri: Uri? = null
)

/**
 * ViewModel para gestionar todo el contenido de la aplicación.
 *
 * Maneja la carga, creación, edición y eliminación de posts/contenido.
 * También gestiona likes, comentarios y la interacción con Firestore.
 *
 * @param application Contexto de la aplicación para acceder a recursos
 */
class ContentViewModel(application: android.app.Application) : androidx.lifecycle.AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ContentUiState())
    /** Estado UI observable para las pantallas de contenido */
    val uiState: StateFlow<ContentUiState> = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _posts = MutableStateFlow<List<ContentModel>>(emptyList())
    /** Lista observable de todos los posts ordenados por fecha */
    val posts: StateFlow<List<ContentModel>> = _posts.asStateFlow()

    init {
        fetchPosts()
    }

    /**
     * Carga todos los posts desde Firestore en tiempo real.
     *
     * Establece un listener que actualiza la lista de posts automáticamente
     * cuando hay cambios en la base de datos.
     */
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

    /**
     * Actualiza la imagen seleccionada en el estado UI.
     *
     * @param uri URI de la imagen seleccionada o null para limpiar la selección
     */
    fun onImageSelected(uri: Uri?) {
        _uiState.value = _uiState.value.copy(selectedImageUri = uri)
    }

    /**
     * Comprime y convierte una imagen a formato Base64.
     *
     * Redimensiona la imagen a máximo 800px y comprime a 60% de calidad
     * para optimizar el tamaño antes de guardarla en Firestore.
     *
     * @param uri URI de la imagen a comprimir
     * @return String en formato Base64 o null si hay error
     */
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

    /**
     * Sube nuevo contenido a Firestore.
     *
     * Procesa y sube la imagen seleccionada (si existe), obtiene información
     * del usuario actual, y crea un nuevo documento de contenido en Firestore.
     *
     * @param title Título del contenido
     * @param description Descripción del contenido
     * @param lat Latitud de la ubicación
     * @param lng Longitud de la ubicación
     */
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

    /**
     * Alterna el estado de "me gusta" para un post.
     *
     * Si el usuario ya le dio like, lo quita. Si no, lo agrega.
     * Actualiza el contador de likes y la lista de usuarios que dieron like.
     *
     * @param content Modelo del contenido a dar/quitar like
     */
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

    /**
     * Agrega un comentario a un post.
     *
     * Obtiene el username del usuario actual y crea un nuevo comentario
     * en la subcolección de comentarios del post.
     *
     * @param contentId ID del post donde agregar el comentario
     * @param text Texto del comentario
     */
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

    /**
     * Obtiene los comentarios de un post en tiempo real.
     *
     * Establece un listener que devuelve los últimos 3 comentarios
     * ordenados por fecha descendente.
     *
     * @param contentId ID del post del que obtener comentarios
     * @param callback Función que recibe la lista de comentarios
     */
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

    /**
     * Obtiene todos los posts de un usuario específico.
     *
     * Establece un listener que devuelve posts del usuario ordenados
     * por fecha descendente.
     *
     * @param userId ID del usuario del que obtener posts
     * @param callback Función que recibe la lista de posts del usuario
     */
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

    /**
     * Elimina un post de Firestore.
     *
     * @param postId ID del post a eliminar
     * @param onSuccess Callback ejecutado si la eliminación es exitosa
     * @param onError Callback ejecutado si hay error, recibe el mensaje de error
     */
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
