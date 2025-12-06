package mx.edu.utng.avht.unidad2

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

// Define colors here to be accessible
val salmon = Color(0xFFE4A691)
val crema = Color(0xFFF7EFD8)
val azulGris = Color(0xFF556270)
val azulOscuro = Color(0xFF273142)

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono de búsqueda
        Text("🔍", modifier = Modifier.padding(start = 16.dp), fontSize = 18.sp, color = azulGris)
        Text(
            "Buscar lugar o meme...",
            modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        /* Text("🔔", modifier = Modifier.padding(end = 16.dp))*/
    }
}

@Composable
fun BottomNav(
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToComunidad: () -> Unit = {}
) {
    NavigationBar(containerColor = Color.White) {

        // MAPA
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Text("📍") },
            label = { Text("Mapa") }
        )

        // COMUNIDAD
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToComunidad() },
            icon = { Text("👥") },
            label = { Text("Comunidad") }
        )

        // PERFIL
        NavigationBarItem(
            selected = false,
            onClick = { onNavigateToPerfil() },
            icon = { Text("🏠") },
            label = { Text("Perfil") }
        )
    }
}
