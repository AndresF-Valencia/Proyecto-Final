package co.edu.uniquindio.poo.billeteravirtual.test;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ServicioTransaccionTest {

    private ServicioTransaccion servicioTransaccion;
    private ServicioCuenta servicioCuenta;
    private ServicioUsuario servicioUsuario;

    private Usuario usuario1;
    private Usuario usuario2;
    private Cuenta cuenta1;
    private Cuenta cuenta2;

    @Before
    public void setUp() {
        servicioTransaccion = ServicioTransaccion.getInstancia();
        servicioCuenta = ServicioCuenta.getInstancia();
        servicioUsuario = ServicioUsuario.getInstancia();

        // Limpiar antes de cada test
        servicioUsuario.getUsuariosRegistrados().clear();
        servicioCuenta.getCuentas().clear();
        servicioTransaccion.getCompras().clear();
        servicioTransaccion.getDepositos().clear();
        servicioTransaccion.getRetiros().clear();
        servicioTransaccion.getTransferencias().clear();

        // Crear usuarios
        usuario1 = new Usuario.UsuarioBuilder()
                .Nombre("Ana")
                .Cedula("111")
                .Correo("ana@mail.com")
                .Telefono("123")
                .PalabraClave("rojo")
                .ClaveAcceso("123")
                .build();

        usuario2 = new Usuario.UsuarioBuilder()
                .Nombre("Luis")
                .Cedula("222")
                .Correo("luis@mail.com")
                .Telefono("456")
                .PalabraClave("azul")
                .ClaveAcceso("456")
                .build();

        servicioUsuario.getUsuariosRegistrados().add(usuario1);
        servicioUsuario.getUsuariosRegistrados().add(usuario2);

        // Registrar cuentas
        servicioCuenta.registrarCuenta("ACC001", "Ahorros", "Banco A", usuario1);
        servicioCuenta.registrarCuenta("ACC002", "Corriente", "Banco B", usuario2);

        cuenta1 = usuario1.getCuentas().get(0);
        cuenta2 = usuario2.getCuentas().get(0);
    }

    @Test
    public void agregarTransaccionPorTipo() {
        Transaccion transaccion = new Transaccion("123", LocalDate.now() ,"COMPRA", 50000, "Compra exitosa de arroz", "ACC001", Transaccion.CUENTAEXTERNA );
        servicioTransaccion.agregarTransaccionPorTipo("COMPRA", transaccion);

        List<Transaccion> compras = servicioTransaccion.getCompras();
        assertEquals(1, compras.size());
        assertEquals("123", compras.get(0).getIdTransaccion());
    }

    @Test
    public void obtenerTransaccionesPorTipo() {
        Transaccion trans = new Transaccion("234", LocalDate.now(), "Transferencia", 100000, "Transferencia exitosa", "ACC001", "ACC002" );
        servicioTransaccion.agregarTransaccionPorTipo("TRANSFERENCIA", trans);

        List<Transaccion> transferencias = servicioTransaccion.obtenerTransaccionesPorTipo("TRANSFERENCIA");
        assertEquals(1, transferencias.size());
        assertEquals("234", transferencias.get(0).getIdTransaccion());
    }

    @Test
    public void obtenerTransaccionesPorCliente() {
        Transaccion trans1 = new Transaccion("123", LocalDate.now(), "Transferencia", 100000, "Transferencia exitosa", "ACC001", "ACC002" );
        Transaccion trans2 = new Transaccion("234", LocalDate.now(), "Transferencia", 300000, "Transferencia exitosa", "ACC001", "ACC002" );

        servicioTransaccion.agregarTransaccionPorTipo("TRANSFERENCIA", trans1);
        servicioTransaccion.agregarTransaccionPorTipo("TRANSFERENCIA", trans2);

        List<Transaccion> transacciones = servicioTransaccion.obtenerTransaccionesPorCliente("111"); // usuario1
        assertEquals(2, transacciones.size());
    }

    @Test
    public void existeTransaccion() {
        Transaccion trans = new Transaccion("123", LocalDate.now() ,"COMPRA", 50000, "Compra exitosa de arroz", "ACC001", "ACC002" );
        servicioTransaccion.agregarTransaccionPorTipo("COMPRA", trans);

        assertTrue(servicioTransaccion.existeTransaccion("123"));
    }

    @Test
    public void contarProductosMasComprados() {
        Transaccion compra1 = new Transaccion("123", LocalDate.now() ,"COMPRA", 50000, "Compra exitosa de arroz", "ACC001", Transaccion.CUENTAEXTERNA );
        Transaccion compra2 = new Transaccion("134", LocalDate.now() ,"COMPRA", 50000, "Compra exitosa de arroz", "ACC001", Transaccion.CUENTAEXTERNA );
        Transaccion compra3 = new Transaccion("167", LocalDate.now() ,"COMPRA", 50000, "Compra exitosa de leche", "ACC001", Transaccion.CUENTAEXTERNA );
        servicioTransaccion.agregarTransaccionPorTipo("COMPRA", compra1);
        servicioTransaccion.agregarTransaccionPorTipo("COMPRA", compra2);
        servicioTransaccion.agregarTransaccionPorTipo("COMPRA", compra3);

        Map<String, Integer> conteo = servicioTransaccion.contarProductosMasComprados();

        assertEquals(2, conteo.size());
        assertEquals(Integer.valueOf(2), conteo.get("arroz"));
        assertEquals(Integer.valueOf(1), conteo.get("leche"));
    }
}
