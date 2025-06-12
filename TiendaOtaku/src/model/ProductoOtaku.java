package model;

public class ProductoOtaku {
    private int id;              // Identificador único del producto
    private String nombre;       // Nombre del producto
    private String categoria;    // Categoría del producto (ej: figura, manga, etc.)
    private Double precio;       // Precio del producto
    private Integer stock;       // Cantidad disponible en inventario
    
    // Constructor vacío necesario para crear objetos sin parámetros
    public ProductoOtaku() {
    }
    
    // Constructor con parámetros para inicializar todos los atributos
    public ProductoOtaku(int id, String nombre, String categoria, Double precio, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }
    
    // Métodos getters y setters para acceder y modificar los atributos
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    // Método para representar el objeto como cadena, útil para mostrar información
    @Override
    public String toString() {
        return "Producto: [id=" + id + ", nombre= " + nombre + ", categoria= " + categoria + ", precio=" + precio
                + ", stock=" + stock + "]";
    }
}
