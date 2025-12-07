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
