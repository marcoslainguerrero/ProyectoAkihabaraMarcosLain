package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String PROPERTIES_FILE = "config.properties"; // Nombre del archivo de configuración
    private static Properties properties;

    // Bloque estático que carga las propiedades una sola vez al cargar la clase
    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar el archivo " + PROPERTIES_FILE); // Error si no encuentra el archivo
            }
            properties.load(input); // Carga las propiedades desde el archivo
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de configuración", e); // Excepción si hay fallo al leer
        }
    }

    // Devuelve la clave API configurada en el archivo properties
    public static String getApiKey() {
        return properties.getProperty("api.key");
    }

    // Devuelve el modelo configurado en el archivo properties
    public static String getModel() {
        return properties.getProperty("api.modelo");
    }
}
