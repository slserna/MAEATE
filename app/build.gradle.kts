import java.util.Properties

// ------------------------------------------------------------------------
// PLUGINS DEL PROYECTO
// ------------------------------------------------------------------------
// Define los plugins necesarios para Android, Kotlin, Compose y Firebase.
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("com.google.gms.google-services") // Firebase / Google Services
}

// ------------------------------------------------------------------------
// LECTURA DE local.properties
// ------------------------------------------------------------------------
// Se utilizan Properties para leer valores sensibles y configurables
// (API Keys, Keystore) desde el archivo local.properties.
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

// Obtención de la API Key de Google Maps
val mapsApiKey = localProperties.getProperty("MAPS_API_KEY") ?: ""

// ------------------------------------------------------------------------
// CONFIGURACIÓN DEL KEYSTORE (FIRMA DE LA APP)
// ------------------------------------------------------------------------
// Datos de firma cargados desde local.properties
val keystorePath = localProperties.getProperty("KEYSTORE_PATH") ?: ""
val keystorePassword = localProperties.getProperty("KEYSTORE_PASSWORD") ?: ""
val keyAlias = localProperties.getProperty("KEY_ALIAS") ?: ""
val keyPassword = localProperties.getProperty("KEY_PASSWORD") ?: ""

android {

    // Namespace de la aplicación
    namespace = "mx.edu.utng.avht.unidad2"

    // SDK con el que se compila la app
    compileSdk = 34

    defaultConfig {

        // Identificador único de la aplicación
        applicationId = "mx.edu.utng.avht.unidad2"

        // Versiones mínima y objetivo de Android
        minSdk = 24
        targetSdk = 34

        // Versión de la app
        versionCode = 1
        versionName = "1.0"

        // Inyección de la API Key de Google Maps en el Manifest
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }

    // --------------------------------------------------------------------
    // CONFIGURACIONES DE FIRMA (SIGNING CONFIGS)
    // --------------------------------------------------------------------
    signingConfigs {
        if (keystorePath.isNotEmpty()) {
            create("release") {
                storeFile = file(keystorePath)
                storePassword = keystorePassword
                keyAlias = keyAlias
                keyPassword = keyPassword
            }
        }
    }

    // --------------------------------------------------------------------
    // TIPOS DE COMPILACIÓN
    // --------------------------------------------------------------------
    buildTypes {

        // Configuración para versión release
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Firma release si existe keystore
            if (keystorePath.isNotEmpty()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }

        // Configuración para versión debug
        debug {
            // Usa la firma release si está configurada
            if (keystorePath.isNotEmpty()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    // --------------------------------------------------------------------
    // CONFIGURACIÓN DE COMPOSE
    // --------------------------------------------------------------------
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.21"
    }

    // --------------------------------------------------------------------
    // OPCIONES DE COMPILACIÓN JAVA / KOTLIN
    // --------------------------------------------------------------------
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// ------------------------------------------------------------------------
// DEPENDENCIAS DEL PROYECTO
// ------------------------------------------------------------------------
dependencies {

    // --------------------------------------------------------------------
    // JETPACK COMPOSE
    // --------------------------------------------------------------------
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Iconos de Material
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // --------------------------------------------------------------------
    // FIREBASE
    // --------------------------------------------------------------------
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    // --------------------------------------------------------------------
    // CARGA DE IMÁGENES (COIL)
    // --------------------------------------------------------------------
    implementation("io.coil-kt:coil-compose:2.3.0")

    // --------------------------------------------------------------------
    // GOOGLE MAPS
    // --------------------------------------------------------------------
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // --------------------------------------------------------------------
    // COROUTINES PARA FIREBASE
    // --------------------------------------------------------------------
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // --------------------------------------------------------------------
    // TESTING
    // --------------------------------------------------------------------
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


