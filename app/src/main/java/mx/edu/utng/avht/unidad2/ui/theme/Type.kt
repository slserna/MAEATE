// Define el paquete donde se agrupan los elementos del tema visual
package mx.edu.utng.avht.unidad2.ui.theme

// Clase principal de Material Design 3 para tipografías
import androidx.compose.material3.Typography

// Definición de estilos de texto
import androidx.compose.ui.text.TextStyle

// Familias tipográficas disponibles
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

// Unidad de medida escalable para texto
import androidx.compose.ui.unit.sp

/**
 * Tipografía global de la aplicación.
 *
 * Este archivo define los estilos de texto utilizados en toda la app
 * siguiendo las guías de Material Design.
 *
 * Los estilos definidos aquí se aplican automáticamente cuando se usa:
 * - MaterialTheme.typography
 *
 * Esto permite mantener coherencia visual en:
 * - Textos
 * - Títulos
 * - Botones
 * - Descripciones
 */

// Conjunto de estilos tipográficos base de Material Design 3
val Typography = Typography(

    /**
     * Estilo de texto para contenido principal.
     *
     * bodyLarge se utiliza comúnmente para:
     * - Texto principal
     * - Descripciones extensas
     * - Contenido informativo
     */
    bodyLarge = TextStyle(

        // Familia tipográfica por defecto del sistema
        fontFamily = FontFamily.Default,

        // Grosor de letra normal
        fontWeight = FontWeight.Normal,

        // Tamaño de la fuente
        fontSize = 16.sp,

        // Altura de línea para mejorar la legibilidad
        lineHeight = 24.sp,

        // Espaciado entre letras
        letterSpacing = 0.5.sp
    )

)
