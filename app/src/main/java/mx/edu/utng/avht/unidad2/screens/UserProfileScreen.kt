package mx.edu.utng.avht.unidad2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    userId: String,
    onNavigateBack: () -> Unit
) {
    val viewModel: mx.edu.utng.avht.unidad2.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val isOwnProfile = userId == currentUserId
    
    var username by remember { mutableStateOf("Cargando...") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profilePicture by remember { mutableStateOf("") }
    var userPosts by remember { mutableStateOf<List<mx.edu.utng.avht.unidad2.data.ContentModel>>(emptyList()) }
    
    DisposableEffect(userId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                username = doc.getString("username") ?: doc.getString("email") ?: "Usuario"
                email = doc.getString("email") ?: ""
                bio = doc.getString("bio") ?: ""
                profilePicture = doc.getString("profilePicture") ?: ""
            }
        
        viewModel.fetchUserPosts(userId) { posts ->
            userPosts = posts
        }
        
        onDispose { }
    }
    
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFD2D0A6))
        ) {
            // Header info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8A38B)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (profilePicture.isNotEmpty() && profilePicture.startsWith("data:image")) {
                            val base64String = profilePicture.substringAfter("base64,")
                            val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
                            val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            
                            if (bitmap != null) {
                                Image(
                                    painter = androidx.compose.ui.graphics.painter.BitmapPainter(bitmap.asImageBitmap()),
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
                    
                    Text(username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    
                    if (email.isNotBlank()) {
                        Text(email, color = Color.Gray, fontSize = 13.sp)
                    }
                    
                    if (bio.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(bio, fontSize = 14.sp)
                    }
                }
            }
            
            // Posts
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3EBD2))
                    .padding(16.dp)
            ) {
                Text("Publicaciones", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                
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
                                    .background(Color(0xFFE8A38B)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (post.imageUrl.isNotEmpty()) {
                                    if (decodedBitmap != null) {
                                        Image(
                                            painter = androidx.compose.ui.graphics.painter.BitmapPainter(decodedBitmap.asImageBitmap()),
                                            contentDescription = post.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else if (!post.imageUrl.startsWith("data:image")) {
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
