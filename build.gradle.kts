// -------------------------------------------------------------------------
// ARCHIVO build.gradle.kts (NIVEL PROYECTO / TOP-LEVEL)
// -------------------------------------------------------------------------
// Este archivo se utiliza para definir configuraciones y plugins
// comunes para TODOS los módulos del proyecto.
//
// No contiene dependencias específicas de la app,
// sino la configuración base que otros módulos reutilizan.
// -------------------------------------------------------------------------

// Declaración de plugins que estarán disponibles para los submódulos
plugins {

    /**
     * Plugin de Android Application
     * --------------------------------
     * Se declara aquí con "apply false" para que:
     *  - Esté disponible en el proyecto
     *  - Pero se aplique únicamente en los módulos que lo necesiten
     *    (por ejemplo, el módulo :app)
     *
     * Se gestiona mediante el catálogo de versiones (libs.versions.toml)
     */
    alias(libs.plugins.android.application) apply false

    /**
     * Plugin de Kotlin para Android
     * ------------------------------
     * Permite usar Kotlin en proyectos Android.
     * Al igual que el anterior, solo se declara aquí
     * y se aplica directamente en los módulos correspondientes.
     */
    alias(libs.plugins.kotlin.android) apply false

    /**
     * Plugin de Google Services (Firebase)
     * -------------------------------------
     * Este plugin es necesario para que Firebase:
     *  - Lea el archivo google-services.json
     *  - Configure automáticamente servicios como:
     *      • Firebase Auth
     *      • Firestore
     *      • Analytics
     *      • Storage
     *
     * Se declara en el nivel superior con:
     *  - Versión explícita
     *  - apply false → se aplica solo en el módulo app
     */
    id("com.google.gms.google-services") version "4.4.4" apply false
}


