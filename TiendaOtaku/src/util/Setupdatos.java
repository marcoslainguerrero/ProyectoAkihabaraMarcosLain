package util;

import dao.ProductoDAO;
import model.ProductoOtaku;

public class Setupdatos {
    
    // Método para inicializar datos de prueba en la base de datos si está vacía
    public void inicializarDatosPrueba(ProductoDAO productoDAO) {
        // Verifica si no hay productos en la base de datos
        if (productoDAO.obtenerTodosProductos().isEmpty()) {
            
            // Crear algunos productos de ejemplo para añadir
            ProductoOtaku producto1 = new ProductoOtaku(0, "Figura de acción de Goku", "Figuras", 34.99, 15);
            ProductoOtaku producto2 = new ProductoOtaku(0, "Camiseta One Piece", "Ropa", 19.90, 25);
            ProductoOtaku producto3 = new ProductoOtaku(0, "Manga Attack on Titan 1", "Manga", 8.50, 40);

            // Insertar los productos creados en la base de datos usando el DAO
            productoDAO.insertarProducto(producto1);
            productoDAO.insertarProducto(producto2);
            productoDAO.insertarProducto(producto3);

            // Mensaje para confirmar que los datos de prueba fueron añadidos
            System.out.println("Datos de prueba inicializados correctamente.");
        }
    }
}
