package mx.edu.utng.avht.unidad2.data

/**
 * ContentModel
 *
 * Este data class representa un contenido publicado dentro de la aplicación.
 * Normalmente se utiliza para:
 * - Mostrar publicaciones en listas o mapas
 * - Guardar información en una base de datos
 * - Transferir datos entre capas (UI, ViewModel, repositorios)
 *
 * Al ser un "data class", Kotlin automáticamente genera:
 * - equals()
 * - hashCode()
 * - toString()
 * - copy()
 */
data class ContentModel(

    // Identificador único del contenido (por ejemplo, en una base de datos)
    val id: String = "",

    // Título del contenido o publicación
    val title: String = "",

    // Descripción o texto principal del contenido
    val description: String = "",

    // URL de la imagen asociada al contenido
    // Puede apuntar a almacenamiento local o remoto
    val imageUrl: String = "",

    // Latitud geográfica donde se creó o se ubicó el contenido
    val lat: Double = 0.0,

    // Longitud geográfica donde se creó o se ubicó el contenido
    val lng: Double = 0.0,

    // Identificador del usuario que creó el contenido
    val userId: String = "",

    // Nombre visible del usuario que publicó el contenido
    val userName: String = "",

    // URL de la foto de perfil del usuario
    val userProfilePicture: String = "",

    // Marca de tiempo de creación del contenido
    // Se inicializa automáticamente con el momento actual
    val timestamp: Long = System.currentTimeMillis(),

    // Cantidad total de "likes" que tiene el contenido
    val likesCount: Int = 0,

    // Lista de IDs de usuarios que han dado like al contenido
    // Se usa para saber si un usuario ya dio like
    val likedBy: List<String> = emptyList(),

    // Cantidad de comentarios que tiene el contenido
    val commentsCount: Int = 0
)

/**
 * CommentModel
 *
 * Este data class representa un comentario asociado a un contenido.
 * Está relacionado con ContentModel, ya que:
 * - Cada contenido puede tener uno o varios comentarios
 * - Se usa para mostrar conversaciones o interacciones
 */
data class CommentModel(

    // Identificador único del comentario
    val id: String = "",

    // Identificador del usuario que escribió el comentario
    val userId: String = "",

    // Nombre del usuario que escribió el comentario
    val userName: String = "",

    // Texto del comentario
    val text: String = "",

    // Marca de tiempo del momento en que se creó el comentario
    val timestamp: Long = System.currentTimeMillis()
)
