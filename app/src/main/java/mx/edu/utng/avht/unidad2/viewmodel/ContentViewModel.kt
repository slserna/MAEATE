package mx.edu.utng.avht.unidad2.viewmodel

import android.net.Uri
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
 * -----------------------------
 * MODELO DE ESTADO DE LA UI
 * -----------------------------
 *
 * Clase que representa el estado actual de la interfaz relacionada
 * con la gestión de contenido (crear posts).
 *
 * Se usa para comunicar el estado del ViewModel a las pantallas Compose.
 */
data class ContentUiState(
    /** Indica si hay una operación en proceso (ej. subir contenido) */
    val isLoading: Boolean = false,

    /** Indica si la operación se completó correctamente */
    val isSuccess: Boolean = false,

    /** Mensaje de error en caso de falla */
    val errorMessage: String? = null,

    /** Imagen seleccionada desde galería o cámara */
    val selectedImageUri: Uri? = null
)

/**
 * ----------------------------------------------------
 * VIEWMODEL PRINCIPAL PARA LA GESTIÓN DE CONTENIDO
 * ----------------------------------------------------
 *
 * Esta clase centraliza toda la lógica relacionada con:
 * - Publicaciones (posts)
 * - Likes
 * - Comentarios
 * - Comunicación con Firestore
 * - Manejo del estado de la UI
 *
 * Sigue la arquitectura MVVM:
 * View (Compose) → ViewModel → Firebase
 *
 * Extiende AndroidViewModel para poder usar el contexto
 * de la aplicación (necesario para procesar imágenes).
 */
class ContentViewModel(application: android.app.Application)
    : androidx.lifecycle.AndroidViewModel(application) {

    /** Estado interno mutable del UI */
    private val _uiState = MutableStateFlow(ContentUiState())

    /** Estado público e inmutable que observan las pantallas */
    val uiState: StateFlow<ContentUiState> = _uiState.asStateFlow()

    /** Instancia de autenticación Firebase */
    private val auth = FirebaseAuth.getInstance()

    /** Instancia de base de datos Firestore */
    private val firestore = FirebaseFirestore.getInstance()

    /** Lista interna mutable de publicaciones */
    private val _posts = MutableStateFlow<List<ContentModel>>(emptyList())

    /** Lista pública observable de publicaciones */
    val posts: StateFlow<List<ContentModel>> = _posts.asStateFlow()

    /**
     * Se ejecuta cuando el ViewModel se crea.
     * Inicia la escucha de publicaciones en Firestore.
     */
    init {
        fetchPosts()
    }

    /**
     * ------------------------------------------------
     * OBTENER POSTS EN TIEMPO REAL
     * ------------------------------------------------
     *
     * Escucha la colección "contents" en Firestore.
     * Cada cambio actualiza automáticamente la UI.
     */
    private fun fetchPosts() {
        viewModelScope.launch {
            firestore.collection("contents")
                .orderBy(
                    "timestamp",
                    com.google.firebase.firestore.Query.Direction.DESCENDING
                )
                .addSnapshotListener { snapshot, e ->

                    // Si ocurre un error, se muestra en la UI
                    if (e != null) {
                        _uiState.value =
                            _uiState.value.copy(errorMessage = e.message)
                        return@addSnapshotListener
                    }

                    // Convierte los documentos a objetos ContentModel
                    if (snapshot != null) {
                        val postsList =
                            snapshot.toObjects(ContentModel::class.java)
                        _posts.value = postsList
                    }
                }
        }
    }

    /**
     * ----------------------------------------------
     * ACTUALIZA LA IMAGEN SELECCIONADA
     * ----------------------------------------------
     *
     * Se usa cuando el usuario selecciona una imagen
     * desde la galería o la cámara.
     */
    fun onImageSelected(uri: Uri?) {
        _uiState.value =
            _uiState.value.copy(selectedImageUri = uri)
    }

    /**
     * -------------------------------------------------
     * COMPRIMIR Y CONVERTIR IMAGEN A BASE64
     * -------------------------------------------------
     *
     * Reduce el tamaño de la imagen y la convierte a texto
     * Base64 para poder guardarla directamente en Firestore.
     */
    private fun compressAndEncodeImage(uri: Uri): String? {
        return try {
            val context =
                getApplication<android.app.Application>().applicationContext

            val inputStream =
                context.contentResolver.openInputStream(uri)

            val originalBitmap =
                android.graphics.BitmapFactory.decodeStream(inputStream)

            inputStream?.close()

            if (originalBitmap == null) return null

            // Redimensionar imagen
            val maxDimension = 800
            val ratio = Math.min(
                maxDimension.toDouble() / originalBitmap.width,
                maxDimension.toDouble() / originalBitmap.height
            )

            val width = (originalBitmap.width * ratio).toInt()
            val height = (originalBitmap.height * ratio).toInt()

            val resizedBitmap =
                android.graphics.Bitmap.createScaledBitmap(
                    originalBitmap, width, height, true
                )

            // Comprimir imagen
            val outputStream = java.io.ByteArrayOutputStream()
            resizedBitmap.compress(
                android.graphics.Bitmap.CompressFormat.JPEG,
                60,
                outputStream
            )

            // Codificar en Base64
            val byteArray = outputStream.toByteArray()
            val base64String =
                android.util.Base64.encodeToString(
                    byteArray,
                    android.util.Base64.DEFAULT
                )

            "data:image/jpeg;base64,$base64String"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * -------------------------------------------------
     * SUBIR NUEVO CONTENIDO
     * -------------------------------------------------
     *
     * Valida datos, procesa imagen, obtiene datos del usuario
     * y guarda un nuevo documento en Firestore.
     */
    fun uploadContent(
        title: String,
        description: String,
        lat: Double,
        lng: Double
    ) {
        val user = auth.currentUser

        // Validaciones
        if (user == null) {
            _uiState.value =
                _uiState.value.copy(errorMessage = "Usuario no autenticado")
            return
        }

        if (title.isBlank() || description.isBlank()) {
            _uiState.value =
                _uiState.value.copy(
                    errorMessage = "Título y descripción son requeridos"
                )
            return
        }

        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val imageUri = _uiState.value.selectedImageUri
                var imageUrl = ""

                // Procesar imagen si existe
                if (imageUri != null) {
                    val base64Image =
                        kotlinx.coroutines.withContext(
                            kotlinx.coroutines.Dispatchers.IO
                        ) {
                            compressAndEncodeImage(imageUri)
                        }

                    imageUrl =
                        base64Image ?: throw Exception(
                            "Error al procesar la imagen"
                        )
                }

                // Obtener datos del usuario
                val userDoc =
                    firestore.collection("usuarios")
                        .document(user.uid)
                        .get()
                        .await()

                val username =
                    userDoc.getString("username")
                        ?: user.email ?: "Usuario"

                val profilePic =
                    userDoc.getString("profilePicture") ?: ""

                // Crear objeto de contenido
                val content = ContentModel(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    lat = lat,
                    lng = lng,
                    userId = user.uid,
                    userName = username,
                    userProfilePicture = profilePic,
                    timestamp = System.currentTimeMillis()
                )

                // Guardar en Firestore
                firestore.collection("contents")
                    .document(content.id)
                    .set(content)
                    .await()

                _uiState.value =
                    _uiState.value.copy(isLoading = false, isSuccess = true)

            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
            }
        }
    }

    /**
     * -------------------------------------------------
     * LIKE / UNLIKE
     * -------------------------------------------------
     *
     * Alterna el like del usuario sobre un post.
     */
    fun toggleLike(content: ContentModel) {
        val user = auth.currentUser ?: return
        val userId = user.uid

        val isLiked = content.likedBy.contains(userId)

        val docRef =
            firestore.collection("contents").document(content.id)

        if (isLiked) {
            docRef.update(
                "likesCount",
                com.google.firebase.firestore.FieldValue.increment(-1),
                "likedBy",
                com.google.firebase.firestore.FieldValue.arrayRemove(userId)
            )
        } else {
            docRef.update(
                "likesCount",
                com.google.firebase.firestore.FieldValue.increment(1),
                "likedBy",
                com.google.firebase.firestore.FieldValue.arrayUnion(userId)
            )
        }
    }

    /**
     * -------------------------------------------------
     * AGREGAR COMENTARIO
     * -------------------------------------------------
     */
    fun addComment(contentId: String, text: String) {
        val user = auth.currentUser ?: return
        if (text.isBlank()) return

        viewModelScope.launch {
            val userDoc =
                firestore.collection("usuarios")
                    .document(user.uid)
                    .get()
                    .await()

            val username =
                userDoc.getString("username")
                    ?: user.email ?: "Usuario"

            val comment =
                mx.edu.utng.avht.unidad2.data.CommentModel(
                    id = UUID.randomUUID().toString(),
                    userId = user.uid,
                    userName = username,
                    text = text,
                    timestamp = System.currentTimeMillis()
                )

            val contentRef =
                firestore.collection("contents")
                    .document(contentId)

            contentRef.collection("comments")
                .document(comment.id)
                .set(comment)
                .await()

            contentRef.update(
                "commentsCount",
                com.google.firebase.firestore.FieldValue.increment(1)
            )
        }
    }

    /**
     * -------------------------------------------------
     * OBTENER COMENTARIOS EN TIEMPO REAL
     * -------------------------------------------------
     */
    fun fetchCommentsForPost(
        contentId: String,
        callback: (
            List<mx.edu.utng.avht.unidad2.data.CommentModel>
        ) -> Unit
    ) {
        firestore.collection("contents")
            .document(contentId)
            .collection("comments")
            .orderBy(
                "timestamp",
                com.google.firebase.firestore.Query.Direction.DESCENDING
            )
            .limit(3)
            .addSnapshotListener { snapshot, _ ->
                callback(
                    snapshot?.toObjects(
                        mx.edu.utng.avht.unidad2.data.CommentModel::class.java
                    ) ?: emptyList()
                )
            }
    }

    /**
     * -------------------------------------------------
     * OBTENER POSTS DE UN USUARIO
     * -------------------------------------------------
     */
    fun fetchUserPosts(
        userId: String,
        callback: (List<ContentModel>) -> Unit
    ) {
        firestore.collection("contents")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ ->
                callback(
                    snapshot?.toObjects(ContentModel::class.java)
                        ?.sortedByDescending { it.timestamp }
                        ?: emptyList()
                )
            }
    }

    /**
     * -------------------------------------------------
     * ELIMINAR POST
     * -------------------------------------------------
     */
    fun deletePost(
        postId: String,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        firestore.collection("contents")
            .document(postId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onError(it.message ?: "Error desconocido")
            }
    }
}
