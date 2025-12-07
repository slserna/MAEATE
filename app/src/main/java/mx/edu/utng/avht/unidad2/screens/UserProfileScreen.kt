package mx.edu.utng.avht.unidad2.screens

// Componentes básicos de imagen y diseño
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

// Componentes para mostrar grids (rejillas) de forma eficiente
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

// Iconos Material
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

// Componentes Material Design 3
import androidx.compose.material3.*

import androidx.compose.runtime.*

// Utilidades visuales
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Firebase Authentication (usuario actual)
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
