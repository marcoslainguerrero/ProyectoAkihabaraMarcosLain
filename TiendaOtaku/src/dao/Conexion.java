package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/AkihabaraDB"; // URL de conexión a la base de datos
    private static final String USER = "AkihabaraUser"; // Usuario de la base de datos
    private static final String PASS = "TiendaOtaku23"; // Contraseña de la base de datos
    
    public static Connection conexion = null; // Objeto estático para mantener la conexión
    
    // Método para obtener la conexión a la base de datos
    public static Connection getConexion() {
        if (conexion == null) { // Si no existe conexión previa, crea una nueva
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Carga el driver JDBC para MySQL
                conexion = DriverManager.getConnection(URL, USER, PASS); // Establece la conexión
                System.out.println("Conexión a la base de datos correcta");
            } catch (ClassNotFoundException e) {
                System.out.println("Error al cargar el driver: " + e.getMessage()); // Error si no se encuentra el driver
            } catch (SQLException e) {
                System.out.println("Error en la conexión: " + e.getMessage()); // Error al conectar a la BD
            }
        }
        return conexion; // Retorna la conexión establecida o existente
    }
    
    // Método para cerrar la conexión a la base de datos
    public static void cerrarConexion() {
        if (conexion != null) { // Solo intenta cerrar si hay una conexión abierta
            try {
                conexion.close(); // Cierra la conexión
                conexion = null; // Limpia la referencia para que se pueda volver a crear en el futuro
                System.out.println("Conexión con la base de datos cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage()); // Error al cerrar conexión
            }
        }
    }
}
