package mx.edu.utng.avht.unidad2.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import mx.edu.utng.avht.unidad2.BottomNav
import mx.edu.utng.avht.unidad2.TopBar

@Composable
fun MapaPrincipalScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToComunidad: () -> Unit = {},
    onNavigateToContenido: (Double, Double) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(21.1619, -100.9300), 10f) // Dolores Hidalgo default
    }

    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> hasLocationPermission = isGranted }
    )

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomNav(
                onNavigateToPerfil = onNavigateToPerfil,
                onNavigateToComunidad = onNavigateToComunidad
            )
        },
        floatingActionButton = {
            if (selectedLocation != null) {
                FloatingActionButton(
                    onClick = {
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
        containerColor = Color(0xFFF7EFD8)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
                uiSettings = MapUiSettings(myLocationButtonEnabled = true),
                onMapClick = { latLng ->
                    selectedLocation = latLng
                },
                onMapLongClick = { latLng ->
                    selectedLocation = latLng
                }
            ) {
                selectedLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Ubicación seleccionada",
                        snippet = "Agregar contenido aquí"
                    )
                }
            }

            if (selectedLocation == null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
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
