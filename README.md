# 📱 MAPÉATE 🗺 — Aplicación Android

Proyecto académico desarrollado en Android Studio con Kotlin y Jetpack Compose, que integra autenticación, navegación entre pantallas y funcionalidades visuales como mapas y perfiles de usuario.

## ✨ Descripción
Esta aplicación permite a los usuarios acceder con autenticación, navegar por diferentes secciones como Mapa Principal, Perfil, Comunidad y explorar contenido relacionado con rutas e información relevante.
El proyecto fue desarrollado como parte de la Materia Aplicaciones Móviles, integrando buenas prácticas de arquitectura, navegación Compose y Firebase. El objetivo principal de esta aplicacion, es que 
que los usuarios puedan conocer mas lugares y conocer mas lacultura en diversos lugares.

## 👩‍💻 Autoras
Sara Lizbeth Serna Rodríguez y Hernandez Torres Alondra vienney

Grupo: GTID141

Proyecto académico — Aplicaciones Móviles Unidaad 4

Universidad Tecnologica del norte de Guanajuato (UTNG)

## 📄 Licencia
Este proyecto se utiliza únicamente con fines educativos.

## 🚀 Funcionalidades principales

<table>
  <tr>
    <th align="left">✅ Funcionalidades</th>
    <th align="left">🛠️ Tecnologías utilizadas</th>
  </tr>

  <tr>
    <td valign="top">
      🔐 Inicio de sesión y autenticación con Firebase<br>
      🗺️ Pantalla de Mapa Principal<br>
      👤 Pantalla de Perfil de Usuario<br>
      👥 Pantalla de Comunidad<br>
      🔎 Explorar rutas desde la pantalla principal<br>
      📸 Integración futura para subida de imágenes desde cámara/galería<br>
      🎨 Temas de color personalizados y diseño moderno en Compose<br>
      🧭 Navegación intuitiva entre pantallas con NavHost y routes
    </td>
    <td valign="top">
      <ul>
        <li>🟣 Kotlin</li>
        <li>🎨 Jetpack Compose</li>
        <li>📐 Material 3</li>
        <li>🔥 Firebase Auth</li>
        <li>🗄️ Firebase Firestore</li>
        <li>🧭 AndroidX Navigation Compose</li>
        <li>🧠 ViewModel + StateFlow</li>
        <li>⚙️ Gradle KTS</li>
      </ul>
    </td>
  </tr>
</table>

## ⚜[ACTIVIDADES EXTRAS](https://github.com/slserna/MAEATE/tree/master/documentos) ⚜

|Actividad| Evidencias | LINKS |
| ------------- |  --------|  ------------- |
| Actividad 2: Demostración Funcional | Evidencia  | [Ver ejercicio](https://github.com/slserna/MAEATE/blob/master/documentos/Actividad-2/SernaRodriguezSaraLizbeth-Demo-U4.pdf)|
| Actividad 3: Pruebas con Usuarios | Evidencia  | [Ver ejercicio](https://github.com/slserna/MAEATE/blob/master/documentos/Actividad-3/HernandezTorresAlondraVianney-Pruebas--U4%20.pdf)|
| Carpeta docs/imágenes | Evidencias |  [Ver ejercicio](https://github.com/slserna/MAEATE/tree/master/documentos/Imagenes)|

---
## 📸 Capturas de la aplicación mapéate
<table>
  <tr>
    <th>🔐 Inicio de sesión</th>
    <th>🗺️ Pantalla principal</th>
    <th>🗺️ Mapa</th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Inicio.png" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Principal.jpeg" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Mapa.jpeg" width="260"/>
    </td>
  </tr>

  <tr>
    <th>📤 Subir contenido</th>
    <th>👤 Perfil de usuario</th>
    <th>👥 Comunidad</th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_SubirContenido.jpeg" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Perfil.jpeg" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Comunidad.jpeg" width="260"/>
    </td>
  </tr>

  <tr>
    <th>📝 Registro</th>
    <th></th>
    <th></th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Registro.png" width="260"/>
    </td>
    <td></td>
    <td></td>
  </tr>
</table>


## ✨ Documentación del Código con KDoc

Toda la base del código fue documentada utilizando KDoc, siguiendo buenas prácticas de documentación en Kotlin.
Se añadieron comentarios en formato /** ... */ :

- CLASE : ContentModel.kt
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








## 📂 Estructura del proyecto

```text
UNIDAD2/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/        (código fuente)
│   │       └── res/         (layouts, drawables, strings)
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md

