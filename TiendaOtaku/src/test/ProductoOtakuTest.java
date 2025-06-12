package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ProductoOtaku;

public class ProductoOtakuTest {

    @Test
    public void testProductoOtaku() {
        ProductoOtaku producto = new ProductoOtaku();
        assertNotNull(producto);
    }

    @Test
    public void testProductoOtakuIntStringStringDoubleInteger() {
        ProductoOtaku producto = new ProductoOtaku(1, "Naruto", "Figura", 29.99, 10);
        assertEquals(1, producto.getId());
        assertEquals("Naruto", producto.getNombre());
        assertEquals("Figura", producto.getCategoria());
        assertEquals(29.99, producto.getPrecio(), 0.001);
        assertEquals(Integer.valueOf(10), producto.getStock());
    }

    @Test
    public void testGetId() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setId(5);
        assertEquals(5, producto.getId());
    }

    @Test
    public void testSetId() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setId(10);
        assertEquals(10, producto.getId());
    }

    @Test
    public void testGetNombre() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setNombre("Luffy");
        assertEquals("Luffy", producto.getNombre());
    }

    @Test
    public void testSetNombre() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setNombre("Goku");
        assertEquals("Goku", producto.getNombre());
    }

    @Test
    public void testGetCategoria() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setCategoria("Poster");
        assertEquals("Poster", producto.getCategoria());
    }

    @Test
    public void testSetCategoria() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setCategoria("Camiseta");
        assertEquals("Camiseta", producto.getCategoria());
    }

    @Test
    public void testGetPrecio() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setPrecio(19.95);
        assertEquals(19.95, producto.getPrecio(), 0.001);
    }

    @Test
    public void testSetPrecio() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setPrecio(35.50);
        assertEquals(35.50, producto.getPrecio(), 0.001);
    }

    @Test
    public void testGetStock() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setStock(20);
        assertEquals(Integer.valueOf(20), producto.getStock());
    }

    @Test
    public void testSetStock() {
        ProductoOtaku producto = new ProductoOtaku();
        producto.setStock(50);
        assertEquals(Integer.valueOf(50), producto.getStock());
    }

    @Test
    public void testToString() {
        ProductoOtaku producto = new ProductoOtaku(1, "Naruto", "Figura", 29.99, 10);
        String esperado = "Producto: [id=1, nombre= Naruto, categoria= Figura, precio=29.99, stock=10]";
        assertEquals(esperado, producto.toString());
    }
}
