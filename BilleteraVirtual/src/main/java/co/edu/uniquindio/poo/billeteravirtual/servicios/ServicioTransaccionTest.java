package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ServicioTransaccionTest {

    @Test
    public void testRegistrarObtenerEliminarTransaccion() throws ExcepcionTransaccion {
        ServicioTransaccion servicioTransaccion = new ServicioTransaccion();

        // Crear una fecha para la transacción
        Date fecha = new Date();

        // Registrar la transacción
        servicioTransaccion.registrarTransaccion(1, fecha, "Ingreso", 150000, "Pago salario");

        // Obtener y verificar datos
        Transaccion transaccion = servicioTransaccion.obtenerTransaccion(1, "Ingreso");
        assertEquals(1, transaccion.getIdTransaccion());
        assertEquals(fecha, transaccion.getFecha());
        assertEquals("Ingreso", transaccion.getTipo());
        assertEquals(150000, transaccion.getMonto(), 0.001);
        assertEquals("Pago salario", transaccion.getDescripcion());

        // Listar transacciones
        List<Transaccion> lista = servicioTransaccion.listarTransaciones();
        assertEquals(1, lista.size());

        // Eliminar transacción
        servicioTransaccion.eliminarTransaccion(1);

        // Verificar que se lanzó la excepción al obtenerla luego de eliminarla
        try {
            servicioTransaccion.obtenerTransaccion(1, "Ingreso");
            fail("Se esperaba una ExcepcionTransaccion al intentar obtener una transacción eliminada.");
        } catch (ExcepcionTransaccion e) {
            assertEquals("No existe una Transaccion con el id: 1", e.getMessage());
        }
    }
}
