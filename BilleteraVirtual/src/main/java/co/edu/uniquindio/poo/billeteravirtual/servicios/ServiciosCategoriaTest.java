package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionCategoria;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Categoria;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServiciosCategoriaTest {

    @Test
    public void testRegistrarObtenerEliminarCategoria() throws ExcepcionCategoria {
        ServiciosCategoria servicio = new ServiciosCategoria();

        // Registrar una categoría
        servicio.registrarCategoria(1, "Alimentos", "Gastos en comida y supermercados");

        // Obtener la categoría y verificar datos
        Categoria categoria = servicio.obtenerCategoria(1);
        assertEquals(1, categoria.getIdCategoria());
        assertEquals("Alimentos", categoria.getNombre());
        assertEquals("Gastos en comida y supermercados", categoria.getDescripcion());

        // Listar categorías y verificar que solo hay una
        List<Categoria> lista = servicio.listarCategorias();
        assertEquals(1, lista.size());

        // Eliminar la categoría
        servicio.eliminarCategoria(1);

        // Verificar que lanza excepción al intentar obtenerla luego de eliminarla
        try {
            servicio.obtenerCategoria(1);
            fail("Se esperaba una ExcepcionCategoria al intentar obtener una categoría eliminada.");
        } catch (ExcepcionCategoria e) {
            assertEquals("No existe una categoria con el id 1", e.getMessage());
        }
    }
}
