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
