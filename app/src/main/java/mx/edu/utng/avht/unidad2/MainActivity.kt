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

// los Icons
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

/**
 * ------------------------------------------------------------------------
 * DEFINICIÓN DE RUTAS DE NAVEGACIÓN
 * ------------------------------------------------------------------------
 * Clase sellada que centraliza todas las rutas de navegación de la aplicación.
 * Cada objeto representa una pantalla y su ruta asociada dentro del NavHost.
 */
sealed class Screen(val route: String) {

    /** Pantalla de inicio de sesión */
    object Login : Screen("login")

    /** Pantalla principal después del login */
    object Principal : Screen("principal")

    /** Pantalla del mapa principal */
    object MapaPrincipal : Screen("mapa_principal")

    /** Pantalla del perfil del usuario */
    object Perfil : Screen("perfil")

    /** Pantalla de la comunidad */
    object Comunidad : Screen("comunidad")

    /**
     * Pantalla de contenido dinámico que recibe latitud y longitud
     * como parámetros en la ruta.
     */
    object Contenido : Screen("contenido/{lat}/{lng}") {
        /**
         * Construye la ruta completa con los parámetros requeridos.
         */
        fun createRoute(lat: Double, lng: Double) = "contenido/$lat/$lng"
    }

    /** Pantalla de registro de usuario */
    object Register : Screen("register_screen")
}

/**
 * ------------------------------------------------------------------------
 * CONFIGURACIÓN PRINCIPAL DE NAVEGACIÓN
 * ------------------------------------------------------------------------
 * Define el NavController y el NavHost, así como todas las rutas y
 * transiciones entre pantallas de la aplicación.
 */
@Composable
fun AppNavigation() {

    // Controlador de navegación
    val navController = rememberNavController()

    // Contenedor principal de navegación
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
                    // Navega a la pantalla principal y elimina login del back stack
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
         * Pantalla del Mapa Principal
         */
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

        /**
         * Pantalla de Contenido con parámetros de ubicación
         */
        composable(
            route = Screen.Contenido.route,
            arguments = listOf(
                androidx.navigation.navArgument("lat") {
                    type = androidx.navigation.NavType.FloatType
                },
                androidx.navigation.navArgument("lng") {
                    type = androidx.navigation.NavType.FloatType
                }
            )
        ) { backStackEntry ->

            // Obtención segura de los argumentos
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStackEntry.arguments?.getFloat("lng")?.toDouble() ?: 0.0

            mx.edu.utng.avht.unidad2.screens.NuevoContenidoScreen(
                lat = lat,
                lng = lng,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        /**
         * Pantalla de Perfil del Usuario
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
         * Pantalla de perfil de otro usuario (ID dinámico)
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
 * Interfaz que permite al usuario iniciar sesión o dirigirse
 * a la pantalla de registro.
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {

    // Estado de la UI obtenido desde el ViewModel
    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        /**
         * Si el inicio de sesión es exitoso,
         * se navega automáticamente a la pantalla principal.
         */
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

            // Ícono decorativo
            Text("🔐", fontSize = 50.sp)
            Spacer(Modifier.height(8.dp))

            // Título
            Text(
                text = "Iniciar sesión",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            /**
             * Campo de correo electrónico
             */
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Campo de contraseña
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
             * Botón para iniciar sesión
             */
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

            /**
             * Mensaje de error, en caso de existir
             */
            state.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }

            Spacer(Modifier.height(20.dp))

            /**
             * Navegación a la pantalla de registro
             */
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Crear cuenta", fontSize = 16.sp)
            }
        }
    }
}


/**
 * ------------------------------------------------------------------------
 * PANTALLA DE REGISTRO DE USUARIO
 * ------------------------------------------------------------------------
 * Permite al usuario crear una nueva cuenta proporcionando
 * nombre de usuario, correo electrónico y contraseña.
 * Incluye navegación para regresar a la pantalla de login.
 */
@Composable
fun RegisterScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit = {}
) {

    // Estado de la interfaz proveniente del ViewModel
    val state = viewModel.uiState.collectAsState().value

    /**
     * Estado local para almacenar el nombre de usuario.
     * Este dato es independiente al estado manejado por el ViewModel.
     */
    var nombre by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xFFF7EFD8) // Color de fondo crema
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /**
             * Barra superior con botón de retroceso
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

            // Icono y título de la pantalla
            Text("📝", fontSize = 50.sp)
            Text(
                text = "Regístrate",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            /**
             * Campo para ingresar el nombre de usuario
             */
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Campo para ingresar el correo electrónico
             */
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Campo para ingresar la contraseña
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
             * Botón para registrar al usuario y volver al login
             */
            Button(
                onClick = {
                    viewModel.onRegisterClick() // Ejecuta el proceso de registro
                    onBack()                    // Retorna a la pantalla de login
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
             * Mostrar mensaje de error en caso de fallo
             */
            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = Color.Red)
            }
        }
    }
}

/**
 * ------------------------------------------------------------------------
 * PANTALLA PRINCIPAL / BIENVENIDA
 * ------------------------------------------------------------------------
 * Pantalla inicial tras el inicio de sesión.
 * Permite la navegación hacia:
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

    // Paleta de colores utilizada en la pantalla
    val salmon = Color(0xFFE4A691)
    val crema = Color(0xFFF7EFD8)
    val verdeSuave = Color(0xFFC8C8A9)
    val azulGris = Color(0xFF556270)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        /**
         * Imagen de fondo a pantalla completa
         */
        Image(
            painter = painterResource(id = R.drawable.gto_bonito),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        /**
         * Contenedor principal del contenido centrado
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            /**
             * Contenedor decorativo del título principal
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
             * Contenedor de botones de navegación principal
             */
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Botón para explorar rutas
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

                // Botón para la comunidad
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

                // Botón para el perfil de usuario
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
 * ------------------------------------------------------------------------
 * PANTALLA DE DETALLE DE RUTA
 * ------------------------------------------------------------------------
 * Muestra la información detallada de una ruta seleccionada.
 * Incluye encabezado, imagen referencial, información general,
 * detalles adicionales, acciones principales y sección de comentarios.
 */
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DetalleRuta() {

    /**
     * Paleta de colores utilizada en la interfaz
     */
    val salmon = Color(0xFFE4A691)
    val crema = Color(0xFFF7EFD8)
    val verdeSuave = Color(0xFFC8C8A9)
    val azulGris = Color(0xFF556270)
    val azulOscuro = Color(0xFF273142)

    /**
     * Contenedor principal que ocupa toda la pantalla y permite desplazamiento vertical
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(crema)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
    ) {

        /**
         * ENCABEZADO SUPERIOR
         * Incluye botón visual de regreso y título de la pantalla
         */
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

        /**
         * SECCIÓN DE IMAGEN PRINCIPAL
         * Representa visualmente el lugar de la ruta
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
         * TÍTULO DEL LUGAR
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
         * SECCIÓN DE UBICACIÓN DE LA RUTA
         */
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
                Text(
                    text = "Ubicación de la ruta",
                    color = azulOscuro,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        /**
         * DESCRIPCIÓN GENERAL DE LA RUTA
         * Simulación de contenido mediante bloques visuales
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
         * SECCIÓN DE DETALLES ADICIONALES
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

                // Título de la sección
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

                // Información simulada
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
         * BOTÓN PRINCIPAL Y OPCIONES RÁPIDAS
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Botón para iniciar la ruta
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

            /**
             * Acciones secundarias: guardar y compartir
             */
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
         * SECCIÓN DE COMENTARIOS
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
             * Lista simulada de comentarios
             */
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

                    // Indicador de reacción
                    Text(if (index == 0) "❤" else "🤍")
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


/**
 * ------------------------------------------------------------------------
 * PANTALLA DE PERFIL DEL USUARIO
 * ------------------------------------------------------------------------
 * Muestra la información del perfil del usuario autenticado.
 * Permite:
 *  - Visualizar y editar la biografía
 *  - Cambiar la foto de perfil
 *  - Cerrar sesión
 *  - Visualizar, abrir y eliminar publicaciones propias
 */
@Composable
fun PerfilUsuarioScreen(
    navController: NavController,
    perfilViewModel: PerfilViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    /**
     * Estados expuestos por el ViewModel del perfil
     */
    val username by perfilViewModel.username.collectAsState()
    val email by perfilViewModel.email.collectAsState()
    val bio by perfilViewModel.bio.collectAsState()

    /**
     * Estado local para el campo editable de la biografía
     */
    var bioText by remember { mutableStateOf("") }

    /**
     * Sincroniza el texto editable cuando cambia el estado original
     */
    LaunchedEffect(bio) {
        bioText = bio
    }

    Scaffold(
        topBar = {
            TopBarPerfil(
                onNavigateBack = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFD2D0A6)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {

            /**
             * ----------------------------------------------------------------
             * SECCIÓN SUPERIOR: INFORMACIÓN DEL PERFIL
             * ----------------------------------------------------------------
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    /**
                     * SELECCIÓN Y VISUALIZACIÓN DE FOTO DE PERFIL
                     */
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

// -------- TOP BAR BÁSICO --------
// Barra superior reutilizable para la pantalla de perfil
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



// ---------------------------------------------------------------------------
// ITEM INDIVIDUAL DE PUBLICACIÓN
// ---------------------------------------------------------------------------

@Composable
fun FeedPostItem(
    post: mx.edu.utng.avht.unidad2.data.ContentModel,
    viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel,
    onLikeClick: () -> Unit,
    onCommentSend: (String) -> Unit,
    onUserClick: (String) -> Unit = {}
) {
    // Texto del tiempo transcurrido (ej. "hace 2 horas")
    val timeAgo =
        android.text.format.DateUtils.getRelativeTimeSpanString(post.timestamp).toString()

    val context = LocalContext.current

    // Usuario actual autenticado
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid ?: ""

    // Verifica si el usuario ya dio like
    val isLiked = post.likedBy.contains(userId)

    // Estado local del texto del comentario
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {

        // -------------------------------------------------------------------
        // Header del post (foto, nombre, tiempo, eliminar)
        // -------------------------------------------------------------------
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Foto de perfil del usuario
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8A38B))
                    .clickable { onUserClick(post.userId) },
                contentAlignment = Alignment.Center
            ) {
                // Si la imagen viene en Base64
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
                        android.graphics.BitmapFactory.decodeByteArray(
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

            // Nombre y tiempo
            Column {
                Text(
                    text =
                        if (post.userName.isNotEmpty())
                            post.userName
                        else
                            "Usuario",
                    fontWeight = FontWeight.Bold,
                    modifier =
                        Modifier.clickable {
                            onUserClick(post.userId)
                        }
                )
                Text(timeAgo, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Icono eliminar (solo si es el autor)
            if (post.userId == userId) {
                var showDeleteDialog by remember { mutableStateOf(false) }

                Text(
                    text = "🗑️",
                    fontSize = 20.sp,
                    modifier =
                        Modifier.clickable {
                            showDeleteDialog = true
                        }
                )

                // Diálogo de confirmación
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
        // Contenido del post: título, descripción, ubicación
        // -------------------------------------------------------------------
        Text(post.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(post.description, fontSize = 14.sp)

        // Enlace para ver ubicación en mapas
        if (post.lat != 0.0 && post.lng != 0.0) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val gmmIntentUri =
                        Uri.parse("geo:${post.lat},${post.lng}?q=${post.lat},${post.lng}(Ubicación)")
                    val mapIntent =
                        Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        val mapIntentBrowser =
                            Intent(Intent.ACTION_VIEW, gmmIntentUri)
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

        // -------------------------------------------------------------------
        // Imagen del post (Base64, URL o placeholder)
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
                    android.graphics.BitmapFactory.decodeByteArray(
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

        Spacer(modifier = Modifier.height(12.dp))

        // -------------------------------------------------------------------
        // Likes, comentarios y compartir
        // -------------------------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text =
                        if (isLiked)
                            "❤️ ${post.likesCount}"
                        else
                            "🤍 ${post.likesCount}",
                    fontSize = 14.sp,
                    modifier =
                        Modifier.clickable {
                            onLikeClick()
                        }
                )

                Spacer(modifier = Modifier.width(16.dp))
                Text("💬 ${post.commentsCount}", fontSize = 14.sp)
            }

            Text(
                text = "🔗 Compartir",
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.clickable {
                    val shareText = buildString {
                        append("${post.title}\n\n")
                        append("${post.description}\n\n")
                        if (post.lat != 0.0 && post.lng != 0.0) {
                            append(
                                "📍 Ubicación: https://maps.google.com/?q=${post.lat},${post.lng}"
                            )
                        }
                    }
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            shareText
                        )
                        type = "text/plain"
                    }
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Compartir publicación"
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // -------------------------------------------------------------------
        // Comentarios existentes
        // -------------------------------------------------------------------
        val comments =
            remember {
                mutableStateOf<List<mx.edu.utng.avht.unidad2.data.CommentModel>>(emptyList())
            }

        LaunchedEffect(post.id) {
            viewModel.fetchCommentsForPost(post.id) { fetchedComments ->
                comments.value = fetchedComments
            }
        }

        if (comments.value.isNotEmpty()) {
            Column {
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

        // -------------------------------------------------------------------
        // Input para escribir un comentario
        // -------------------------------------------------------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(20.dp)
                )
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
                    if (commentText.isEmpty())
                        Text(
                            "Escribe un comentario...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
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
