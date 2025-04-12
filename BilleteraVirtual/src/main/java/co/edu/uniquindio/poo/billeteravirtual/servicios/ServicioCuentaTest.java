package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionCuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServicioCuentaTest {

    @Test
    public void testRegistrarObtenerYEliminarCuenta() throws ExcepcionCuenta {
        ServicioCuenta servicioCuenta = new ServicioCuenta();

        // Registrar una cuenta
        servicioCuenta.registrarCuenta(1, "123456789", "Ahorros");

        // Obtener y verificar los datos de la cuenta
        Cuenta cuenta = servicioCuenta.obtenerCuenta(1);
        assertEquals(1, cuenta.getIdCuenta());
        assertEquals("123456789", cuenta.getNumeroCuenta());
        assertEquals("Ahorros", cuenta.getTipoCuenta());

        // Eliminar la cuenta
        servicioCuenta.eliminarCuenta(1);

        // Verificar que lanzar excepción al intentar obtenerla de nuevo
        try {
            servicioCuenta.obtenerCuenta(1);
            fail("Se esperaba una ExcepcionCuenta al obtener una cuenta eliminada.");
        } catch (ExcepcionCuenta e) {
            assertEquals("La cuenta no existe", e.getMessage());
        }
    }
}
