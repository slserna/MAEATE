package mx.edu.utng.avht.unidad2

// Android & Compose básico
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.text.style.TextDecoration
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
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

// los que batallo
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

/* Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

 */

// ViewModels
import mx.edu.utng.avht.unidad2.screens.MapaPrincipalScreen
import mx.edu.utng.avht.unidad2.screens.NuevoContenidoScreen
import mx.edu.utng.avht.unidad2.screens.UserProfileScreen
import mx.edu.utng.avht.unidad2.viewmodel.LoginViewModel
import mx.edu.utng.avht.unidad2.viewmodel.PerfilViewModel

// Java / IO
import java.io.File
import java.io.FileOutputStream
import java.util.*




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

// 1. PRIMERO: Define las rutas de navegación
// 1. PRIMERO: Define las rutas de navegación
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Principal : Screen("principal")
    object MapaPrincipal : Screen("mapa_principal")
    object Perfil : Screen("perfil")
    object Comunidad : Screen("comunidad")
    object Contenido : Screen("contenido/{lat}/{lng}") {
        fun createRoute(lat: Double, lng: Double) = "contenido/$lat/$lng"
    }
    object Register : Screen("register_screen")
}

// 2. CONFIGURACION DE NAVEGACION
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
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

        composable(Screen.Register.route) {
            RegisterScreen(
                onBack = { navController.popBackStack() }
            )
        }

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

        composable(Screen.MapaPrincipal.route) {
            mx.edu.utng.avht.unidad2.screens.MapaPrincipalScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPerfil = { navController.navigate(Screen.Perfil.route) },
                onNavigateToComunidad = { navController.navigate(Screen.Comunidad.route) },
                onNavigateToContenido = { lat, lng ->
                    navController.navigate(Screen.Contenido.createRoute(lat, lng))
                }
            )
        }

        composable(
            route = Screen.Contenido.route,
            arguments = listOf(
                androidx.navigation.navArgument("lat") { type = androidx.navigation.NavType.FloatType },
                androidx.navigation.navArgument("lng") { type = androidx.navigation.NavType.FloatType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStackEntry.arguments?.getFloat("lng")?.toDouble() ?: 0.0
            mx.edu.utng.avht.unidad2.screens.NuevoContenidoScreen(
                lat = lat,
                lng = lng,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilUsuarioScreen(navController = navController)
        }

        composable(Screen.Comunidad.route) {
            FeedComunidadScreen(onNavigateBack = { navController.popBackStack() }, navController = navController)
        }
        
        composable(
            route = "user_profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserProfileScreen(
                userId = userId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        containerColor = Color(0xFFD2D0A6) // color de fondo que ya tenías
    ) { padding ->

        if (state.isLoginSuccessful) {
            LaunchedEffect(Unit) { onLoginSuccess() }
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
                "Iniciar sesión",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // EMAIL
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PASSWORD
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🚀 BOTÓN CON TU COLOR *SALMÓN*
            Button(
                onClick = { viewModel.onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE4A691) // <- SALMÓN
                )
            ) {
                Text("Iniciar sesión", color = Color.White)
            }

            // ERROR
            state.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }

            Spacer(Modifier.height(20.dp))

            // 👉 BOTÓN PARA REGISTRO
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Crear cuenta", fontSize = 16.sp)
            }
        }
    }
}

// PANTALLA 1 REGISTRARSE --- SARA
@Composable
fun RegisterScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    // 👉 Estado local para el nombre
    var nombre by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xFFF7EFD8) // crema
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }

            Spacer(Modifier.height(10.dp))

            Text("📝", fontSize = 50.sp)
            Text("Regístrate", fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(40.dp))

            // 👉 NOMBRE editable
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // CORREO
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // CONTRASEÑA
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onRegisterClick()   // Guardar en Firebase
                    onBack()                      // Regresar al login
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE4A691) // salmon
                )
            ) {
                Text("Registrarme", color = Color.White)
            }

            state.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }
        }
    }
}


//PANTALLA 2 - BIENVENIDA ----- SARA
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PrincipalGto(
    onExplorarRutas: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToComunidad: () -> Unit = {}
) {
    val salmon = Color(0xFFE4A691)
    val crema = Color(0xFFF7EFD8)
    val verdeSuave = Color(0xFFC8C8A9)
    val azulGris = Color(0xFF556270)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🔵 IMAGEN DE FONDO
        Image(
            painter = painterResource(id = R.drawable.gto_bonito),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🔵 TODO CENTRADO Y SUBIDO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 🟩 CUADRO VERDE DETRÁS DE “MAPEATE”
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(verdeSuave.copy(alpha = 0.88f))  // suave y visible
                    .padding(horizontal = 28.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "🗺️ MAPEATE",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(50.dp)) // espacio encima de botones

            // 🔵 BOTONES MÁS ARRIBA Y ORDENADOS
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onExplorarRutas() },
                    colors = ButtonDefaults.buttonColors(containerColor = crema),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(70.dp)
                ) {
                    Text("📍 Explorar rutas", color = azulGris)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onNavigateToComunidad() },
                    colors = ButtonDefaults.buttonColors(containerColor = salmon),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(70.dp)
                ) {
                    Text("\uD83C\uDFD8\uFE0F Comunidad", color = azulGris)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onNavigateToPerfil() },
                    colors = ButtonDefaults.buttonColors(containerColor = azulGris),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(70.dp)
                ) {
                    Text("👤 Mi perfil", color = crema)
                }
            }
        }
    }
}



/*PANTALLA de mapprincipal----VIANNEY
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
*/
//PANTALLA 4 DETALLE RUTA-------SARA
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DetalleRuta() {
    val salmon = Color(0xFFE4A691)
    val crema = Color(0xFFF7EFD8)
    val verdeSuave = Color(0xFFC8C8A9)
    val azulGris = Color(0xFF556270)
    val azulOscuro = Color(0xFF273142)

    Column(
        modifier = Modifier
            .fillMaxSize()        // ← SE ADAPTA A TODA LA PANTALLA
            .background(crema)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
    ) {
        //Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(crema)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = azulOscuro,
                fontSize = 22.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Detalle de Ruta",
                color = azulOscuro,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Header imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(salmon),
            contentAlignment = Alignment.Center
        ) {
            Text("Imagen del lugar", color = azulOscuro, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(azulOscuro, RoundedCornerShape(6.dp))
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text("Nombre del lugar", color = crema, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ubicación
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("📍", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .background(verdeSuave, RoundedCornerShape(6.dp))
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text("Ubicación de la ruta", color = azulOscuro, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Descripción
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

        // Detalles extra
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(crema, RoundedCornerShape(12.dp))
                .border(1.dp, azulGris, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .background(azulGris, RoundedCornerShape(4.dp))
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text("Detalles adicionales", color = crema, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
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

        // Botón y opciones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = salmon),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            ) {
                Text("🚀 Iniciar ruta", color = azulOscuro, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.width(12.dp))

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

        // Comentarios
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Comentarios", color = azulOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            repeat(2) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(crema, RoundedCornerShape(12.dp))
                        .border(1.dp, verdeSuave, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (index == 0) salmon else azulGris,
                                CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
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
                    Text(if (index == 0) "❤" else "🤍")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

//PANTALLA 5 MEME LOCAL-------VIANNEY
@Composable
@Preview(showBackground = true)
fun MemeLocalScreen() {
    Scaffold(
        topBar = { TopBarMemeLocal() },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Sección del meme o imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Meme / Imagen", color = Color.Gray)
                    Text("😂", fontSize = 50.sp)
                }
            }

            // Sección inferior con información
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3EBD2))
                    .padding(16.dp)
            ) {
                // Autor
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8A38B))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Nombre de usuario", fontWeight = FontWeight.Bold)
                        Text("📍 Ciudad o ubicación", fontSize = 12.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("2h", color = Color.Gray, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF8D8D8D))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFD9D8B4))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Likes, comentarios y compartir
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("❤️ 124", modifier = Modifier.padding(end = 12.dp))
                    Text("💬 43", modifier = Modifier.padding(end = 12.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    Text("🔗 Compartir", fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Comentarios", fontWeight = FontWeight.Bold)

                // Comentarios simulados
                Spacer(modifier = Modifier.height(8.dp))
                ComentarioItem("Usuario1", "Muy bueno 😂")
                ComentarioItem("Usuario2", "Jajaja, me identifiqué 😆")
            }
        }
    }
}

@Composable
fun TopBarMemeLocal() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("←", fontSize = 22.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Meme Local", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun ComentarioItem(usuario: String, comentario: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(Color(0xFFDFAEA5))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFE8E5B2))
                .padding(8.dp)
        ) {
            Text(usuario, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(comentario, fontSize = 12.sp)
        }
    }
}


@Composable
fun MemeLocalPreview() {
    MemeLocalScreen()
}

@Composable
fun PerfilUsuarioScreen(
    navController: NavController,
    perfilViewModel: PerfilViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Obtenemos username, email y bio desde el ViewModel
    val username by perfilViewModel.username.collectAsState()
    val email by perfilViewModel.email.collectAsState()
    val bio by perfilViewModel.bio.collectAsState()

    // Estado local para el TextField editable
    var bioText by remember { mutableStateOf("") }

    // Sincronizamos bioText con el StateFlow cada vez que cambia
    LaunchedEffect(bio) {
        bioText = bio
    }

    Scaffold(
        topBar = { TopBarPerfil(onNavigateBack = { navController.popBackStack() }) },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {

            // -------- CAJA PRINCIPAL ----------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // FOTO - Image picker
                    val context = LocalContext.current
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        uri?.let {
                            perfilViewModel.updateProfilePicture(it, context)
                        }
                    }
                    
                    val profilePic = perfilViewModel.profilePicture.collectAsState()
                    
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8A38B))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (profilePic.value.isNotEmpty() && profilePic.value.startsWith("data:image")) {
                            val base64String = profilePic.value.substringAfter("base64,")
                            val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
                            val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            
                            if (bitmap != null) {
                                Image(
                                    painter = androidx.compose.ui.graphics.painter.BitmapPainter(bitmap.asImageBitmap()),
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

                    // 🔥 Nombre real / Loading 🔥
                    if (username == "Cargando...") {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = username,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        // Email debajo del nombre
                        if (email.isNotBlank()) {
                            Text(
                                text = email,
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Campo editable de la descripción/bio
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

                        // Botón para guardar la descripción
                        Button(
                            onClick = { perfilViewModel.updateBio(bioText) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC8C8A9),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally) // centrado
                        ) {
                            Text("Guardar descripción")
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Botón cerrar sesión
                        Button(
                            onClick = {
                                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
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

            // -------- PUBLICACIONES --------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3EBD2))
                    .padding(16.dp)
            ) {
                Text("Mis Publicaciones", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))

                var userPosts by remember { mutableStateOf<List<mx.edu.utng.avht.unidad2.data.ContentModel>>(emptyList()) }
                val contentViewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
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
                    var selectedPost by remember { mutableStateOf<mx.edu.utng.avht.unidad2.data.ContentModel?>(null) }
                    
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userPosts) { post ->
                            val decodedBitmap = remember(post.imageUrl) {
                                if (post.imageUrl.startsWith("data:image")) {
                                    try {
                                        val base64String = post.imageUrl.substringAfter("base64,")
                                        val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
                                        android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                    } catch (e: Exception) {
                                        null
                                    }
                                } else {
                                    null
                                }
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
                                    if (decodedBitmap != null) {
                                        // Base64 image decoded
                                        Image(
                                            painter = androidx.compose.ui.graphics.painter.BitmapPainter(decodedBitmap.asImageBitmap()),
                                            contentDescription = post.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else if (!post.imageUrl.startsWith("data:image")) {
                                        // URL image
                                        coil.compose.AsyncImage(
                                            model = post.imageUrl,
                                            contentDescription = post.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        // Error loading Base64
                                        Text("❌", fontSize = 28.sp)
                                    }
                                } else {
                                    // No image placeholder
                                    Text("📷", fontSize = 28.sp)
                                }
                            }
                        }
                    }
                    
                    // Dialog para mostrar publicación completa
                    selectedPost?.let { post ->
                        androidx.compose.ui.window.Dialog(onDismissRequest = { selectedPost = null }) {
                            androidx.compose.material3.Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .padding(16.dp)
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    // Imagen
                                    if (post.imageUrl.isNotEmpty()) {
                                        val dialogBitmap = remember(post.imageUrl) {
                                            if (post.imageUrl.startsWith("data:image")) {
                                                try {
                                                    val base64String = post.imageUrl.substringAfter("base64,")
                                                    val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
                                                    android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                                } catch (e: Exception) {
                                                    null
                                                }
                                            } else {
                                                null
                                            }
                                        }
                                        
                                        if (dialogBitmap != null) {
                                            Image(
                                                painter = androidx.compose.ui.graphics.painter.BitmapPainter(dialogBitmap.asImageBitmap()),
                                                contentDescription = post.title,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(250.dp)
                                                    .clip(RoundedCornerShape(12.dp)),
                                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                            )
                                        } else if (!post.imageUrl.startsWith("data:image")) {
                                            coil.compose.AsyncImage(
                                                model = post.imageUrl,
                                                contentDescription = post.title,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(250.dp)
                                                    .clip(RoundedCornerShape(12.dp)),
                                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                            )
                                        }
                                    }
                                    
                                    Spacer(modifier = Modifier.height(16.dp))
                                    
                                    // Título
                                    Text(
                                        text = post.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    // Descripción
                                    Text(
                                        text = post.description,
                                        fontSize = 14.sp
                                    )
                                    
                                    // Ubicación
                                    if (post.lat != 0.0 && post.lng != 0.0) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        val context = LocalContext.current
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable {
                                                val gmmIntentUri = Uri.parse("geo:${post.lat},${post.lng}?q=${post.lat},${post.lng}(Ubicación)")
                                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                mapIntent.setPackage("com.google.android.apps.maps")
                                                try {
                                                    context.startActivity(mapIntent)
                                                } catch (e: Exception) {
                                                    val mapIntentBrowser = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                    context.startActivity(mapIntentBrowser)
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
                                    
                                    Spacer(modifier = Modifier.height(16.dp))
                                    
                                    // Botones
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // Botón eliminar (solo si es el autor)
                                        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
                                        if (post.userId == auth.currentUser?.uid) {
                                            var showDeleteConfirm by remember { mutableStateOf(false) }
                                            
                                            Button(
                                                onClick = { showDeleteConfirm = true },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color.Red
                                                )
                                            ) {
                                                Text("🗑️ Eliminar")
                                            }
                                            
                                            if (showDeleteConfirm) {
                                                androidx.compose.material3.AlertDialog(
                                                    onDismissRequest = { showDeleteConfirm = false },
                                                    title = { Text("Confirmar eliminación") },
                                                    text = { Text("¿Estás seguro de eliminar esta publicación?") },
                                                    confirmButton = {
                                                        Button(
                                                            onClick = {
                                                                contentViewModel.deletePost(post.id,
                                                                    onSuccess = {
                                                                        showDeleteConfirm = false
                                                                        selectedPost = null
                                                                    }
                                                                )
                                                            },
                                                            colors = ButtonDefaults.buttonColors(
                                                                containerColor = Color.Red
                                                            )
                                                        ) {
                                                            Text("Eliminar")
                                                        }
                                                    },
                                                    dismissButton = {
                                                        Button(onClick = { showDeleteConfirm = false }) {
                                                            Text("Cancelar")
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                        
                                        Spacer(modifier = Modifier.weight(1f))
                                        
                                        // Botón cerrar
                                        Button(
                                            onClick = { selectedPost = null }
                                        ) {
                                            Text("Cerrar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------- TOP BAR BÁSICO --------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPerfil(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = { Text("Perfil") },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFD2D0A6),
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}




// PANTALLA 11 COMUNIDAD-----VIANNEY

// Definición de colores
private val grisOscuro = Color(0xFF40464B)
private val salmonClaro = Color(0xFFE8A38B)
private val fondoClaro = Color(0xFFF3EBD2)

//PANTALLA 11 COMUNIDAD-----VIANNEY
@Composable
fun FeedComunidadScreen(
    onNavigateBack: () -> Unit,
    navController: NavController,
    viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val posts by viewModel.posts.collectAsState()

    Scaffold(
        topBar = { TopBarFeedComunidad(onNavigateBack = onNavigateBack) },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {
            // CORRECCIÓN: Se llama a la función TabsFeed()
            TabsFeed()

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
                        onCommentSend = { text -> viewModel.addComment(post.id, text) },
                        onUserClick = { userId ->
                            navController.navigate("user_profile/$userId")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Espacio entre posts
                }
            }
        }
    }
}

// --------------------------------------------------------------------------
// ## Componentes
// --------------------------------------------------------------------------

@Composable
fun TopBarFeedComunidad(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "←",
            fontSize = 22.sp,
            modifier = Modifier.clickable { onNavigateBack() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Feed Comunidad", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text("📈", fontSize = 20.sp)
    }
}

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
            Text("Recientes", color = Color.White, fontWeight = FontWeight.Bold)
        }


    }
}

@Composable
fun FeedPostItem(
    post: mx.edu.utng.avht.unidad2.data.ContentModel,
    viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel,
    onLikeClick: () -> Unit,
    onCommentSend: (String) -> Unit,
    onUserClick: (String) -> Unit = {}
) {
    val timeAgo = android.text.format.DateUtils.getRelativeTimeSpanString(post.timestamp).toString()
    val context = LocalContext.current
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid ?: ""
    val isLiked = post.likedBy.contains(userId)
    
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header del post (Avatar + Nombre + Tiempo)
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Foto de perfil
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8A38B))
                    .clickable { onUserClick(post.userId) },
                contentAlignment = Alignment.Center
            ) {
                if (post.userProfilePicture.isNotEmpty() && post.userProfilePicture.startsWith("data:image")) {
                    val base64String = post.userProfilePicture.substringAfter("base64,")
                    val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
                    val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    
                    if (bitmap != null) {
                        Image(
                            painter = androidx.compose.ui.graphics.painter.BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        Text("👤", fontSize = 20.sp)
                    }
                } else {
                    Text("👤", fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = if (post.userName.isNotEmpty()) post.userName else "Usuario",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onUserClick(post.userId) }
                )
                Text(timeAgo, fontSize = 12.sp, color = Color.Gray)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Botón eliminar (solo si es el autor)
            if (post.userId == userId) {
                var showDeleteDialog by remember { mutableStateOf(false) }
                
                Text(
                    text = "🗑️",
                    fontSize = 20.sp,
                    modifier = Modifier.clickable { showDeleteDialog = true }
                )
                
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Eliminar publicación") },
                        text = { Text("¿Estás seguro de eliminar esta publicación?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    viewModel.deletePost(post.id)
                                    showDeleteDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Título y Descripción
        Text(post.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(post.description, fontSize = 14.sp)

        // Ubicación
        if (post.lat != 0.0 && post.lng != 0.0) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val gmmIntentUri = Uri.parse("geo:${post.lat},${post.lng}?q=${post.lat},${post.lng}(Ubicación)")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                         val mapIntentBrowser = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                         context.startActivity(mapIntentBrowser)
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

        // Imagen del post
        if (post.imageUrl.isNotEmpty()) {
            if (post.imageUrl.startsWith("data:image")) {
                // Base64 image
                val base64String = post.imageUrl.substringAfter("base64,")
                val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
                val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                
                if (bitmap != null) {
                    Image(
                        painter = androidx.compose.ui.graphics.painter.BitmapPainter(bitmap.asImageBitmap()),
                        contentDescription = "Imagen del post",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            } else {
                // URL image (from Firebase Storage or other)
                coil.compose.AsyncImage(
                    model = post.imageUrl,
                    contentDescription = "Imagen del post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        } else {
            // Placeholder si no hay imagen
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

        Spacer(modifier = Modifier.height(12.dp))

        // Likes y comentarios
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                // Likes
                val userId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val isLiked = post.likedBy.contains(userId)
                
                Text(
                    text = if (isLiked) "❤️ ${post.likesCount}" else "🤍 ${post.likesCount}",
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onLikeClick() }
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Comentarios
                Text("💬 ${post.commentsCount}", fontSize = 14.sp)
            }
            
            // Botón compartir
            val context = LocalContext.current
            Text(
                text = "🔗 Compartir",
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.clickable {
                    val shareText = buildString {
                        append("${post.title}\n\n")
                        append("${post.description}\n\n")
                        if (post.lat != 0.0 && post.lng != 0.0) {
                            append("📍 Ubicación: https://maps.google.com/?q=${post.lat},${post.lng}")
                        }
                    }
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Compartir publicación"))
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Comentarios
        val comments = remember { mutableStateOf<List<mx.edu.utng.avht.unidad2.data.CommentModel>>(emptyList()) }
        
        LaunchedEffect(post.id) {
            viewModel.fetchCommentsForPost(post.id) { fetchedComments ->
                comments.value = fetchedComments
            }
        }
        
        if (comments.value.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                comments.value.forEach { comment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                comment.userName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                comment.text,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Input comentario
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            
            androidx.compose.foundation.text.BasicTextField(
                value = commentText,
                onValueChange = { commentText = it },
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (commentText.isEmpty()) Text("Escribe un comentario...", color = Color.Gray, fontSize = 14.sp)
                    innerTextField()
                }
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "↗️", 
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    if (commentText.isNotBlank()) {
                        onCommentSend(commentText)
                        commentText = ""
                    }
                }
            )
        }
    }
}
