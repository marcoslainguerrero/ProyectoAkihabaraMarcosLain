package com.akihabara.market;

import dao.Conexion;
import dao.ProductoDAO;
import model.ProductoOtaku;
import service.LlmService;

import java.util.List;
import util.Setupdatos;
import view.InterfazConsola;

public class Main {
	private LlmService llmService;
    private ProductoDAO productoDAO;
    private InterfazConsola interfaz;

    public Main() {
        this.productoDAO = new ProductoDAO(); // Inicializa el acceso a datos de productos
        this.interfaz = new InterfazConsola(); // Inicializa la interfaz de usuario por consola
        this.llmService = new LlmService(); // Servicio para llamadas a modelo de lenguaje (IA)
        
        Setupdatos setup = new Setupdatos(); // Clase para configuración inicial
        setup.inicializarDatosPrueba(productoDAO); // Carga algunos productos de prueba
    }

    public void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            interfaz.mostrarMenuPrincipal(); // Muestra menú principal al usuario
            int opcion = interfaz.obtenerOpcionUsuario(); // Lee la opción del usuario
            
            switch (opcion) {
                case 1:
                    agregarProducto(); // Opción para agregar nuevo producto
                    break;
                case 2:
                    listarProductos(); // Opción para listar todos los productos
                    break;
                case 3:
                    buscarProductoPorId(); // Buscar producto por su ID
                    break;
                case 4:
                    actualizarProducto(); // Actualizar datos de un producto
                    break;
                case 5:
                    eliminarProducto(); // Eliminar producto por ID
                    break;
                case 6:
                    menuIA(); // Acceso a funcionalidades con IA
                    break;
                case 7:
                    salir = true;
                    Conexion.cerrarConexion(); // Cierra conexión con la base de datos
                    interfaz.mostrarMensaje("Saliendo del programa...");
                    break;
            }
        }
    }

    private void agregarProducto() {
        ProductoOtaku producto = interfaz.solicitarDatosProductoNecesarios(); // Pide datos al usuario
        boolean resultado = productoDAO.insertarProducto(producto); // Inserta en la base de datos
        if (resultado) {
            interfaz.mostrarMensaje("Producto añadido exitosamente!");
        } else {
            interfaz.mostrarMensaje("Error al añadir el producto.");
        }
    }

    private void listarProductos() {
        List<ProductoOtaku> productos = productoDAO.obtenerTodosProductos(); // Obtiene todos los productos
        interfaz.mostrarProductos(productos); // Muestra en consola
    }

    private void buscarProductoPorId() {
    	int id = utilidades.Utilidades1.pedirEntero("Introduzca el ID del pedido a buscar");
        ProductoOtaku producto = productoDAO.obtenerProductoPorId(id); // Busca producto en la BD
        if (producto != null) {
            interfaz.mostrarProductos(List.of(producto));
        } else {
            interfaz.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void actualizarProducto() {
    	int id = utilidades.Utilidades1.pedirEntero("Introduzca el ID del producto a actualizar");
        ProductoOtaku productoExistente = productoDAO.obtenerProductoPorId(id); // Busca producto existente
        
        if (productoExistente != null) {
            interfaz.mostrarMensaje("Ingrese los nuevos datos (deje en blanco para mantener el valor actual, precio y stock deben ser introducidos):");
            ProductoOtaku nuevosDatos = interfaz.solicitarDatosProducto(); // Pide nuevos datos
            
            // Actualiza solo los campos proporcionados
            if (!nuevosDatos.getNombre().isEmpty()) productoExistente.setNombre(nuevosDatos.getNombre());
            if (!nuevosDatos.getCategoria().isEmpty()) productoExistente.setCategoria(nuevosDatos.getCategoria());
            if (!(nuevosDatos.getPrecio().isNaN())) productoExistente.setPrecio(nuevosDatos.getPrecio());
            if (!(nuevosDatos.getStock()==null)) productoExistente.setStock(nuevosDatos.getStock());
            
            boolean resultado = productoDAO.actualizarProducto(productoExistente); // Actualiza en BD
            if (resultado) {
                interfaz.mostrarMensaje("Producto actualizado exitosamente!");
            } else {
                interfaz.mostrarMensaje("Error al actualizar el producto.");
            }
        } else {
            interfaz.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void eliminarProducto() {
    	int id = utilidades.Utilidades1.pedirEnteroObligatorioSinRango("Introduzca el ID del pedido a eliminar");
        boolean resultado = productoDAO.eliminarProducto(id); // Elimina por ID
        if (resultado) {
            interfaz.mostrarMensaje("Producto eliminado exitosamente!");
        } else {
            interfaz.mostrarMensaje("Error al eliminar el producto o producto no encontrado.");
        }
    }

    public static int asistenteIA() {
    	return 0; // Método de placeholder, no implementado
    }
    
    private void menuIA() {
        boolean volver = false;
        
        while (!volver) {
            interfaz.mostrarMenuIA(); // Muestra el menú de opciones con IA
            int opcion = interfaz.obtenerOpcionUsuarioIA(); // Lee opción del usuario
            
            switch (opcion) {
                case 1:
                    generarDescripcionProducto(); // IA genera descripción de producto
                    break;
                case 2:
                    sugerirCategoria(); // IA sugiere categoría
                    break;
                case 3:
                    volver = true; // Salir del menú IA
                    break;
                default:
                    interfaz.mostrarMensaje("Opción no válida. Intente nuevamente.");
            }
        }
    }
    
    private void generarDescripcionProducto() {
        try {
            int id = interfaz.solicitarIdProducto(); // Solicita ID al usuario
            ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
            
            if (producto != null) {
                // Prepara un mensaje para que la IA genere una descripción de marketing
                String prompt = "Responde en idioma español. Ponte en el contexto de que tenemos una tienda de productos Otakus/Frikis y queremos generar una breve descripcion de un producto para publicarla en nuestra web, quiero que generes una descripción de marketing para el siguiente producto: " +
                              producto.getNombre() + " (" + producto.getCategoria() + ")";
                
                String respuesta = llmService.consultarLLM(prompt); // Consulta a la IA
                interfaz.mostrarRespuestaIA(respuesta); // Muestra la respuesta generada
            } else {
                interfaz.mostrarMensaje("Producto no encontrado.");
            }
        } catch (Exception e) {
            interfaz.mostrarMensaje("Error al generar descripción: " + e.getMessage());
        }
    }

    private void sugerirCategoria() {
    	try {
            String nombreProducto = interfaz.solicitarNombreProducto(); // Pide el nombre del producto
            // Prompt para que la IA sugiera una categoría adecuada
            String prompt = "No respondas en inglés. Ponte en el contexto de una tienda de productos Otakus/Frikis. " +
                "Dado el siguiente nombre de producto: \"" + nombreProducto + "\", responde solo con una categoría adecuada en idioma español. " +
                "Usa exactamente este formato:\n\nCategoria: [una sola palabra]\n\nNo agregues ninguna explicación ni texto adicional.";

            String respuesta = llmService.consultarLLM(prompt); // Consulta a la IA
            interfaz.mostrarRespuestaIA(respuesta); // Muestra categoría sugerida
    	} catch (Exception e) {
    		interfaz.mostrarMensaje("Error"); // Manejo genérico de error
    	}
    }
    	
    public static void main(String[] args) {
        Main app = new Main(); // Crea instancia principal del programa
        app.iniciar(); // Inicia el bucle del menú
    }
}
