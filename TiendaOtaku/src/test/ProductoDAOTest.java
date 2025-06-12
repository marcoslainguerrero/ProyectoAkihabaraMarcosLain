package test;

import dao.ProductoDAO;
import model.ProductoOtaku;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductoDAOTest {
    private ProductoDAO dao;
    private ProductoOtaku productoPrueba;

    @Before
    public void setUp() {
        dao = new ProductoDAO();
        productoPrueba = new ProductoOtaku();
        productoPrueba.setNombre("Figura Luffy");
        productoPrueba.setCategoria("Figuras");
        productoPrueba.setPrecio(49.99);
        productoPrueba.setStock(10);
        dao.insertarProducto(productoPrueba);

        // Obtener ID generado automáticamente
        List<ProductoOtaku> lista = dao.obtenerTodosProductos();
        for (ProductoOtaku p : lista) {
            if (p.getNombre().equals("Figura Luffy")) {
                productoPrueba.setId(p.getId());
                break;
            }
        }
    }

    @After
    public void tearDown() {
        if (productoPrueba != null && productoPrueba.getId() > 0) {
            dao.eliminarProducto(productoPrueba.getId());
        }
    }

    @Test
    public void testInsertarProducto() {
        ProductoOtaku nuevo = new ProductoOtaku();
        nuevo.setNombre("Taza Naruto");
        nuevo.setCategoria("Accesorios");
        nuevo.setPrecio(14.99);
        nuevo.setStock(20);

        boolean insertado = dao.insertarProducto(nuevo);
        Assert.assertTrue(insertado);

        // Limpieza
        List<ProductoOtaku> productos = dao.obtenerTodosProductos();
        for (ProductoOtaku p : productos) {
            if (p.getNombre().equals("Taza Naruto")) {
                dao.eliminarProducto(p.getId());
                break;
            }
        }
    }

    @Test
    public void testObtenerTodosProductos() {
        List<ProductoOtaku> productos = dao.obtenerTodosProductos();
        Assert.assertNotNull(productos);
        Assert.assertTrue(productos.size() > 0);
    }

    @Test
    public void testObtenerProductoPorId() {
        ProductoOtaku resultado = dao.obtenerProductoPorId(productoPrueba.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals("Figura Luffy", resultado.getNombre());
    }

    @Test
    public void testActualizarProducto() {
        productoPrueba.setPrecio(59.99);
        boolean actualizado = dao.actualizarProducto(productoPrueba);
        Assert.assertTrue(actualizado);

        ProductoOtaku actualizadoProducto = dao.obtenerProductoPorId(productoPrueba.getId());
        Assert.assertEquals(59.99, actualizadoProducto.getPrecio(), 0.01);
    }

    @Test
    public void testEliminarProducto() {
        ProductoOtaku eliminar = new ProductoOtaku();
        eliminar.setNombre("Poster Goku");
        eliminar.setCategoria("Posters");
        eliminar.setPrecio(9.99);
        eliminar.setStock(5);
        dao.insertarProducto(eliminar);

        // Obtener ID y eliminar
        List<ProductoOtaku> lista = dao.obtenerTodosProductos();
        int idEliminar = -1;
        for (ProductoOtaku p : lista) {
            if (p.getNombre().equals("Poster Goku")) {
                idEliminar = p.getId();
                break;
            }
        }

        Assert.assertTrue(dao.eliminarProducto(idEliminar));
        Assert.assertNull(dao.obtenerProductoPorId(idEliminar));
    }
}
