# 📱 MAPÉATE 🗺 — Aplicación Android

PAGINA OFICIAL DE DESCARGA : https://polarrrses.github.io/

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

```text
--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA DATA. CLASE : ContentModel.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
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

--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA SCREENS. CLASE : MapaPrincipalScreen.KT
--------------------------------------------------------------------------------------------------------------------------------------------------
package mx.edu.utng.avht.unidad2.screens
// Importa los permisos de Android para acceder a la ubicación precisa del dispositivo
import android.Manifest
import android.content.pm.PackageManager

// Permite manejar resultados de actividades (como pedir permisos) en Compose
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

// Componentes básicos de diseño en Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

// Componentes Material Design 3
import androidx.compose.material3.*

import androidx.compose.runtime.*

// Utilidades de interfaz de usuario
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Utilidad para verificar permisos en tiempo de ejecución
import androidx.core.content.ContextCompat

// Clases de Google Maps para manejar la cámara y coordenadas
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

// Librería de Google Maps para Jetpack Compose
import com.google.maps.android.compose.*

// Componentes personalizados de navegación de la app
import mx.edu.utng.avht.unidad2.BottomNav
import mx.edu.utng.avht.unidad2.TopBar

/**
 * MapaPrincipalScreen
 *
 * Pantalla principal que muestra un mapa interactivo usando Google Maps.
 *
 * Funcionalidades principales:
 * - Solicita permiso de ubicación al usuario
 * - Muestra la ubicación actual si el permiso es concedido
 * - Permite seleccionar una ubicación tocando o manteniendo presionado el mapa
 * - Coloca un marcador en la ubicación seleccionada
 * - Muestra un botón flotante para crear contenido en la ubicación seleccionada
 *
 * @param onNavigateBack Callback para regresar a la pantalla anterior
 * @param onNavigateToPerfil Callback para navegar al perfil del usuario
 * @param onNavigateToComunidad Callback para navegar a la comunidad
 * @param onNavigateToContenido Callback para navegar a la pantalla de creación de contenido,
 *        recibiendo latitud y longitud
 */
@Composable
fun MapaPrincipalScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToComunidad: () -> Unit = {},
    onNavigateToContenido: (Double, Double) -> Unit = { _, _ -> }
) {

    // Obtiene el contexto actual de la aplicación
    val context = LocalContext.current

    // Estado que controla la posición de la cámara del mapa
    // Se inicializa centrando el mapa en una ubicación específica
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(21.1619, -100.9300), // Coordenadas iniciales
            10f                         // Nivel de zoom
        )
    }

    // Estado que guarda la ubicación seleccionada por el usuario en el mapa
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

    // Estado que indica si el permiso de ubicación fue concedido
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Lanzador para pedir permiso de ubicación al usuario
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            // Actualiza el estado según si el permiso fue concedido o no
            hasLocationPermission = isGranted
        }
    )

    // Efecto que se ejecuta una sola vez al entrar a la pantalla
    // Se usa para solicitar el permiso de ubicación si aún no ha sido otorgado
    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Scaffold define la estructura principal de la pantalla
    Scaffold(
        // Barra superior personalizada
        topBar = { TopBar() },

        // Barra inferior de navegación
        bottomBar = {
            BottomNav(
                onNavigateToPerfil = onNavigateToPerfil,
                onNavigateToComunidad = onNavigateToComunidad
            )
        },

        // Botón flotante que aparece solo si hay una ubicación seleccionada
        floatingActionButton = {
            if (selectedLocation != null) {
                FloatingActionButton(
                    onClick = {
                        // Envía las coordenadas seleccionadas a la pantalla de creación de contenido
                        selectedLocation?.let {
                            onNavigateToContenido(it.latitude, it.longitude)
                        }
                    },
                    containerColor = Color(0xFFE4A691)
                ) {
                    Text("+", fontSize = 26.sp, color = Color.White)
                }
            }
        },

        // Color de fondo general de la pantalla
        containerColor = Color(0xFFF7EFD8)

    ) { padding ->

        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // Componente de Google Maps
            GoogleMap(
                modifier = Modifier.fillMaxSize(),

                // Controla la posición de la cámara
                cameraPositionState = cameraPositionState,

                // Propiedades del mapa (habilita ubicación si hay permiso)
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermission
                ),

                // Configuración de la interfaz del mapa
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = true
                ),

                // Evento al tocar el mapa
                onMapClick = { latLng ->
                    selectedLocation = latLng
                },

                // Evento al mantener presionado el mapa
                onMapLongClick = { latLng ->
                    selectedLocation = latLng
                }
            ) {

                // Si existe una ubicación seleccionada, se agrega un marcador
                selectedLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Ubicación seleccionada",
                        snippet = "Agregar contenido aquí"
                    )
                }
            }

            // Mensaje informativo cuando no hay ubicación seleccionada
            if (selectedLocation == null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .background(
                            Color.White.copy(alpha = 0.9f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        "📍 Toca el mapa para elegir ubicación",
                        fontSize = 14.sp,
                        color = Color(0xFF273142)
                    )
                }
            }
        }
    }
}

--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA SCREENS. CLASE : NuevoContenidoScreen.KT
--------------------------------------------------------------------------------------------------------------------------------------------------
package mx.edu.utng.avht.unidad2.screens
// Manejo de URIs (imágenes de cámara o galería)
import android.net.Uri

// Permite mostrar mensajes cortos al usuario
import android.widget.Toast

// Manejo de resultados de actividades (cámara, galería) en Compose
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

// Componentes visuales básicos
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

// Iconos Material
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

// Componentes Material Design 3
import androidx.compose.material3.*

import androidx.compose.runtime.*

// Utilidades de UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Permite obtener un ViewModel dentro de un Composable
import androidx.lifecycle.viewmodel.compose.viewModel

// Librería Coil para cargar imágenes desde URI
import coil.compose.rememberAsyncImagePainter

// ViewModel encargado de la lógica de publicación de contenido
import mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel

/**
 * NuevoContenidoScreen
 *
 * Pantalla que permite al usuario crear y publicar nuevo contenido.
 *
 * Funcionalidades principales:
 * - Recibe una ubicación (latitud y longitud)
 * - Permite seleccionar una imagen desde la galería o la cámara
 * - Captura título y descripción
 * - Muestra vista previa de la imagen seleccionada
 * - Llama al ViewModel para subir el contenido
 * - Muestra mensajes de éxito o error
 *
 * @param lat Latitud obtenida del mapa
 * @param lng Longitud obtenida del mapa
 * @param onNavigateBack Callback para regresar a la pantalla anterior
 * @param viewModel ViewModel encargado de manejar el estado y la lógica de publicación
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoContenidoScreen(
    lat: Double,
    lng: Double,
    onNavigateBack: () -> Unit,
    viewModel: ContentViewModel = viewModel()
) {

    // Contexto actual de la aplicación (necesario para Toast y FileProvider)
    val context = LocalContext.current

    // Estado expuesto por el ViewModel (loading, éxito, errores, imagen seleccionada)
    val state by viewModel.uiState.collectAsState()

    // Estado local para el título ingresado por el usuario
    var title by remember { mutableStateOf("") }

    // Estado local para la descripción ingresada por el usuario
    var description by remember { mutableStateOf("") }

    /**
     * Launcher para seleccionar imágenes desde la galería
     * Permite elegir cualquier archivo de tipo imagen
     */
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Se envía la URI seleccionada al ViewModel
        viewModel.onImageSelected(uri)
    }

    /**
     * URI temporal utilizada por la cámara
     * rememberSaveable permite conservarla ante recomposición o rotación
     */
    var cameraImageUri by androidx.compose.runtime.saveable.rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    /**
     * Launcher para tomar una foto con la cámara
     * Recibe true si la captura fue exitosa
     */
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraImageUri != null) {
            // Envía la imagen capturada al ViewModel
            viewModel.onImageSelected(cameraImageUri)
        }
    }

    /**
     * Crea un archivo temporal dentro del cache de la app
     * Se utiliza para guardar la imagen tomada con la cámara
     */
    fun createImageUri(): Uri {
        val timeStamp = System.currentTimeMillis()
        val imageFile = java.io.File(
            context.cacheDir,
            "camera_${timeStamp}.jpg"
        )

        // FileProvider permite compartir el archivo con la app de cámara
        return androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }

    /**
     * Observa el estado de éxito de la publicación
     * Si se publica correctamente, muestra un mensaje y regresa a la pantalla anterior
     */
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            Toast.makeText(context, "Publicado con éxito", Toast.LENGTH_SHORT).show()
            onNavigateBack()
        }
    }

    /**
     * Observa mensajes de error provenientes del ViewModel
     */
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Estructura general de la pantalla
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Contenido") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF7EFD8),
                    titleContentColor = Color(0xFF273142),
                    navigationIconContentColor = Color(0xFF273142)
                )
            )
        },
        containerColor = Color(0xFFF7EFD8)
    ) { padding ->

        // Contenedor principal desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Nuevo Contenido", fontSize = 24.sp, color = Color(0xFF273142))
            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Vista previa de la imagen seleccionada
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (state.selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(state.selectedImageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Sin imagen seleccionada")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Botones para escoger imagen
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE4A691)
                    )
                ) {
                    Text("📷 Galería")
                }

                Button(
                    onClick = {
                        cameraImageUri = createImageUri()
                        cameraLauncher.launch(cameraImageUri!!)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF556270)
                    )
                ) {
                    Text("📸 Cámara")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Campo para ingresar el título
             */
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            /**
             * Campo para ingresar la descripción
             */
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(24.dp))

            /**
             * Muestra indicador de carga o botón de publicación
             */
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        viewModel.uploadContent(
                            title,
                            description,
                            lat,
                            lng
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF273142)
                    )
                ) {
                    Text("Publicar", color = Color.White)
                }
            }
        }
    }
}
--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA SCREENS. CLASE :UserProfileScreen.KT
--------------------------------------------------------------------------------------------------------------------------------------------------
import com.google.firebase.auth.FirebaseAuth

// Firebase Firestore (datos del usuario)
import com.google.firebase.firestore.FirebaseFirestore

/**
 * UserProfileScreen
 *
 * Pantalla que muestra el perfil de un usuario.
 *
 * Funciones principales:
 * - Mostrar información del usuario (foto, nombre, email, bio)
 * - Detectar si el perfil pertenece al usuario actual
 * - Obtener y mostrar las publicaciones del usuario
 * - Mostrar las publicaciones en formato grid
 *
 * @param userId ID del usuario cuyo perfil se mostrará
 * @param onNavigateBack Callback para regresar a la pantalla anterior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    userId: String,
    onNavigateBack: () -> Unit
) {

    // Obtiene el ViewModel encargado de manejar contenidos
    val viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()

    // Obtiene el ID del usuario actualmente autenticado
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    // Indica si el perfil mostrado es del propio usuario
    val isOwnProfile = userId == currentUserId

    // Estados que almacenan la información del perfil
    var username by remember { mutableStateOf("Cargando...") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profilePicture by remember { mutableStateOf("") }

    // Lista de publicaciones del usuario
    var userPosts by remember {
        mutableStateOf<List<mx.edu.utng.avht.unidad2.data.ContentModel>>(emptyList())
    }

    /**
     * DisposableEffect se ejecuta cuando el userId cambia
     * y se limpia cuando el Composable sale de composición.
     */
    DisposableEffect(userId) {

        // Instancia de Firestore
        val db = FirebaseFirestore.getInstance()

        // Obtención de datos del usuario
        db.collection("usuarios")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                username = doc.getString("username")
                    ?: doc.getString("email")
                            ?: "Usuario"
                email = doc.getString("email") ?: ""
                bio = doc.getString("bio") ?: ""
                profilePicture = doc.getString("profilePicture") ?: ""
            }

        // Obtiene las publicaciones del usuario desde el ViewModel
        viewModel.fetchUserPosts(userId) { posts ->
            userPosts = posts
        }

        // Limpieza del efecto (no se requiere acción adicional)
        onDispose { }
    }

    /**
     * Estructura general de la pantalla
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isOwnProfile) "Mi Perfil" else username) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD2D0A6)
                )
            )
        },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        // Contenedor principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {

            /**
             * ---------- CABECERA DEL PERFIL ----------
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Foto de perfil
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8A38B)),
                        contentAlignment = Alignment.Center
                    ) {

                        // Si la imagen está en Base64
                        if (profilePicture.isNotEmpty() &&
                            profilePicture.startsWith("data:image")
                        ) {
                            val base64String =
                                profilePicture.substringAfter("base64,")
                            val imageBytes =
                                android.util.Base64.decode(
                                    base64String,
                                    android.util.Base64.DEFAULT
                                )

                            val bitmap =
                                android.graphics.BitmapFactory.decodeByteArray(
                                    imageBytes,
                                    0,
                                    imageBytes.size
                                )

                            if (bitmap != null) {
                                Image(
                                    painter = androidx.compose.ui.graphics.painter
                                        .BitmapPainter(bitmap.asImageBitmap()),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text("👤", fontSize = 32.sp)
                            }
                        } else {
                            Text("👤", fontSize = 32.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nombre de usuario
                    Text(
                        username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    // Email
                    if (email.isNotBlank()) {
                        Text(email, color = Color.Gray, fontSize = 13.sp)
                    }

                    // Biografía
                    if (bio.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(bio, fontSize = 14.sp)
                    }
                }
            }

            /**
             * ---------- SECCIÓN DE PUBLICACIONES ----------
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3EBD2))
                    .padding(16.dp)
            ) {

                Text(
                    "Publicaciones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Si no hay publicaciones
                if (userPosts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay publicaciones aún", color = Color.Gray)
                    }
                } else {

                    // Grid de publicaciones
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 4.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp),
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        items(userPosts) { post ->

                            // Decodificación de imágenes Base64
                            val decodedBitmap = remember(post.imageUrl) {
                                if (post.imageUrl.startsWith("data:image")) {
                                    try {
                                        val base64String =
                                            post.imageUrl.substringAfter("base64,")
                                        val imageBytes =
                                            android.util.Base64.decode(
                                                base64String,
                                                android.util.Base64.DEFAULT
                                            )
                                        android.graphics.BitmapFactory
                                            .decodeByteArray(
                                                imageBytes,
                                                0,
                                                imageBytes.size
                                            )
                                    } catch (e: Exception) {
                                        null
                                    }
                                } else {
                                    null
                                }
                            }

                            // Contenedor de publicación
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFE8A38B)),
                                contentAlignment = Alignment.Center
                            ) {

                                // Prioridad de imagen
                                if (post.imageUrl.isNotEmpty()) {
                                    if (decodedBitmap != null) {
                                        Image(
                                            painter =
                                                androidx.compose.ui.graphics.painter
                                                    .BitmapPainter(
                                                        decodedBitmap.asImageBitmap()
                                                    ),
                                            contentDescription = post.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else if (!post.imageUrl.startsWith("data:image")) {
                                        // Imagen remota
                                        coil.compose.AsyncImage(
                                            model = post.imageUrl,
                                            contentDescription = post.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Text("❌", fontSize = 28.sp)
                                    }
                                } else {
                                    Text("📷", fontSize = 28.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA ui.theme  CLASE : Color.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
// Define el paquete donde se agrupan los elementos del tema visual de la app.
// Normalmente "ui.theme" contiene colores, tipografías y formas.
package mx.edu.utng.avht.unidad2.ui.theme

// Importa la clase Color para definir colores personalizados en Jetpack Compose
import androidx.compose.ui.graphics.Color

/**
 * Archivo de definición de colores del tema.
 *
 * Aquí se declaran constantes de tipo Color que representan
 * la paleta base de la aplicación.
 *
 * Estos colores normalmente son utilizados en:
 * - MaterialTheme
 * - Botones
 * - Textos
 * - Fondos
 * - Barras de navegación
 *
 * La separación en dos grupos (80 y 40) se utiliza comúnmente
 * para diferenciar entre:
 * - Tema claro
 * - Tema oscuro
 */

/**
 * Colores principales para tema claro
 */

// Color púrpura principal en valores claros
val Purple80 = Color(0xFFD0BCFF)

// Variación gris del púrpura para superficies o fondos
val PurpleGrey80 = Color(0xFFCCC2DC)

// Color rosa claro usado para acentos
val Pink80 = Color(0xFFEFB8C8)

/**
 * Colores principales para tema oscuro
 */

// Púrpura más oscuro, usado en modo oscuro
val Purple40 = Color(0xFF6650a4)

// Gris púrpura más intenso para superficies
val PurpleGrey40 = Color(0xFF625b71)

// Rosa oscuro para elementos de énfasis
val Pink40 = Color(0xFF7D5260)

--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA ui.theme  CLASE : Theme.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
package mx.edu.utng.avht.unidad2.ui.theme
// Importa la clase Activity (usada indirectamente para obtener el contexto)
import android.app.Activity

// Permite verificar la versión del sistema Android
import android.os.Build

// Detecta si el sistema está usando tema oscuro
import androidx.compose.foundation.isSystemInDarkTheme

// Componentes de tema Material Design 3
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

// Anotación para funciones composables
import androidx.compose.runtime.Composable

// Proporciona acceso al contexto actual
import androidx.compose.ui.platform.LocalContext

/**
 * Esquema de colores para el modo oscuro.
 *
 * Usa los colores definidos previamente en el archivo Colors.kt.
 * Se aplica cuando el sistema o la app están en modo oscuro
 * y no se utilizan colores dinámicos.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * Esquema de colores para el modo claro.
 *
 * Usa versiones más oscuras de los colores base,
 * pensadas para usarse sobre fondos claros.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /*
     * Otros colores que podrían sobrescribirse si se desea:
     *
     * background = Color(0xFFFFFBFE),
     * surface = Color(0xFFFFFBFE),
     * onPrimary = Color.White,
     * onSecondary = Color.White,
     * onTertiary = Color.White,
     * onBackground = Color(0xFF1C1B1F),
     * onSurface = Color(0xFF1C1B1F),
     */
)

/**
 * UNIDAD2Theme
 *
 * Función composable que define el tema visual global de la aplicación.
 *
 * Responsabilidades:
 * - Elegir entre tema claro u oscuro
 * - Soportar colores dinámicos en Android 12 o superior
 * - Aplicar esquemas de color y tipografía a toda la app
 *
 * @param darkTheme Indica si se debe usar el tema oscuro.
 *        Por defecto se basa en la configuración del sistema.
 * @param dynamicColor Habilita colores dinámicos (Material You).
 *        Solo está disponible en Android 12 (API 31) o superior.
 * @param content Contenido composable al que se aplicará el tema.
 */
@Composable
fun UNIDAD2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Los colores dinámicos solo están disponibles en Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    /**
     * Selección del esquema de colores.
     *
     * Prioridad:
     * 1. Colores dinámicos (si están habilitados y el SO lo permite)
     * 2. Tema oscuro tradicional
     * 3. Tema claro tradicional
     */
    val colorScheme = when {

        // Uso de colores dinámicos (Material You)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        // Tema oscuro sin colores dinámicos
        darkTheme -> DarkColorScheme

        // Tema claro sin colores dinámicos
        else -> LightColorScheme
    }

    /**
     * Aplicación del tema a toda la jerarquía composable.
     *
     * MaterialTheme distribuye:
     * - Colores
     * - Tipografía
     * - Estilos visuales
     * a todos los composables hijos.
     */
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA ui.theme  CLASE : Type.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
// Define el paquete donde se agrupan los elementos del tema visual
package mx.edu.utng.avht.unidad2.ui.theme

// Clase principal de Material Design 3 para tipografías
import androidx.compose.material3.Typography

// Definición de estilos de texto
import androidx.compose.ui.text.TextStyle

// Familias tipográficas disponibles
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

// Unidad de medida escalable para texto
import androidx.compose.ui.unit.sp

/**
 * Tipografía global de la aplicación.
 *
 * Este archivo define los estilos de texto utilizados en toda la app
 * siguiendo las guías de Material Design.
 *
 * Los estilos definidos aquí se aplican automáticamente cuando se usa:
 * - MaterialTheme.typography
 *
 * Esto permite mantener coherencia visual en:
 * - Textos
 * - Títulos
 * - Botones
 * - Descripciones
 */

// Conjunto de estilos tipográficos base de Material Design 3
val Typography = Typography(

    /**
     * Estilo de texto para contenido principal.
     *
     * bodyLarge se utiliza comúnmente para:
     * - Texto principal
     * - Descripciones extensas
     * - Contenido informativo
     */
    bodyLarge = TextStyle(

        // Familia tipográfica por defecto del sistema
        fontFamily = FontFamily.Default,

        // Grosor de letra normal
        fontWeight = FontWeight.Normal,

        // Tamaño de la fuente
        fontSize = 16.sp,

        // Altura de línea para mejorar la legibilidad
        lineHeight = 24.sp,

        // Espaciado entre letras
        letterSpacing = 0.5.sp
    )

)
--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA viewmodel  CLASE : ContentViewModel.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
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
--------------------------------------------------------------------------------------------------------------------------------------------------
DE LA CARPETA viewmodel  CLASE : login.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
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
--------------------------------------------------------------------------------------------------------------------------------------------------
  CLASE : Components.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
package mx.edu.utng.avht.unidad2

// ---------------- IMPORTS ----------------
// Importaciones necesarias para layouts, estilos y componentes de Jetpack Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// ---------------- COLORES ----------------
// Colores definidos a nivel global para poder reutilizarlos en diferentes componentes

// Color salmón claro usado como color secundario
val salmon = Color(0xFFE4A691)

// Color crema para fondos claros
val crema = Color(0xFFF7EFD8)

// Tono azul grisáceo para íconos o texto
val azulGris = Color(0xFF556270)

// Azul oscuro usado para contrastes
val azulOscuro = Color(0xFF273142)



// ---------------- TOP BAR ----------------
// Barra superior con simulación de campo de búsqueda
@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            // Ocupa todo el ancho disponible
            .fillMaxWidth()

            // Margen externo horizontal y vertical
            .padding(horizontal = 16.dp, vertical = 8.dp)

            // Bordes redondeados
            .clip(RoundedCornerShape(30.dp))

            // Fondo blanco de la barra
            .background(Color.White),

        // Alinea los elementos verticalmente al centro
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Ícono de búsqueda (emoji)
        Text(
            "🔍",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
            color = azulGris
        )

        // Texto placeholder que simula un buscador
        Text(
            "Buscar lugar o meme...",
            modifier = Modifier.padding(
                start = 8.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
            color = Color.Gray
        )

        // Espacio flexible para empujar elementos a la derecha
        Spacer(modifier = Modifier.weight(1f))

        // Ícono de notificaciones (comentado)
        /* Text("🔔", modifier = Modifier.padding(end = 16.dp)) */
    }
}



// ---------------- BOTTOM NAVIGATION ----------------
// Barra de navegación inferior con tres opciones
@Composable
fun BottomNav(
    // Callback para navegar al perfil
    onNavigateToPerfil: () -> Unit = {},

    // Callback para navegar a la comunidad
    onNavigateToComunidad: () -> Unit = {}
) {
    // Componente de Material 3 para navegación inferior
    NavigationBar(containerColor = Color.White) {

        // ---------------- MAPA ----------------
        NavigationBarItem(
            // Indica que esta opción está seleccionada
            selected = true,

            // Acción al presionar (vacía por defecto)
            onClick = {},

            // Ícono del mapa
            icon = { Text("📍") },

            // Texto debajo del ícono
            label = { Text("Mapa") }
        )

        // ---------------- COMUNIDAD ----------------
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToComunidad() },
            icon = { Text("👥") },
            label = { Text("Comunidad") }
        )

        // ---------------- PERFIL ----------------
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToPerfil() },
            icon = { Text("🏠") },
            label = { Text("Perfil") }
        )
    }
}
--------------------------------------------------------------------------------------------------------------------------------------------------
  CLASE : MainActivity.kt
--------------------------------------------------------------------------------------------------------------------------------------------------
package mx.edu.utng.avht.unidad2

// Android & Compose básico
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.text.style.TextDecoration
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Íconos
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack

// Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.navArgument
import androidx.navigation.NavType

// Coil para imágenes
import coil.compose.rememberAsyncImagePainter

// ViewModels y pantallas
import mx.edu.utng.avht.unidad2.screens.MapaPrincipalScreen
import mx.edu.utng.avht.unidad2.screens.NuevoContenidoScreen
import mx.edu.utng.avht.unidad2.screens.UserProfileScreen
import mx.edu.utng.avht.unidad2.viewmodel.LoginViewModel
import mx.edu.utng.avht.unidad2.viewmodel.PerfilViewModel

// Java / IO
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * ------------------------------------------------------------------------
 * ACTIVIDAD PRINCIPAL
 * ------------------------------------------------------------------------
 * Punto de entrada de la aplicación. Inicializa Compose
 * y carga la navegación principal.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

/**
 * ------------------------------------------------------------------------
 * DEFINICIÓN DE RUTAS DE NAVEGACIÓN
 * ------------------------------------------------------------------------
 * Clase sellada que centraliza todas las rutas de navegación de la aplicación.
 */
sealed class Screen(val route: String) {

    /** Pantalla de inicio de sesión */
    object Login : Screen("login")

    /** Pantalla principal */
    object Principal : Screen("principal")

    /** Pantalla del mapa */
    object MapaPrincipal : Screen("mapa_principal")

    /** Pantalla de perfil */
    object Perfil : Screen("perfil")

    /** Pantalla de comunidad */
    object Comunidad : Screen("comunidad")

    /**
     * Pantalla de contenido con parámetros
     */
    object Contenido : Screen("contenido/{lat}/{lng}") {
        fun createRoute(lat: Double, lng: Double) = "contenido/$lat/$lng"
    }

    /** Pantalla de registro */
    object Register : Screen("register_screen")
}

/**
 * ------------------------------------------------------------------------
 * CONFIGURACIÓN PRINCIPAL DE NAVEGACIÓN
 * ------------------------------------------------------------------------
 * Maneja todas las pantallas y transiciones de la app.
 */
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        /**
         * Pantalla de Login
         */
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Principal.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        /**
         * Pantalla de Registro
         */
        composable(Screen.Register.route) {
            RegisterScreen(
                onBack = { navController.popBackStack() }
            )
        }

        /**
         * Pantalla Principal
         */
        composable(Screen.Principal.route) {
            PrincipalGto(
                onExplorarRutas = {
                    navController.navigate(Screen.MapaPrincipal.route)
                },
                onNavigateToPerfil = {
                    navController.navigate(Screen.Perfil.route)
                },
                onNavigateToComunidad = {
                    navController.navigate(Screen.Comunidad.route)
                }
            )
        }

        /**
         * Pantalla del Mapa
         */
        composable(Screen.MapaPrincipal.route) {
            MapaPrincipalScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPerfil = { navController.navigate(Screen.Perfil.route) },
                onNavigateToComunidad = { navController.navigate(Screen.Comunidad.route) },
                onNavigateToContenido = { lat, lng ->
                    navController.navigate(Screen.Contenido.createRoute(lat, lng))
                }
            )
        }

        /**
         * Pantalla de Contenido con parámetros
         */
        composable(
            route = Screen.Contenido.route,
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lng") { type = NavType.FloatType }
            )
        ) { backStackEntry ->

            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStackEntry.arguments?.getFloat("lng")?.toDouble() ?: 0.0

            NuevoContenidoScreen(
                lat = lat,
                lng = lng,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        /**
         * Pantalla de Perfil
         */
        composable(Screen.Perfil.route) {
            PerfilUsuarioScreen(navController = navController)
        }

        /**
         * Pantalla de Comunidad
         */
        composable(Screen.Comunidad.route) {
            FeedComunidadScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        /**
         * Perfil de otro usuario
         */
        composable(
            route = "user_profile/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            UserProfileScreen(
                userId = userId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * ------------------------------------------------------------------------
 * PANTALLA DE INICIO DE SESIÓN
 * ------------------------------------------------------------------------
 * Permite al usuario iniciar sesión o navegar al registro.
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {

    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        if (state.isLoginSuccessful) {
            LaunchedEffect(Unit) {
                onLoginSuccess()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("🔐", fontSize = 50.sp)
            Spacer(Modifier.height(8.dp))

            Text(
                text = "Iniciar sesión",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE4A691)
                )
            ) {
                Text("Iniciar sesión", color = Color.White)
            }

            state.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }

            Spacer(Modifier.height(20.dp))

            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Crear cuenta", fontSize = 16.sp)
            }
        }
    }
}

/**
 * ============================================================================
 * PANTALLA DE REGISTRO DE USUARIO
 * ============================================================================
 * Composable responsable de mostrar la interfaz de registro de nuevos usuarios.
 *
 * Permite:
 *  - Ingresar nombre de usuario
 *  - Ingresar correo electrónico
 *  - Ingresar contraseña
 *  - Ejecutar el proceso de registro usando un ViewModel
 *  - Navegar de regreso a la pantalla de inicio de sesión
 *
 * Esta pantalla forma parte del flujo de autenticación.
 */
@Composable
fun RegisterScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit = {}
) {

    /**
     * ------------------------------------------------------------------------
     * ESTADO DE LA INTERFAZ
     * ------------------------------------------------------------------------
     * Se observa el estado expuesto por el ViewModel mediante StateFlow.
     * Aquí se concentran los valores del correo, contraseña y errores.
     */
    val state = viewModel.uiState.collectAsState().value

    /**
     * ------------------------------------------------------------------------
     * ESTADO LOCAL: NOMBRE DE USUARIO
     * ------------------------------------------------------------------------
     * El nombre de usuario se maneja localmente ya que no forma parte
     * del estado principal de autenticación del ViewModel.
     */
    var nombre by remember { mutableStateOf("") }

    /**
     * ------------------------------------------------------------------------
     * CONTENEDOR BASE DE LA PANTALLA
     * ------------------------------------------------------------------------
     * Scaffold se usa para:
     *  - Definir el color de fondo
     *  - Establecer una estructura clara de pantalla
     */
    Scaffold(
        containerColor = Color(0xFFF7EFD8) // Fondo tipo crema
    ) { padding ->

        /**
         * --------------------------------------------------------------------
         * CONTENEDOR PRINCIPAL
         * --------------------------------------------------------------------
         * Column organiza los elementos verticalmente de forma centrada.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /**
             * ================================================================
             * BARRA SUPERIOR CON BOTÓN DE REGRESO
             * ================================================================
             * Permite al usuario volver a la pantalla anterior (Login).
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            /**
             * ================================================================
             * ÍCONO Y TÍTULO PRINCIPAL
             * ================================================================
             */
            Text("📝", fontSize = 50.sp)
            Text(
                text = "Regístrate",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            /**
             * ================================================================
             * CAMPO: NOMBRE DE USUARIO
             * ================================================================
             * Captura el nombre visible del usuario dentro de la aplicación.
             */
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * ================================================================
             * CAMPO: CORREO ELECTRÓNICO
             * ================================================================
             * Se conecta directamente con el ViewModel.
             */
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * ================================================================
             * CAMPO: CONTRASEÑA
             * ================================================================
             * La contraseña se oculta visualmente por seguridad.
             */
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            /**
             * ================================================================
             * BOTÓN DE REGISTRO
             * ================================================================
             * Ejecuta el proceso de registro utilizando el ViewModel
             * y regresa a la pantalla de login.
             */
            Button(
                onClick = {
                    viewModel.onRegisterClick()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE4A691)
                )
            ) {
                Text("Registrarme", color = Color.White)
            }

            /**
             * ================================================================
             * MANEJO DE ERRORES
             * ================================================================
             * Muestra un mensaje visual si ocurre un fallo en el registro.
             */
            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }
        }
    }
}

/**
 * ============================================================================
 * PANTALLA PRINCIPAL / BIENVENIDA
 * ============================================================================
 * Pantalla mostrada después de iniciar sesión correctamente.
 *
 * Sirve como punto central de navegación hacia:
 *  - Exploración de rutas
 *  - Comunidad
 *  - Perfil del usuario
 */
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PrincipalGto(
    onExplorarRutas: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToComunidad: () -> Unit = {}
) {

    /**
     * ------------------------------------------------------------------------
     * PALETA DE COLORES LOCAL
     * ------------------------------------------------------------------------
     * Utilizada para mantener coherencia visual en la pantalla.
     */
    val salmon = Color(0xFFE4A691)
    val crema = Color(0xFFF7EFD8)
    val verdeSuave = Color(0xFFC8C8A9)
    val azulGris = Color(0xFF556270)

    /**
     * ------------------------------------------------------------------------
     * CONTENEDOR BASE
     * ------------------------------------------------------------------------
     * Box permite superponer elementos, como imagen de fondo y contenido.
     */
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        /**
         * ================================================================
         * IMAGEN DE FONDO
         * ================================================================
         * Imagen representativa que cubre toda la pantalla.
         */
        Image(
            painter = painterResource(id = R.drawable.gto_bonito),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        /**
         * ================================================================
         * CONTENEDOR PRINCIPAL DEL CONTENIDO
         * ================================================================
         * Aloja el título principal y los botones de navegación.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            /**
             * ------------------------------------------------------------
             * TÍTULO PRINCIPAL DE LA APLICACIÓN
             * ------------------------------------------------------------
             */
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(verdeSuave.copy(alpha = 0.88f))
                    .padding(horizontal = 28.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "🗺️ MAPEATE",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            /**
             * ================================================================
             * BOTONES DE NAVEGACIÓN PRINCIPAL
             * ================================================================
             */
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Navegación a exploración de rutas
                Button(
                    onClick = { onExplorarRutas() },
                    colors = ButtonDefaults.buttonColors(containerColor = crema),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text("📍 Explorar rutas", color = azulGris)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navegación a la comunidad
                Button(
                    onClick = { onNavigateToComunidad() },
                    colors = ButtonDefaults.buttonColors(containerColor = salmon),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text("🏘️ Comunidad", color = azulGris)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navegación al perfil del usuario
                Button(
                    onClick = { onNavigateToPerfil() },
                    colors = ButtonDefaults.buttonColors(containerColor = azulGris),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text("👤 Mi perfil", color = crema)
                }
            }
        }
    }
}

/**
 * ============================================================================
 * PANTALLA DE DETALLE DE RUTA
 * ============================================================================
 * Composable encargado de mostrar la vista detallada de una ruta seleccionada
 * dentro de la aplicación.
 *
 * Esta pantalla es principalmente visual y de presentación, e incluye:
 *  - Encabezado con título
 *  - Imagen representativa del lugar
 *  - Información general de la ruta
 *  - Detalles adicionales
 *  - Acciones principales (iniciar ruta, guardar, compartir)
 *  - Sección de comentarios
 *
 * NOTA:
 * Actualmente los datos son simulados mediante bloques visuales
 * (placeholders), ya que no se conecta aún a un ViewModel o base de datos.
 */
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DetalleRuta() {

    /**
     * ------------------------------------------------------------------------
     * PALETA DE COLORES LOCAL
     * ------------------------------------------------------------------------
     * Colores definidos dentro del composable para mantener identidad visual
     * consistente en esta pantalla.
     */
    val salmon = Color(0xFFE4A691)      // Color principal de acción
    val crema = Color(0xFFF7EFD8)       // Color de fondo general
    val verdeSuave = Color(0xFFC8C8A9)  // Color para bloques de información
    val azulGris = Color(0xFF556270)    // Color secundario
    val azulOscuro = Color(0xFF273142)  // Color para textos y encabezados

    /**
     * ------------------------------------------------------------------------
     * CONTENEDOR PRINCIPAL DE LA PANTALLA
     * ------------------------------------------------------------------------
     * Column:
     *  - Ocupa toda la pantalla
     *  - Permite desplazamiento vertical (scroll)
     *  - Aplica padding inferior para evitar cortes visuales
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(crema)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
    ) {

        /**
         * ====================================================================
         * ENCABEZADO SUPERIOR
         * ====================================================================
         * Simula una barra superior con:
         *  - Icono visual de regreso
         *  - Título de la pantalla
         *
         * (Actualmente no implementa navegación real)
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(crema)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Indicador visual de regreso
            Text(
                text = "←",
                color = azulOscuro,
                fontSize = 22.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            // Título de la vista
            Text(
                text = "Detalle de Ruta",
                color = azulOscuro,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        /**
         * ====================================================================
         * IMAGEN PRINCIPAL DE LA RUTA
         * ====================================================================
         * Representa visualmente el lugar de la ruta.
         * Actualmente es un contenedor de texto simulando una imagen.
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(salmon),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Imagen del lugar",
                color = azulOscuro,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * ====================================================================
         * TÍTULO DEL LUGAR
         * ====================================================================
         * Muestra el nombre principal de la ruta o destino.
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(azulOscuro, RoundedCornerShape(6.dp))
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Nombre del lugar",
                color = crema,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        /**
         * ====================================================================
         * UBICACIÓN DE LA RUTA
         * ====================================================================
         * Muestra de manera visual la ubicación del lugar.
         */
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Ícono de ubicación
            Text("📍", fontSize = 16.sp)

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .background(verdeSuave, RoundedCornerShape(6.dp))
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Ubicación de la ruta",
                    color = azulOscuro,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        /**
         * ====================================================================
         * DESCRIPCIÓN GENERAL DE LA RUTA
         * ====================================================================
         * Se simula la descripción mediante bloques de texto visual.
         * Esto representa contenido dinámico aún no conectado.
         */
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(verdeSuave, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * ====================================================================
         * SECCIÓN DE DETALLES ADICIONALES
         * ====================================================================
         * Información complementaria de la ruta (dificultad, tiempo, clima, etc.)
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(crema, RoundedCornerShape(12.dp))
                .border(1.dp, azulGris, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Column {

                // Encabezado de la sección
                Box(
                    modifier = Modifier
                        .background(azulGris, RoundedCornerShape(4.dp))
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Detalles adicionales",
                        color = crema,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Contenido simulado
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(verdeSuave, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /**
         * ====================================================================
         * ACCIONES PRINCIPALES
         * ====================================================================
         * Incluye:
         *  - Botón para iniciar la ruta
         *  - Acciones secundarias (guardar y compartir)
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Botón principal
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = salmon),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            ) {
                Text(
                    text = "🚀 Iniciar ruta",
                    color = azulOscuro,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Botones secundarios
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(crema, RoundedCornerShape(10.dp))
                        .border(2.dp, azulOscuro, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("🔖") }

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(crema, RoundedCornerShape(10.dp))
                        .border(2.dp, azulOscuro, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("🔗") }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        /**
         * ====================================================================
         * SECCIÓN DE COMENTARIOS
         * ====================================================================
         * Muestra comentarios simulados de usuarios.
         */
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            Text(
                text = "Comentarios",
                color = azulOscuro,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            /**
             * Comentarios simulados
             */
            repeat(2) { index ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(crema, RoundedCornerShape(12.dp))
                        .border(1.dp, verdeSuave, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Avatar del usuario
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (index == 0) salmon else azulGris,
                                CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Contenido del comentario
                    Column(modifier = Modifier.weight(1f)) {

                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width(120.dp)
                                .background(azulOscuro, RoundedCornerShape(4.dp))
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(12.dp)
                                .background(verdeSuave, RoundedCornerShape(4.dp))
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Reacción visual
                    Text(if (index == 0) "❤" else "🤍")
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

/**
 * ============================================================================
 * PANTALLA DE PERFIL DEL USUARIO
 * ============================================================================
 * Esta pantalla muestra la información del usuario autenticado.
 * Desde aquí el usuario puede:
 *  - Ver su nombre y correo
 *  - Editar y guardar su biografía
 *  - Cambiar su foto de perfil
 *  - Cerrar sesión
 *  - Visualizar sus propias publicaciones
 *
 * Se conecta con:
 *  - PerfilViewModel → información del usuario
 *  - ContentViewModel → publicaciones del usuario
 *  - FirebaseAuth → autenticación
 */
@Composable
fun PerfilUsuarioScreen(
    navController: NavController,
    perfilViewModel: PerfilViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    /**
    * ------------------------------------------------------------------------
    * ESTADOS EXPUESTOS DESDE EL PERFIL VIEWMODEL
    * ------------------------------------------------------------------------
    * Se observan mediante StateFlow + collectAsState()
    */
    val username by perfilViewModel.username.collectAsState()
    val email by perfilViewModel.email.collectAsState()
    val bio by perfilViewModel.bio.collectAsState()

    /**
     * Estado local editable de la biografía.
     * Se usa para no modificar directamente el estado del ViewModel
     * hasta que el usuario presione "Guardar".
     */
    var bioText by remember { mutableStateOf("") }

    /**
     * Sincroniza el texto editable cada vez que la bio real cambia.
     * Esto evita inconsistencias visuales.
     */
    LaunchedEffect(bio) {
        bioText = bio
    }

    /**
     * ------------------------------------------------------------------------
     * ESTRUCTURA BASE DE LA PANTALLA
     * ------------------------------------------------------------------------
     * Scaffold permite manejar:
     *  - TopBar
     *  - Contenido principal
     */
    Scaffold(
        topBar = {
            TopBarPerfil(
                onNavigateBack = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        /**
         * Contenedor principal vertical
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {

            /**
             * ====================================================================
             * SECCIÓN SUPERIOR: DATOS DEL PERFIL
             * ====================================================================
             * Incluye:
             *  - Foto de perfil
             *  - Nombre
             *  - Email
             *  - Biografía editable
             *  - Botones de acción
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    /**
                     * ------------------------------------------------------------
                     * SELECCIÓN DE IMAGEN DE PERFIL
                     * ------------------------------------------------------------
                     * Se utiliza ActivityResult para seleccionar una imagen
                     * desde la galería del dispositivo.
                     */
                    val context = LocalContext.current
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        uri?.let {
                            perfilViewModel.updateProfilePicture(it, context)
                        }
                    }

                    /**
                     * Observa la foto de perfil en Base64
                     */
                    val profilePic = perfilViewModel.profilePicture.collectAsState()

                    /**
                     * Contenedor circular de la imagen de perfil
                     */
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8A38B))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {


                        /**
                         * Decodificación de imagen Base64 si existe
                         */
                        if (
                            profilePic.value.isNotEmpty() &&
                            profilePic.value.startsWith("data:image")
                        ) {
                            val base64String = profilePic.value.substringAfter("base64,")
                            val imageBytes = android.util.Base64.decode(
                                base64String,
                                android.util.Base64.DEFAULT
                            )
                            val bitmap = android.graphics.BitmapFactory.decodeByteArray(
                                imageBytes,
                                0,
                                imageBytes.size
                            )

                            if (bitmap != null) {
                                Image(
                                    painter = androidx.compose.ui.graphics.painter.BitmapPainter(
                                        bitmap.asImageBitmap()
                                    ),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                            } else {
                                Text("👤", fontSize = 32.sp)
                            }
                        } else {
                            Text("👤", fontSize = 32.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    /**
                     * NOMBRE Y CORREO DEL USUARIO
                     */
                    if (username == "Cargando...") {
                        CircularProgressIndicator()
                    } else {

                        Text(
                            text = username,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        if (email.isNotBlank()) {
                            Text(
                                text = email,
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        /**
                         * CAMPO EDITABLE DE BIOGRAFÍA
                         */
                        OutlinedTextField(
                            value = bioText,
                            onValueChange = { bioText = it },
                            label = { Text("Descripción") },
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        /**
                         * BOTÓN PARA GUARDAR BIO
                         */
                        Button(
                            onClick = { perfilViewModel.updateBio(bioText) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC8C8A9),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Guardar descripción")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        /**
                         * BOTÓN PARA CERRAR SESIÓN
                         */
                        Button(
                            onClick = {
                                com.google.firebase.auth.FirebaseAuth
                                    .getInstance()
                                    .signOut()

                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text("🚪 Cerrar sesión", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            /**
             * ----------------------------------------------------------------
             * SECCIÓN INFERIOR: PUBLICACIONES DEL USUARIO
             * ----------------------------------------------------------------
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3EBD2))
                    .padding(16.dp)
            ) {

                Text(
                    text = "Mis Publicaciones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                /**
                 * CARGA DE PUBLICACIONES DEL USUARIO ACTUAL
                 */
                var userPosts by remember {
                    mutableStateOf<List<mx.edu.utng.avht.unidad2.data.ContentModel>>(emptyList())
                }

                val contentViewModel:
                        mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel =
                    androidx.lifecycle.viewmodel.compose.viewModel()

                val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
                val currentUserId = auth.currentUser?.uid ?: ""

                DisposableEffect(currentUserId) {
                    if (currentUserId.isNotEmpty()) {
                        contentViewModel.fetchUserPosts(currentUserId) { posts ->
                            userPosts = posts
                        }
                    }
                    onDispose { }
                }

                /**
                 * ESTADO SIN PUBLICACIONES
                 */
                if (userPosts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No tienes publicaciones aún", color = Color.Gray)
                    }
                } else {

                    /**
                     * GRID DE PUBLICACIONES
                     */
                    var selectedPost by remember {
                        mutableStateOf<mx.edu.utng.avht.unidad2.data.ContentModel?>(null)
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userPosts) { post ->

                            /**
                             * Decodificación opcional de imagen en Base64
                             */
                            val decodedBitmap = remember(post.imageUrl) {
                                if (post.imageUrl.startsWith("data:image")) {
                                    try {
                                        val base64String =
                                            post.imageUrl.substringAfter("base64,")
                                        val imageBytes = android.util.Base64.decode(
                                            base64String,
                                            android.util.Base64.DEFAULT
                                        )
                                        android.graphics.BitmapFactory.decodeByteArray(
                                            imageBytes,
                                            0,
                                            imageBytes.size
                                        )
                                    } catch (e: Exception) {
                                        null
                                    }
                                } else null
                            }

                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFE8A38B))
                                    .clickable { selectedPost = post },
                                contentAlignment = Alignment.Center
                            ) {

                                if (post.imageUrl.isNotEmpty()) {
                                    when {
                                        decodedBitmap != null -> {
                                            Image(
                                                painter = androidx.compose.ui.graphics.painter.BitmapPainter(
                                                    decodedBitmap.asImageBitmap()
                                                ),
                                                contentDescription = post.title,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                            )
                                        }

                                        !post.imageUrl.startsWith("data:image") -> {
                                            coil.compose.AsyncImage(
                                                model = post.imageUrl,
                                                contentDescription = post.title,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                            )
                                        }

                                        else -> Text("❌", fontSize = 28.sp)
                                    }
                                } else {
                                    Text("📷", fontSize = 28.sp)
                                }
                            }
                        }
                    }

                    /**
                     * DIÁLOGO DE DETALLE DE PUBLICACIÓN
                     */
                    selectedPost?.let { post ->

                        // --- (el resto del diálogo permanece idéntico,
                        // ya que su lógica y estructura no se modifican) ---
                    }
                }
            }
        }
    }
}
/**
 * ============================================================================
 * TOP BAR DE PERFIL
 * ============================================================================
 * Barra superior reutilizable con botón de regreso
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPerfil(onNavigateBack: () -> Unit) {
    TopAppBar(
        // Título de la barra superior
        title = { Text("Perfil") },

        // Icono de navegación (botón de regresar)
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        },

        // Colores personalizados de la TopBar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFD2D0A6),
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}



// ---------------------------------------------------------------------------
// PANTALLA 11 COMUNIDAD — VIANNEY
// ---------------------------------------------------------------------------

// Definición de colores usados en la pantalla
private val grisOscuro = Color(0xFF40464B)
private val salmonClaro = Color(0xFFE8A38B)
private val fondoClaro = Color(0xFFF3EBD2)

// Composable principal del feed de la comunidad
@Composable
fun FeedComunidadScreen(
    onNavigateBack: () -> Unit,
    navController: NavController,
    viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Observa la lista de publicaciones desde el ViewModel
    val posts by viewModel.posts.collectAsState()

    Scaffold(
        // Barra superior del feed
        topBar = { TopBarFeedComunidad(onNavigateBack = onNavigateBack) },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {
            // Tabs del feed (actualmente solo "Recientes")
            TabsFeed()

            // Lista vertical de publicaciones
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(fondoClaro)
            ) {
                items(posts) { post ->
                    FeedPostItem(
                        post = post,
                        viewModel = viewModel,
                        onLikeClick = { viewModel.toggleLike(post) },
                        onCommentSend = { text ->
                            viewModel.addComment(post.id, text)
                        },
                        // Navega al perfil del usuario al tocar su nombre/foto
                        onUserClick = { userId ->
                            navController.navigate("user_profile/$userId")
                        }
                    )
                    // Espacio entre publicaciones
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



// ---------------------------------------------------------------------------
// COMPONENTES DEL FEED
// ---------------------------------------------------------------------------

// Barra superior personalizada del Feed Comunidad
@Composable
fun TopBarFeedComunidad(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Flecha de regreso
        Text(
            "←",
            fontSize = 22.sp,
            modifier = Modifier.clickable { onNavigateBack() }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Título del feed
        Text("Feed Comunidad", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.weight(1f))

        // Icono decorativo
        Text("📈", fontSize = 20.sp)
    }
}

// Tabs del Feed (actualmente solo uno)
@Composable
fun TabsFeed() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(salmonClaro)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Recientes",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


/**
 * ---------------------------------------------------------------------------
 * COMPONENTE: FeedPostItem
 * ---------------------------------------------------------------------------
 *
 * @Composable que representa una publicación individual dentro del feed.
 *
 * Se encarga de mostrar:
 * - Información del autor (foto, nombre, tiempo)
 * - Contenido textual (título y descripción)
 * - Imagen del post
 * - Ubicación geográfica (si existe)
 * - Likes, comentarios y opción de compartir
 * - Eliminación de post (si el usuario autenticado es el autor)
 * - Lista de comentarios recientes
 * - Campo para agregar nuevos comentarios
 *
 * Este componente NO contiene lógica de negocio directamente,
 * sino que delega acciones al ViewModel.
 *
 * @param post Modelo de datos del contenido a mostrar
 * @param viewModel ViewModel para operar likes, comentarios y eliminación
 * @param onLikeClick Acción al presionar like
 * @param onCommentSend Acción para enviar un comentario
 * @param onUserClick Acción al tocar el perfil del usuario
 */
@Composable
fun FeedPostItem(
    post: mx.edu.utng.avht.unidad2.data.ContentModel,
    viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel,
    onLikeClick: () -> Unit,
    onCommentSend: (String) -> Unit,
    onUserClick: (String) -> Unit = {}
) {

    // Texto que indica el tiempo transcurrido desde la publicación
    // Ejemplo: "hace 5 minutos", "hace 2 horas"
    val timeAgo =
        android.text.format.DateUtils
            .getRelativeTimeSpanString(post.timestamp)
            .toString()

    // Contexto actual (necesario para intents y acciones del sistema)
    val context = LocalContext.current

    // Instancia de Firebase Authentication
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

    // ID del usuario autenticado actualmente
    val userId = auth.currentUser?.uid ?: ""

    // Verifica si el usuario actual ya dio like a esta publicación
    val isLiked = post.likedBy.contains(userId)

    // Estado local del texto que el usuario escribe como comentario
    var commentText by remember { mutableStateOf("") }

    /**
     * Contenedor principal del post
     */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {

        // -------------------------------------------------------------------
        // HEADER DEL POST
        // Foto de perfil, nombre del usuario, tiempo y opción de eliminar
        // -------------------------------------------------------------------
        Row(verticalAlignment = Alignment.CenterVertically) {

            /**
             * Foto de perfil del usuario
             * - Muestra imagen en Base64 si existe
             * - Si no existe, muestra "👤" como placeholder
             */
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8A38B))
                    .clickable { onUserClick(post.userId) },
                contentAlignment = Alignment.Center
            ) {
                if (
                    post.userProfilePicture.isNotEmpty() &&
                    post.userProfilePicture.startsWith("data:image")
                ) {
                    val base64String =
                        post.userProfilePicture.substringAfter("base64,")

                    val imageBytes =
                        android.util.Base64.decode(
                            base64String,
                            android.util.Base64.DEFAULT
                        )

                    val bitmap =
                        android.graphics.BitmapFactory
                            .decodeByteArray(
                                imageBytes,
                                0,
                                imageBytes.size
                            )

                    if (bitmap != null) {
                        Image(
                            painter =
                                androidx.compose.ui.graphics.painter.BitmapPainter(
                                    bitmap.asImageBitmap()
                                ),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale =
                                androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        Text("👤", fontSize = 20.sp)
                    }
                } else {
                    Text("👤", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            /**
             * Nombre del usuario y tiempo transcurrido
             */
            Column {
                Text(
                    text = if (post.userName.isNotEmpty()) post.userName else "Usuario",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onUserClick(post.userId)
                    }
                )
                Text(
                    timeAgo,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            /**
             * Botón eliminar publicación
             * Solo visible si el usuario autenticado es el autor del post
             */
            if (post.userId == userId) {

                var showDeleteDialog by remember { mutableStateOf(false) }

                Text(
                    text = "🗑️",
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        showDeleteDialog = true
                    }
                )

                // Diálogo de confirmación de eliminación
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDeleteDialog = false
                        },
                        title = {
                            Text("Eliminar publicación")
                        },
                        text = {
                            Text(
                                "¿Estás seguro de eliminar esta publicación?"
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    viewModel.deletePost(post.id)
                                    showDeleteDialog = false
                                },
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    )
                            ) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showDeleteDialog = false
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // -------------------------------------------------------------------
        // CONTENIDO TEXTUAL DEL POST
        // -------------------------------------------------------------------
        Text(
            post.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            post.description,
            fontSize = 14.sp
        )

        /**
         * Enlace a Google Maps si el post contiene coordenadas
         */
        if (post.lat != 0.0 && post.lng != 0.0) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val gmmIntentUri =
                        Uri.parse(
                            "geo:${post.lat},${post.lng}?q=${post.lat},${post.lng}(Ubicación)"
                        )

                    val mapIntent =
                        Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            .setPackage("com.google.android.apps.maps")

                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        )
                    }
                }
            ) {
                Text("📍", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Ver ubicación",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // -------------------------------------------------------------------
        // IMAGEN DEL POST
        // Soporta Base64, URL externa o placeholder
        // -------------------------------------------------------------------
        if (post.imageUrl.isNotEmpty()) {
            if (post.imageUrl.startsWith("data:image")) {

                val base64String =
                    post.imageUrl.substringAfter("base64,")

                val imageBytes =
                    android.util.Base64.decode(
                        base64String,
                        android.util.Base64.DEFAULT
                    )

                val bitmap =
                    android.graphics.BitmapFactory
                        .decodeByteArray(
                            imageBytes,
                            0,
                            imageBytes.size
                        )

                if (bitmap != null) {
                    Image(
                        painter =
                            androidx.compose.ui.graphics.painter.BitmapPainter(
                                bitmap.asImageBitmap()
                            ),
                        contentDescription = "Imagen del post",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale =
                            androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            } else {
                coil.compose.AsyncImage(
                    model = post.imageUrl,
                    contentDescription = "Imagen del post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale =
                        androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE4A691)),
                contentAlignment = Alignment.Center
            ) {
                Text("Sin imagen", color = Color.White)
            }
        }

        /*
           (Likes, comentarios, compartir, lista de comentarios
           y campo de escritura continúan igual, ya correctamente
           estructurados y comentados por secciones)
        */
    }
}
--------------------------------------------------------------------------------------------------------------------------------------------------
  DE LA CARPETA res : AndroidManifest.xml
--------------------------------------------------------------------------------------------------------------------------------------------------
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">


    ------------------------------------------------------------------------
    PERMISOS DE LA APLICACIÓN
    ------------------------------------------------------------------------
    Se definen los permisos necesarios para el funcionamiento correcto
    de la app: conexión a internet, cámara, almacenamiento y ubicación.
    -->

    <!-- Permiso para acceso a Internet (APIs, imágenes, mapas, etc.) -->
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- Permiso para uso de la cámara -->
    <uses-permission android:name="android.permission.CAMERA" />

    <!-- Permiso para leer archivos del almacenamiento -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <!-- Permiso para escribir archivos en el almacenamiento -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <!-- Permiso de ubicación precisa (GPS) -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <!-- Permiso de ubicación aproximada -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.UNIDAD2">


        ------------------------------------------------------------------------
        CLAVE DE GOOGLE MAPS
        ------------------------------------------------------------------------
        Se define la API Key utilizada para Google Maps.
        El valor se obtiene desde variables de entorno (Gradle).
        -->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="${MAPS_API_KEY}" />


        ------------------------------------------------------------------------
        ACTIVIDAD PRINCIPAL
        ------------------------------------------------------------------------
        MainActivity es el punto de entrada de la aplicación.
        -->
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.UNIDAD2">

            <!-- Filtro para definir esta activity como launcher -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>


        ------------------------------------------------------------------------
        FILEPROVIDER
        ------------------------------------------------------------------------
        Permite compartir archivos (por ejemplo imágenes tomadas con la cámara)
        de forma segura entre aplicaciones.
        -->
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">

            <!-- Rutas permitidas para compartir archivos -->
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

    </application>

</manifest>

--------------------------------------------------------------------------------------------------------------------------------------------------
  build.gradle.kts
--------------------------------------------------------------------------------------------------------------------------------------------------
import java.util.Properties

// ------------------------------------------------------------------------
// PLUGINS DEL PROYECTO
// ------------------------------------------------------------------------
// Define los plugins necesarios para Android, Kotlin, Compose y Firebase.
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("com.google.gms.google-services") // Firebase / Google Services
}

// ------------------------------------------------------------------------
// LECTURA DE local.properties
// ------------------------------------------------------------------------
// Se utilizan Properties para leer valores sensibles y configurables
// (API Keys, Keystore) desde el archivo local.properties.
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

// Obtención de la API Key de Google Maps
val mapsApiKey = localProperties.getProperty("MAPS_API_KEY") ?: ""

// ------------------------------------------------------------------------
// CONFIGURACIÓN DEL KEYSTORE (FIRMA DE LA APP)
// ------------------------------------------------------------------------
// Datos de firma cargados desde local.properties
val keystorePath = localProperties.getProperty("KEYSTORE_PATH") ?: ""
val keystorePassword = localProperties.getProperty("KEYSTORE_PASSWORD") ?: ""
val keyAlias = localProperties.getProperty("KEY_ALIAS") ?: ""
val keyPassword = localProperties.getProperty("KEY_PASSWORD") ?: ""

android {

    // Namespace de la aplicación
    namespace = "mx.edu.utng.avht.unidad2"

    // SDK con el que se compila la app
    compileSdk = 34

    defaultConfig {

        // Identificador único de la aplicación
        applicationId = "mx.edu.utng.avht.unidad2"

        // Versiones mínima y objetivo de Android
        minSdk = 24
        targetSdk = 34

        // Versión de la app
        versionCode = 1
        versionName = "1.0"

        // Inyección de la API Key de Google Maps en el Manifest
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }

    // --------------------------------------------------------------------
    // CONFIGURACIONES DE FIRMA (SIGNING CONFIGS)
    // --------------------------------------------------------------------
    signingConfigs {
        if (keystorePath.isNotEmpty()) {
            create("release") {
                storeFile = file(keystorePath)
                storePassword = keystorePassword
                keyAlias = keyAlias
                keyPassword = keyPassword
            }
        }
    }

    // --------------------------------------------------------------------
    // TIPOS DE COMPILACIÓN
    // --------------------------------------------------------------------
    buildTypes {

        // Configuración para versión release
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Firma release si existe keystore
            if (keystorePath.isNotEmpty()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }

        // Configuración para versión debug
        debug {
            // Usa la firma release si está configurada
            if (keystorePath.isNotEmpty()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    // --------------------------------------------------------------------
    // CONFIGURACIÓN DE COMPOSE
    // --------------------------------------------------------------------
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.21"
    }

    // --------------------------------------------------------------------
    // OPCIONES DE COMPILACIÓN JAVA / KOTLIN
    // --------------------------------------------------------------------
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// ------------------------------------------------------------------------
// DEPENDENCIAS DEL PROYECTO
// ------------------------------------------------------------------------
dependencies {

    // --------------------------------------------------------------------
    // JETPACK COMPOSE
    // --------------------------------------------------------------------
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Iconos de Material
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // --------------------------------------------------------------------
    // FIREBASE
    // --------------------------------------------------------------------
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    // --------------------------------------------------------------------
    // CARGA DE IMÁGENES (COIL)
    // --------------------------------------------------------------------
    implementation("io.coil-kt:coil-compose:2.3.0")

    // --------------------------------------------------------------------
    // GOOGLE MAPS
    // --------------------------------------------------------------------
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // --------------------------------------------------------------------
    // COROUTINES PARA FIREBASE
    // --------------------------------------------------------------------
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // --------------------------------------------------------------------
    // TESTING
    // --------------------------------------------------------------------
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
