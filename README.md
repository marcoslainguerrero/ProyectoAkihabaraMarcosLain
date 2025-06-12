# TiendaOtaku - README
Enlace a Github:  https://github.com/marcoslainguerrero/TiendaakihabaraMarcosLain.git
## 1. Configuración de la base de datos MySQL

1. Asegúrate de tener instalado MySQL Server.  
2. Ejecuta el script `crear_tabla.sql` antes de ejecutar el archivo ejecutable, esto es IMPRESCINDIBLE para crear la base de datos y las tablas necesarias antes de ejecutar el programa:

    ```sql
    DROP DATABASE IF EXISTS AkihabaraDB;
    CREATE DATABASE AkihabaraDB;
    USE AkihabaraDB;
    CREATE TABLE IF NOT EXISTS productos (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(255) NOT NULL,
        categoria VARCHAR(100),
        precio DECIMAL(10,2),
        stock INT
    );
    CREATE USER IF NOT EXISTS 'AkihabaraUser'@'localhost' IDENTIFIED BY 'TiendaOtaku23';
    GRANT SELECT, INSERT, UPDATE, DELETE ON AkihabaraDB.* TO 'AkihabaraUser'@'localhost';
    FLUSH PRIVILEGES;
    ```

3. Verifica que el usuario `'AkihabaraUser'` tenga acceso y permisos adecuados.  
4. En el archivo `src/config/ConfigLoader.java` o en `resources/config.properties` puedes configurar los datos de conexión (host, usuario, contraseña).

---

## 2. Configuración de la API Key de OpenRouter

1. La integración con OpenRouter LLM se realiza en `service/LlmService.java`.  
2. Para configurar la API Key:  
    - Edita el archivo `src/resources/config.properties`.  
    - Añade la línea siguiente, reemplazando `TU_API_KEY_AQUI` con tu clave real:

    ```properties
    openrouter.api.key=TU_API_KEY_AQUI
    ```

3. La clase `ConfigLoader` lee este archivo y pasa la clave a `LlmService`.

---

## 3. Cómo compilar y ejecutar la aplicación

**Requisitos:**  
- Java 17 o superior.  
- MySQL Connector/J (ya incluido en `/lib` y Referenced Libraries).

**Desde eclipse IDE, dentro de la carpeta `src/com.akihabara.market`:**
Elige que versión vas a utilizar:      
 
  	-Si vas a utilizar la version en consola, click derecho en Main.java Run as Java Aplication.    
  	 
  	 
    
    
    
  	-Si vas a utilizar la version con interfaz gráfica, click derecho en InterfazGrafica.java Run as Java Aplication.   

## 4. Cómo abrir el ejecutable directamente (no requiere tener Eclipse IDE instalado)
-Si vas a utilizar la version en consola: Abre el archivo AkihabaraEjecutableConsola.bat.       

 
-Si vas a utilizar la version con interfaz gráfica: Abre el archivo AkihabaraEjecutableInterfaz.bat.        




Ya tendrías el programa corriendo en local.

