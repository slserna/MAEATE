// Define el paquete donde se agrupan los elementos del tema visual de la app.
// Normalmente "ui.theme" contiene colores, tipografías y formas.
package mx.edu.utng.avht.unidad2.ui.theme

// Importa la clase Color para definir colores personalizados en Jetpack Compose
import androidx.compose.ui.graphics.Color

/**
 * Archivo de definición de colores del tema.
 *
 * Aquí se declaran constantes de tipo Color que representan
 * la paleta base de la aplicación.
 *
 * Estos colores normalmente son utilizados en:
 * - MaterialTheme
 * - Botones
 * - Textos
 * - Fondos
 * - Barras de navegación
 *
 * La separación en dos grupos (80 y 40) se utiliza comúnmente
 * para diferenciar entre:
 * - Tema claro
 * - Tema oscuro
 */

/**
 * Colores principales para tema claro
 */

// Color púrpura principal en valores claros
val Purple80 = Color(0xFFD0BCFF)

// Variación gris del púrpura para superficies o fondos
val PurpleGrey80 = Color(0xFFCCC2DC)

// Color rosa claro usado para acentos
val Pink80 = Color(0xFFEFB8C8)

/**
 * Colores principales para tema oscuro
 */

// Púrpura más oscuro, usado en modo oscuro
val Purple40 = Color(0xFF6650a4)

// Gris púrpura más intenso para superficies
val PurpleGrey40 = Color(0xFF625b71)

// Rosa oscuro para elementos de énfasis
val Pink40 = Color(0xFF7D5260)
