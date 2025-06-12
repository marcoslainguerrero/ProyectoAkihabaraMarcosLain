package view;

import java.util.List;
import java.util.Scanner;

import model.ProductoOtaku;

public class InterfazConsola {
    private Scanner scanner;

    // Constructor que inicializa el Scanner para entrada por consola
    public InterfazConsola() {
        this.scanner = new Scanner(System.in);
    }

    // Muestra el menú principal al usuario
    public void mostrarMenuPrincipal() {
        System.out.println("\n=== BIENVENIDO A AKIHABARA MARKET ===");
        System.out.println("1. Añadir Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Buscar Producto por ID");
        System.out.println("4. Actualizar Producto");
        System.out.println("5. Eliminar Producto");
        System.out.println("6. Utilizar asistente IA");
        System.out.println("7. Salir del programa");
        System.out.print("Seleccione una opción entre");
    }

    // Solicita al usuario los datos para crear un nuevo producto
    public ProductoOtaku solicitarDatosProducto() {
        System.out.println("\n--- Añadir Nuevo Producto ---");
        String nombre = utilidades.Utilidades1.pedirString("Nombre del producto: ");
        String categoria = utilidades.Utilidades1.pedirString("Categoria del producto: ");
        Double precio = utilidades.Utilidades1.pedirDouble("Precio");
        Integer stock = utilidades.Utilidades1.pedirEntero("Stock");

        return new ProductoOtaku(0, nombre, categoria, precio, stock);
    }

    // Muestra una lista de productos en consola
    public void mostrarProductos(List<ProductoOtaku> productos) {
        System.out.println("\n--- Lista de Productos ---");
        for (ProductoOtaku producto : productos) {
            System.out.println(producto);
        }
    }

    // Solicita al usuario el ID de un producto
    public int solicitarIdProducto() {
        System.out.print("\nIngrese el ID del producto: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Muestra un mensaje simple por consola
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Obtiene la opción seleccionada por el usuario en el menú principal
    public int obtenerOpcionUsuario() {
        int opcion = utilidades.Utilidades1.pedirEnteroObligatorio("", 1, 7);
        return opcion;
    }

    // Obtiene la opción seleccionada por el usuario en el menú del asistente IA
    public int obtenerOpcionUsuarioIA() {
        int opcion = utilidades.Utilidades1.pedirEnteroObligatorio("", 1, 3);
        return opcion;
    }

    // Muestra el menú específico para el asistente IA
    public void mostrarMenuIA() {
        System.out.println("\n--- Asistente IA ---");
        System.out.println("1. Generar descripción de producto");
        System.out.println("2. Sugerir categoría para producto");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    // Solicita el nombre del producto al usuario (para funcionalidades IA)
    public String solicitarNombreProducto() {
        System.out.print("\nIngrese el nombre del producto: ");
        return scanner.nextLine();
    }

    // Muestra la respuesta generada por el asistente IA
    public void mostrarRespuestaIA(String respuesta) {
        System.out.println("\n--- Respuesta IA ---");
        System.out.println(respuesta);
    }

    // Solicita los datos necesarios para crear un nuevo producto, asegurando que los campos obligatorios sean completados
    public ProductoOtaku solicitarDatosProductoNecesarios() {
        System.out.println("\n--- Añadir Nuevo Producto ---");
        String nombre = utilidades.Utilidades1.pedirStringObligatorio("Nombre del producto: ");
        String categoria = utilidades.Utilidades1.pedirStringObligatorio("Categoria del producto: ");
        Double precio = utilidades.Utilidades1.pedirDouble("Precio");
        Integer stock = utilidades.Utilidades1.pedirEntero("Stock");

        return new ProductoOtaku(0, nombre, categoria, precio, stock);
    }
}
