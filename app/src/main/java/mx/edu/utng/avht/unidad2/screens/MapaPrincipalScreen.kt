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
