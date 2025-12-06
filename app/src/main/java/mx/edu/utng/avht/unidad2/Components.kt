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
