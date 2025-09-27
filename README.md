# ChistesProgra - App de Chistes del Día

**Autores:**  
- **Lester David Payez Mendez** – 0905-24-22750  
- **Mario David Tereta Sapalun** – 0905-15-14297  

**Roles:**  
- Ambos autores investigaron la API a utilizar (JokeAPI) y colaboraron en pensar y mejorar los prompts para generar el código.  
- Cada uno elaboró la mitad de la documentación de cambios y pruebas de la app.

---

## Documentación de cambios

### 1. Cambio de idioma de los chistes a español
Se modificó la URL de la API en el helper de red para incluir el parámetro `lang=es`, permitiendo que los chistes se obtengan en español si están disponibles.

### 2. Nombre de la app cambiado a "ChistesProgra"
Se actualizó el valor de `app_name` en el archivo `strings.xml` para que el nombre "ChistesProgra" aparezca en la barra superior, el ícono y el menú de aplicaciones de Android.

### 3. Preferencias internas renombradas
Las preferencias donde se almacenan datos internos (como el último chiste) ahora usan el nombre `chistesprogra_prefs` para mantener coherencia con el nombre de la app.

### 4. Autores en la pantalla "Acerca de"
Se agregó la información de autores en la pantalla "Acerca de" de la app, mostrando:  
**Autor: Lester Payes y Mario Tereta**

### 5. Permiso de Internet agregado
Se añadió el permiso de `INTERNET` en el archivo `AndroidManifest.xml` para permitir que la app realice peticiones de red y obtenga chistes desde la API.

### 6. Verificación de ids y robustez del código
Se revisó que todos los ids de vistas existan en el layout y que el código de consulta a la API sea robusto y seguro, evitando cierres inesperados.

---

## Prompts utilizados

### Primer Prompt en Gemini
**Título:** Prompt inicial en Gemini (no pudo desarrollar la app)  
```markdown
Ayúdame a generar código Java para Android para la app AppChistesdelDia que consuma JokeAPI (https://v2.jokeapi.dev/joke/Any) de forma segura y confiable:
1. Realizar la petición HTTP fuera del hilo principal (AsyncTask, Thread o Executor).
2. Incluir User-Agent correcto para evitar 403.
3. Manejar errores HTTP 403, 429 y otros, mostrando mensajes amigables en la UI.
4. Parsear JSON de JokeAPI correctamente:
   - type=single → mostrar joke
   - type=twopart → mostrar setup y delivery
5. Actualizar TextView en el hilo UI.
6. Incluir try-catch para manejar errores de conexión y parseo.
7. Usar parámetros de la API:
   - lang=es para español
   - format=json para JSON
   - safe-mode para chistes seguros
8. Comentar cada bloque explicando su función.
9. Garantizar que la app nunca se cierre inesperadamente.
10. Mostrar un mensaje de error amigable si la API falla.
Segundo Prompt en Gemini
Título: Segundo prompt en Gemini (no pudo desarrollarlo)

markdown
Copiar código
Genera código Java para Android para la app AppChistesdelDia que consuma JokeAPI (https://v2.jokeapi.dev/joke/Any) de manera robusta:
- La llamada HTTP debe ejecutarse fuera del hilo principal (usar AsyncTask o Thread).
- Manejar errores HTTP: 403 (User-Agent), 429 (Too many requests) y otros.
- Parsear JSON considerando:
  - Si type = single → mostrar joke
  - Si type = twopart → mostrar setup seguido de delivery
- Actualizar el TextView en el hilo UI.
- Incluir try-catch para cualquier excepción.
- Comentar cada paso con explicaciones claras.
- Parámetros opcionales de la API:
  - ?lang=es para español
  - ?format=json para JSON
  - ?safe-mode para chistes seguros
- Garantizar que la app no se cierre inesperadamente y muestre un mensaje si la API falla.
Prompt solicitado a GitHub Copilot
Título: Prompt Maestro: App de Chistes del Día en Java

markdown
Copiar código
Actúa como un experto en desarrollo Android con Java y en consumo de APIs REST.
Estoy desarrollando una app llamada "Chiste del Día" como parte de un reto académico.
Quiero que me ayudes a generar todo el proyecto en Android Studio paso a paso, incluyendo Activities, consumo de API y diseño básico.

🎯 Objetivo de la app
Mostrar un chiste nuevo cada día al usuario.
Consumir la API pública JokeAPI: https://sv443.net/jokeapi/v2/
Permitir al usuario presionar un botón para obtener un nuevo chiste.
Tener un diseño simple, divertido y funcional.

📌 Requerimientos técnicos
- Lenguaje: Java.
- Estructura del proyecto: Android Studio con Activities.
- Pantallas mínimas:
  - MainActivity: título de la app, botón para “Obtener chiste” y área de texto para mostrar el chiste.
  - AboutActivity: pantalla secundaria con información de la app (autores y reto académico).

Consumo de la API:
- Hacer request GET a JokeAPI.
- Parsear JSON (puede devolver setup + delivery o joke según el tipo de chiste).
- Mostrar el chiste completo en pantalla.

Manejo de errores:
- Si la API falla, mostrar un mensaje como “Error al obtener el chiste. Intenta de nuevo.”

Extras opcionales:
- Compartir el chiste en redes sociales (Share Intent).
- Guardar los últimos 3 chistes en memoria local (SharedPreferences).
- Cambiar estilo de UI con colores divertidos y emojis.

📌 Lo que necesito que generes
- Estructura completa del proyecto Android en Java.
- Código para MainActivity y AboutActivity en Java.
- Lógica para consumir JokeAPI usando librerías como Volley o Retrofit (elige la más sencilla para principiantes).
- Diseño XML básico de ambas pantallas (botón, texto, título).
- Comentarios en el código explicando cada parte (para aprendizaje).
- Instrucciones de qué dependencias debo agregar en build.gradle.
- Código listo para copiar y pegar en Android Studio.
- Sugerencias de mejoras o extras divertidos.

📌 Documentación que debo entregar
- Lista de prompts que usé (incluido este).
- Resultados de cada prompt: código generado, errores y correcciones.
- Capturas de pantalla de la app en funcionamiento.
- Reflexión final sobre el uso de IA (Copilot y Gemini) en el desarrollo.

📌 Importante
- Todo el proyecto debe estar escrito en Java, no en Kotlin.
- No omitas los imports.
- Explica qué dependencias debo agregar en build.gradle.
Correcciones al Prompt original solicitado a GitHub Copilot
markdown
Copiar código
Corrige el crash de la app AppChistesdelDia que ocurre al conectarse a JokeAPI. 
Genera código Java para Android que:
1. Realice la petición HTTP en un hilo separado (AsyncTask o Thread).
2. Maneje correctamente errores HTTP 403, 429 y otros.
3. Parsee JSON de chistes tipo single o twopart sin romper la app.
4. Actualice el TextView con el chiste en el hilo UI.
5. Agregue comentarios explicativos de cada paso.
6. Asegure que la app no se cierre inesperadamente.
