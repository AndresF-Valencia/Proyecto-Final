package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServicioPresupuestoTest {

    @Test
    public void testRegistrarObtenerEliminarPresupuesto() throws ExcepcionPresupuesto {
        ServicioPresupuesto servicio = new ServicioPresupuesto();

        // Registrar un presupuesto válido
        servicio.registrarPresupuesto(1, "Viaje a la playa", 2000000, 500000);

        // Obtenerlo y verificar datos
        Presupuesto p = servicio.obtenerPresupuesto(1);
        assertEquals(1, p.getIdPresupuesto());
        assertEquals("Viaje a la playa", p.getDescripcion());
        assertEquals(2000000, p.getMontoTotal(), 0.01);
        assertEquals(500000, p.getMontoGastado(), 0.01);

        // Listar y verificar cantidad
        List<Presupuesto> lista = servicio.listarPresupuestos();
        assertEquals(1, lista.size());

        // Eliminarlo
        servicio.eliminarPresupuesto(1);

        // Verificar que ya no existe
        try {
            servicio.obtenerPresupuesto(1);
            fail("Se esperaba una ExcepcionPresupuesto al obtener un presupuesto eliminado.");
        } catch (ExcepcionPresupuesto e) {
            assertEquals("No se encontro ningún presupuesto con dicha ID1", e.getMessage());
        }
    }

    @Test
    public void testRegistrarPresupuestoExistenteYLimite() throws ExcepcionPresupuesto {
        ServicioPresupuesto servicio = new ServicioPresupuesto();
        servicio.registrarPresupuesto(2, "Educación", 1000000, 200000);

        // Intentar registrar mismo ID
        try {
            servicio.registrarPresupuesto(2, "Otra cosa", 500000, 100000);
            fail("Se esperaba excepción por ID duplicado.");
        } catch (ExcepcionPresupuesto e) {
            assertEquals("Ya existe un presupuesto con el id 2", e.getMessage());
        }

        // Intentar registrar con montoGastado mayor al montoTotal
        try {
            servicio.registrarPresupuesto(3, "Error lógico", 100000, 200000);
            fail("Se esperaba excepción por lógica de montos.");
        } catch (ExcepcionPresupuesto e) {
            assertEquals("No tenes tantas lukas mi rey", e.getMessage());
        }
    }
}

