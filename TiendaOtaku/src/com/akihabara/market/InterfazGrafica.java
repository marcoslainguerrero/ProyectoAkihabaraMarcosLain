package com.akihabara.market;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import service.LlmService;

import dao.ProductoDAO;
import model.ProductoOtaku;
import service.LlmService;
import util.Setupdatos;

public class InterfazGrafica extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaProductos; // Tabla para mostrar productos
    private JScrollPane scrollPaneProductos; // Scroll para la tabla
    private ProductoDAO productoDAO; // DAO para acceso a datos de productos
    private LlmService llmService = new LlmService(); // Servicio para comunicación con el asistente IA

    public static void main(String[] args) {
        // Arranca la interfaz en el hilo de eventos de Swing
        EventQueue.invokeLater(() -> {
            try {
                InterfazGrafica frame = new InterfazGrafica();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public InterfazGrafica() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al cerrar ventana
        setBounds(100, 100, 1280, 800); // Tamaño y posición ventana

        productoDAO = new ProductoDAO(); // Instanciar DAO

        // Inicializar datos de prueba si no hay productos
        new Setupdatos().inicializarDatosPrueba(productoDAO);

        JMenuBar menuBar = new JMenuBar(); // Barra de menú

        menuBar.setLayout(new java.awt.GridLayout(1, 0)); // Layout en grilla horizontal
        setJMenuBar(menuBar);

        // Botón para añadir producto
        JButton btnAddProducto = new JButton("AÑADIR PRODUCTO");
        menuBar.add(btnAddProducto);
        btnAddProducto.addActionListener(e -> mostrarDialogoAñadirProducto());

        // Botón para mostrar u ocultar lista de productos
        JButton btnListarProductos = new JButton("LISTAR PRODUCTOS");
        menuBar.add(btnListarProductos);
        btnListarProductos.addActionListener(e -> {
            scrollPaneProductos.setVisible(!scrollPaneProductos.isVisible()); // Toggle visibilidad tabla

            if (scrollPaneProductos.isVisible()) {
                listarProductos(); // Cargar productos en tabla
                btnListarProductos.setText("OCULTAR PRODUCTOS");
            } else {
                btnListarProductos.setText("LISTAR PRODUCTOS");
            }
            revalidate();
            repaint();
        });

        // Botón para buscar un producto por ID
        JButton btnBuscarProducto = new JButton("BUSCAR PRODUCTO");
        menuBar.add(btnBuscarProducto);
        btnBuscarProducto.addActionListener(e -> buscarProducto());

        // Botón para actualizar producto existente
        JButton btnActualizarProducto = new JButton("ACTUALIZAR PRODUCTO");
        menuBar.add(btnActualizarProducto);
        btnActualizarProducto.addActionListener(e -> actualizarProducto());

        // Botón para eliminar producto por ID
        JButton btnEliminarProducto = new JButton("ELIMINAR PRODUCTO");
        menuBar.add(btnEliminarProducto);
        btnEliminarProducto.addActionListener(e -> eliminarProducto());

        // Botón para usar el asistente IA
        JButton btnAsistenteIA = new JButton("UTILIZAR ASISTENTE IA");
        menuBar.add(btnAsistenteIA);
        btnAsistenteIA.addActionListener(e -> mostrarDialogoAsistenteIA());

        // Botón para salir de la aplicación
        JButton btnSalir = new JButton("SALIR");
        menuBar.add(btnSalir);
        btnSalir.addActionListener(e -> System.exit(0));

        // Layout principal del contenido
        getContentPane().setLayout(new BorderLayout(0, 0));

        // Scroll pane que contiene la tabla de productos, inicialmente oculto
        scrollPaneProductos = new JScrollPane();
        scrollPaneProductos.setVisible(false);
        getContentPane().add(scrollPaneProductos, BorderLayout.CENTER);

        // Tabla para mostrar productos con columnas definidas
        tablaProductos = new JTable();
        tablaProductos.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "NOMBRE", "CATEGORIAS", "PRECIO", "STOCK"}
        ));
        scrollPaneProductos.setViewportView(tablaProductos);
    }

    // Método para listar todos los productos en la tabla
    private void listarProductos() {
        try {
            List<ProductoOtaku> productos = productoDAO.obtenerTodosProductos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            mostrarProductosEnTabla(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Llena la tabla con una lista de productos
    private void mostrarProductosEnTabla(List<ProductoOtaku> productos) {
        DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
        model.setRowCount(0); // Limpiar filas previas

        for (ProductoOtaku producto : productos) {
            Object[] fila = {
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStock()
            };
            model.addRow(fila);
        }
    }

    // Diálogo para añadir un producto nuevo
    private void mostrarDialogoAñadirProducto() {
        try {
            String nombre;
            do {    // Mostramos el panel si el usuario deja en blanco los campos.
                nombre = JOptionPane.showInputDialog(this, "Nombre del producto:");
                if (nombre == null) return;
                if (nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío. Intente de nuevo.");
                }
            } while (nombre.trim().isEmpty());

            String categoria;
            do {   // Mostramos el panel si el usuario deja en blanco los campos.
                categoria = JOptionPane.showInputDialog(this, "Categoría:");
                if (categoria == null) return;
                if (categoria.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La categoría no puede estar vacía. Intente de nuevo.");
                }
            } while (categoria.trim().isEmpty());

            String precioStr;
            double precio;
            do {
                precioStr = JOptionPane.showInputDialog(this, "Precio:");
                if (precioStr == null) return;
                if (precioStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El precio no puede estar vacío. Intente de nuevo.");
                    continue;
                }
                try {    //Comprobamos el formato de entrada del precio
                    precio = Double.parseDouble(precioStr.trim());
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Precio inválido. Intente de nuevo.");
                }
            } while (true);

            String stockStr;
            int stock;
            do {
                stockStr = JOptionPane.showInputDialog(this, "Stock:");
                if (stockStr == null) return;
                if (stockStr.trim().isEmpty()) {   // Comprobamos que se introduzca un numero de stock
                    JOptionPane.showMessageDialog(this, "El stock no puede estar vacío. Intente de nuevo.");
                    continue;
                }
                try {
                    stock = Integer.parseInt(stockStr.trim());
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Stock inválido. Intente de nuevo.");
                }
            } while (true);

            ProductoOtaku nuevoProducto = new ProductoOtaku();
            nuevoProducto.setNombre(nombre.trim());
            nuevoProducto.setCategoria(categoria.trim());
            nuevoProducto.setPrecio(precio);
            nuevoProducto.setStock(stock);

            boolean agregado = productoDAO.insertarProducto(nuevoProducto);
            if (agregado) {
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
                listarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Buscar producto por ID y mostrar detalles
    private void buscarProducto() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del producto a buscar:");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int id = Integer.parseInt(idStr.trim());

            ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
            if (producto == null) {  // Comprobamos que el Id existe
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "ID: " + producto.getId() + "\n" +
                    "Nombre: " + producto.getNombre() + "\n" +
                    "Categoría: " + producto.getCategoria() + "\n" +
                    "Precio: " + producto.getPrecio() + "\n" +
                    "Stock: " + producto.getStock(),
                    "Producto encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {   // Comprobamos que la entrada sea un número
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Actualizar producto existente solicitando nuevos datos
    private void actualizarProducto() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del producto a actualizar:");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int id = Integer.parseInt(idStr.trim());

            ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                return;
            }

            // Pedir nombre hasta que se introduzca uno válido o se cancele
            String nombre;
            do {
                nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", producto.getNombre());
                if (nombre == null) return;
                if (nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío. Intente de nuevo.");
                }
            } while (nombre.trim().isEmpty());

            String categoria;
            do {   // Comprobamos que la categoria del producto no este vacía
                categoria = JOptionPane.showInputDialog(this, "Nueva categoría:", producto.getCategoria());
                if (categoria == null) return;
                if (categoria.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La categoría no puede estar vacía. Intente de nuevo.");
                }
            } while (categoria.trim().isEmpty());

            String precioStr;
            double precio;
            do {      
                precioStr = JOptionPane.showInputDialog(this, "Nuevo precio:", String.valueOf(producto.getPrecio()));
                if (precioStr == null) return;
                if (precioStr.trim().isEmpty()) {    // Comprobamos que el precio del producto no este vacía
                    JOptionPane.showMessageDialog(this, "El precio no puede estar vacío. Intente de nuevo.");
                    continue;
                }
                try {
                    precio = Double.parseDouble(precioStr.trim());
                    break;
                } catch (NumberFormatException ex) {    // Comprobamos que la entrada del usuario no sea un String.
                    JOptionPane.showMessageDialog(this, "Precio inválido. Intente de nuevo.");
                }
            } while (true);

            String stockStr;
            int stock;
            do {
                stockStr = JOptionPane.showInputDialog(this, "Nuevo stock:", String.valueOf(producto.getStock()));
                if (stockStr == null) return;
                if (stockStr.trim().isEmpty()) {   // Comprobamos que el stock del producto no este vacío
                    JOptionPane.showMessageDialog(this, "El stock no puede estar vacío. Intente de nuevo.");
                    continue;
                }
                try {
                    stock = Integer.parseInt(stockStr.trim());
                    break;
                } catch (NumberFormatException ex) {    // Comprobamos que la entrada del usuario no sea un String.
                    JOptionPane.showMessageDialog(this, "Stock inválido. Intente de nuevo.");
                }
            } while (true);

            // Asignar nuevos valores
            producto.setNombre(nombre.trim());
            producto.setCategoria(categoria.trim());
            producto.setPrecio(precio);
            producto.setStock(stock);

            boolean actualizado = productoDAO.actualizarProducto(producto);
            if (actualizado) {   // Mensaje de confirmación de actualizacion
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                listarProductos();
            } else {   
                JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Eliminar producto por ID
    private void eliminarProducto() {    
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del producto a eliminar:");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int id = Integer.parseInt(idStr.trim());

            boolean eliminado = productoDAO.eliminarProducto(id);
            if (eliminado) {  // Mensaje de confirmacion de eliminacion
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                listarProductos();
            } else {     // Comprobamos si el ID está registrado en la BBDD.
                JOptionPane.showMessageDialog(this, "Producto no encontrado o error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {   // Comprobamos la entrada de usuario del ID 
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mostrar diálogo para usar el asistente IA con opciones
    private void mostrarDialogoAsistenteIA() {
        Object[] opciones = {"Generar Descripción de Producto", "Sugerir Categoría", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "¿Qué desea hacer con el asistente IA?",
                "Asistente IA",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        switch (seleccion) {
            case 0: //  Opcion para generar descripción de producto
                generarDescripcionProducto();
                break;
            case 1: // Opcion para sugerir categoría para producto
                sugerirCategoriaProducto();
                break;
            default: // Cancelar y cerrar diálogo
                break;
        }
    }

    // Funcion para generar la descripcion del producto
    private void generarDescripcionProducto() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del producto:");
            if (idStr == null || idStr.trim().isEmpty()) return; // Comprobamos que el ID introducido está registrado en la BBDD

            int id = Integer.parseInt(idStr.trim());
            ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);

            if (producto != null) {  // Si el producto existe, mandamos el prompt a la IA de openrouter.
                String prompt = "Responde en idioma español. Ponte en el contexto de que tenemos una tienda de productos Otakus/Frikis y queremos generar una breve descripción de un producto para publicarla en nuestra web. Quiero que generes una descripción de marketing para el siguiente producto: " +
                        producto.getNombre() + " (" + producto.getCategoria() + ")";

                String respuesta = llmService.consultarLLM(prompt);

                // Mostramos la respuesta de la IA con scroll y salto de línea
                JTextArea textArea = new JTextArea(respuesta, 10, 50);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(this, scrollPane, "Descripción generada por IA", JOptionPane.INFORMATION_MESSAGE);
            } else {  // Si no está registrado mostramos un mensaje			
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar descripción: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Funcion para sugerir una categoría para un nombre de producto usando la IA
    private void sugerirCategoriaProducto() {
        try {
        	// Solicitamos el nombre del producto que mandaremos a la API de openrouter
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
            if (nombre == null || nombre.trim().isEmpty()) return;
         // Preparamos el prompt para mandarlo a la API de openrouter.
            String prompt = "No respondas en inglés. Ponte en el contexto de una tienda de productos Otakus/Frikis. " +
                    "Dado el siguiente nombre de producto: \"" + nombre + "\", responde solo con una categoría adecuada en idioma español. " +
                    "Usa exactamente este formato:\n\nCategoria: [una sola palabra]\n\nNo agregues ninguna explicación ni texto adicional.";

            String respuesta = llmService.consultarLLM(prompt); // Mostramos la respuesta de la IA
            JOptionPane.showMessageDialog(this, respuesta, "Categoría sugerida", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {  // Controlamos las excepciones
            JOptionPane.showMessageDialog(this, "Error al sugerir categoría: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
