# 📱 MAPÉATE 🗺 — Aplicación Android

Proyecto académico desarrollado en Android Studio con Kotlin y Jetpack Compose, que integra autenticación, navegación entre pantallas y funcionalidades visuales como mapas y perfiles de usuario.

## ✨ Descripción
Esta aplicación permite a los usuarios acceder con autenticación, navegar por diferentes secciones como Mapa Principal, Perfil, Comunidad y explorar contenido relacionado con rutas e información relevante.
El proyecto fue desarrollado como parte de la Materia Aplicaciones Móviles, integrando buenas prácticas de arquitectura, navegación Compose y Firebase. El objetivo principal de esta aplicacion, es que 
que los usuarios puedan conocer mas lugares y conocer mas lacultura en diversos lugares.

## 👩‍💻 Autoras
Sara Lizbeth Serna Rodríguez y Hernandez Torres Alondra vienney

Grupo: GTID141

Proyecto académico — Aplicaciones Móviles Unidaad 4

Universidad Tecnologica del norte de Guanajuato (UTNG)

## 📄 Licencia
Este proyecto se utiliza únicamente con fines educativos.

## 🚀 Funcionalidades principales

<table>
  <tr>
    <th align="left">✅ Funcionalidades</th>
    <th align="left">🛠️ Tecnologías utilizadas</th>
  </tr>

  <tr>
    <td valign="top">
      🔐 Inicio de sesión y autenticación con Firebase<br>
      🗺️ Pantalla de Mapa Principal<br>
      👤 Pantalla de Perfil de Usuario<br>
      👥 Pantalla de Comunidad<br>
      🔎 Explorar rutas desde la pantalla principal<br>
      📸 Integración futura para subida de imágenes desde cámara/galería<br>
      🎨 Temas de color personalizados y diseño moderno en Compose<br>
      🧭 Navegación intuitiva entre pantallas con NavHost y routes
    </td>

    <td valign="top">
      <ul>
        <li>🟣 Kotlin</li>
        <li>🎨 Jetpack Compose</li>
        <li>📐 Material 3</li>
        <li>🔥 Firebase Auth</li>
        <li>🗄️ Firebase Firestore</li>
        <li>🧭 AndroidX Navigation Compose</li>
        <li>🧠 ViewModel + StateFlow</li>
        <li>⚙️ Gradle KTS</li>
      </ul>
    </td>
  </tr>
</table>



---
## 📸 Capturas de la aplicación mapéate
<table>
  <tr>
    <th>🔐 Inicio de sesión</th>
    <th>🗺️ Pantalla principal</th>
    <th>🗺️ Mapa</th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Inicio.png" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Principal.jpeg" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Mapa.jpeg" width="260"/>
    </td>
  </tr>

  <tr>
    <th>📤 Subir contenido</th>
    <th>👤 Perfil de usuario</th>
    <th>👥 Comunidad</th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_SubirContenido.jpeg" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Perfil.jpeg" width="260"/>
    </td>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Comunidad.jpeg" width="260"/>
    </td>
  </tr>

  <tr>
    <th>📝 Registro</th>
    <th></th>
    <th></th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/slserna/MAEATE/blob/master/documentos/Imagenes/Pantalla_Registro.png" width="260"/>
    </td>
    <td></td>
    <td></td>
  </tr>
</table>




## ✨ Documentación del Código con KDoc

Toda la base del código fue documentada utilizando KDoc, siguiendo buenas prácticas de documentación en Kotlin.
Se añadieron comentarios en formato /** ... */ para:

- Explicar el propósito de cada clase, ViewModel, pantalla y componente.
- Describir las funcionalidades de cada función importante, incluyendo parámetros y valores de retorno.
- Aclarar la lógica de elementos relacionados con navegación, mapas, repositorios, bases de datos, eventos de UI y ViewModels.
- Facilitar la lectura, mantenimiento y comprensión general del proyecto para otros desarrolladores.
- Con esta documentación, el código ahora es más entendible y cumple con los requisitos solicitados en la actividad.

## ⚜[ACTIVIDADES EXTRAS](https://github.com/slserna/MAEATE/tree/master/documentos) ⚜

|Actividad| Evidencias | LINKS |
| ------------- |  --------|  ------------- |
| Actividad 2: Demostración Funcional | Evidencia  | [Ver ejercicio](https://github.com/slserna/MAEATE/blob/master/documentos/Actividad-2/SernaRodriguezSaraLizbeth-Demo-U4.pdf)|
| Actividad 3: Pruebas con Usuarios | Evidencia  | [Ver ejercicio](https://github.com/slserna/MAEATE/blob/master/documentos/Actividad-3/HernandezTorresAlondraVianney-Pruebas--U4%20.pdf)|
| Carpeta docs/imágenes | Evidencias |  [Ver ejercicio](https://github.com/slserna/MAEATE/tree/master/documentos/Imagenes)|
| Código fuente con comentarios KDoc/JSDoc | Evidencia |  [Ver ejercicio](https://github.com/slserna/MAEATE/blob/master/app/src/main/java/mx/edu/utng/avht/unidad2/MainActivity.kt)|

## 📂 Estructura del proyecto

```text
UNIDAD2/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/        (código fuente)
│   │       └── res/         (layouts, drawables, strings)
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md

