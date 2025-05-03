package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.estrategia.ProcesadorTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.estrategia.TransaccionGasto;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class ServicioTransaccionTest {

    @Test
    public void testRegistrarObtenerEliminarTransaccion() throws ExcepcionTransaccion {
        // Crear instancia del servicio
        ServicioTransaccion servicioTransaccion = new ServicioTransaccion();

        // Crear una fecha para la transacción
        Date fecha = new Date();

        // Crear un presupuesto con monto total 1000 y monto gastado inicial 0
        Presupuesto presupuesto = new Presupuesto(1, "Presupuesto Personal", 1000.0, 0.0);

        // Crear la transacción (por ejemplo, un gasto)
        Transaccion transaccion = new Transaccion(1, fecha, "Gasto", 150.0, "Compra en supermercado");

        // Crear el procesador de transacción con la estrategia de gasto
        ProcesadorTransaccion procesador = new ProcesadorTransaccion();
        procesador.setEstrategia(new TransaccionGasto()); // Usamos la estrategia de gasto para actualizar el presupuesto

        // Procesar la transacción con el presupuesto
        procesador.procesar(transaccion, presupuesto);

        // Verificar que el monto gastado en el presupuesto se haya actualizado correctamente
        assertEquals(150.0, presupuesto.getMontoGastado(), 0.001);

        // Registrar la transacción en el servicio (simulando el registro)
        servicioTransaccion.registrarTransaccion(1, fecha, "Gasto", 150.0, "Compra supermercado");

        // Obtener la transacción registrada
        Transaccion transaccionObtenida = servicioTransaccion.obtenerTransaccion(1, "Gasto");
        assertEquals(1, transaccionObtenida.getIdTransaccion());
        assertEquals(fecha, transaccionObtenida.getFecha());
        assertEquals("Gasto", transaccionObtenida.getTipo());
        assertEquals(150.0, transaccionObtenida.getMonto(), 0.001);
        assertEquals("Compra supermercado", transaccionObtenida.getDescripcion());

        // Verificar la lista de transacciones
        List<Transaccion> lista = servicioTransaccion.listarTransaciones();
        assertEquals(1, lista.size());

        // Eliminar la transacción
        servicioTransaccion.eliminarTransaccion(1);

        // Verificar que se lanzó la excepción al intentar obtener la transacción eliminada
        try {
            servicioTransaccion.obtenerTransaccion(1, "Gasto");
            fail("Se esperaba una ExcepcionTransaccion al intentar obtener una transacción eliminada.");
        } catch (ExcepcionTransaccion e) {
            assertEquals("No existe una Transaccion con el id: 1", e.getMessage());
        }
    }

    @Test(expected = ExcepcionTransaccion.class)
    public void testTransaccionMontoNegativo() throws ExcepcionTransaccion {
        ServicioTransaccion servicioTransaccion = new ServicioTransaccion();

        // Crear una fecha para la transacción
        Date fecha = new Date();

        // Intentar registrar una transacción con un monto negativo
        servicioTransaccion.registrarTransaccion(1, fecha, "Gasto", -100.0, "Compra supermercado");
    }
}

