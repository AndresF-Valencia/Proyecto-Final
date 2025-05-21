package co.edu.uniquindio.poo.billeteravirtual.test;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServicioCuentaTest {

    private ServicioCuenta servicioCuenta;
    private ServicioUsuario servicioUsuario;
    private Usuario usuario;

    @Before
    public void setUp() {
        servicioCuenta = ServicioCuenta.getInstancia();
        servicioCuenta.getCuentas().clear(); // limpiar antes de cada test

        servicioUsuario = ServicioUsuario.getInstancia();
        servicioUsuario.getUsuariosRegistrados().clear();

        usuario = new Usuario.UsuarioBuilder()
                .Nombre("Juan")
                .Cedula("1234")
                .Correo("juan@mail.com")
                .Telefono("123")
                .PalabraClave("rojo")
                .ClaveAcceso("clave123")
                .build();

        servicioUsuario.getUsuariosRegistrados().add(usuario);
    }

    @Test
    public void registrarCuenta_DeberiaAgregarCuentaNueva() {
        servicioCuenta.registrarCuenta("1111", "Ahorros", "Banco A", usuario);
        List<Cuenta> cuentasUsuario = servicioCuenta.obtenerCuentasDe(usuario);

        assertEquals(1, cuentasUsuario.size());
        assertEquals("1111", cuentasUsuario.get(0).getNumeroCuenta());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registrarCuenta_DebeLanzarExcepcionSiNumeroExiste() {
        servicioCuenta.registrarCuenta("2222", "Corriente", "Banco B", usuario);
        servicioCuenta.registrarCuenta("2222", "Ahorros", "Banco C", usuario); // debe fallar
    }

    @Test
    public void obtenerCuentas_DeberiaRetornarLasDelUsuario() {
        servicioCuenta.registrarCuenta("3333", "Corriente", "Banco C", usuario);
        servicioCuenta.registrarCuenta("4444", "Ahorros", "Banco D", usuario);

        List<Cuenta> cuentas = servicioCuenta.obtenerCuentasDe(usuario);
        assertEquals(2, cuentas.size());
    }

    @Test
    public void obtenerSaldo_DeberiaSumarSaldosDeTodasLasCuentas() {
        servicioCuenta.registrarCuenta("5555", "Corriente", "Banco E", usuario);
        servicioCuenta.registrarCuenta("6666", "Ahorros", "Banco F", usuario);

        usuario.getCuentas().get(0).setSaldo1(100.0);
        usuario.getCuentas().get(1).setSaldo1(150.0);

        double saldoTotal = servicioCuenta.obtenerSaldo(usuario);
        assertEquals(250.0, saldoTotal, 0.01);
    }

    @Test
    public void existeNumeroCuenta_DeberiaRetornarTrueSiExiste() {
        servicioCuenta.registrarCuenta("7777", "Corriente", "Banco G", usuario);
        assertTrue(servicioCuenta.existeNumeroCuenta("7777"));
    }

    @Test
    public void obtenerCuentaPorNumero_DeberiaRetornarCuentaCorrecta() {
        servicioCuenta.registrarCuenta("8888", "Ahorros", "Banco H", usuario);
        Cuenta cuenta = ServicioCuenta.obtenerCuentaPorNumero("8888");

        assertNotNull(cuenta);
        assertEquals("8888", cuenta.getNumeroCuenta());
    }

}
