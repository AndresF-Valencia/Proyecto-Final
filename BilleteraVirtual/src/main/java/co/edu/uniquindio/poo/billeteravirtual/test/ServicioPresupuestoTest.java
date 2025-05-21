package co.edu.uniquindio.poo.billeteravirtual.test;
import co.edu.uniquindio.poo.billeteravirtual.model.decoradores.UsuarioConPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioPresupuesto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServicioPresupuestoTest {

    private ServicioPresupuesto servicio;
    private Usuario usuario;
    private Presupuesto presupuestoCategoria;
    private Presupuesto presupuestoGeneral;

    @Before
    public void setUp() {
        servicio = ServicioPresupuesto.getInstancia();
        servicio.presupuestosPorUsuario.clear(); // Limpia estado previo

        usuario = new Usuario.UsuarioBuilder()
                .Nombre("Ana")
                .Cedula("111")
                .Correo("ana@mail.com")
                .Telefono("123")
                .PalabraClave("rojo")
                .ClaveAcceso("123")
                .build();

        presupuestoCategoria = new Presupuesto("123", "Comida", 300000.0, false);
        presupuestoCategoria.setMontoGastado(10000);
        presupuestoCategoria.setCategoria("Alimentos");
        presupuestoGeneral = new Presupuesto("321", "General", 300000.0, true);
        presupuestoGeneral.setMontoGastado(20000);
    }

    @Test
    public void agregarYObtenerPresupuestos_DeberiaGuardarYRecuperarCorrectamente() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);

        List<Presupuesto> obtenidos = servicio.obtenerPresupuestos(usuario);
        assertEquals(1, obtenidos.size());
        assertTrue(obtenidos.contains(presupuestoCategoria));
    }

    @Test
    public void crearUsuarioConPresupuesto_DeberiaIncluirPresupuestosDelUsuario() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);
        servicio.agregarPresupuesto(usuario, presupuestoGeneral);

        UsuarioConPresupuesto decorado = servicio.crearUsuarioConPresupuesto(usuario);

        assertNotNull(decorado);
        assertEquals(2, decorado.getPresupuestos().size());
        assertTrue(decorado.getPresupuestos().contains(presupuestoGeneral));
    }

    @Test
    public void obtenerMontoGastado_DeberiaRetornarMontoCorrectoSegunCategoria() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);
        servicio.agregarPresupuesto(usuario, presupuestoGeneral);

        assertEquals(10000.0, servicio.obtenerMontoGastado(usuario, "Alimentos"), 0.001);
        assertEquals(20000.0, servicio.obtenerMontoGastado(usuario, "General"), 0.001);
    }

    @Test
    public void obtenerMontoTotal_DeberiaRetornarMontoCorrectoSegunCategoria() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);
        servicio.agregarPresupuesto(usuario, presupuestoGeneral);

        assertEquals(300000.0, servicio.obtenerMontoTotal(usuario, "Alimentos"), 0.001);
        assertEquals(300000.0, servicio.obtenerMontoTotal(usuario, "General"), 0.001);
    }

    @Test
    public void obtenerMontoDisponible_DeberiaCalcularCorrectamente() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);
        servicio.agregarPresupuesto(usuario, presupuestoGeneral);

        assertEquals(290000.0, servicio.obtenerMontoDisponible(usuario, "Alimentos"), 0.001);
        assertEquals(280000.0, servicio.obtenerMontoDisponible(usuario, "General"), 0.001);
    }

    @Test
    public void eliminarPresupuesto_DeberiaRemoverCorrectamente() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);
        servicio.eliminarPresupuesto(usuario, presupuestoCategoria);

        List<Presupuesto> obtenidos = servicio.obtenerPresupuestos(usuario);
        assertFalse(obtenidos.contains(presupuestoCategoria));
    }

    @Test
    public void actualizarMontoPresupuesto_DeberiaCambiarElMontoTotal() {
        servicio.agregarPresupuesto(usuario, presupuestoCategoria);
        servicio.actualizarMontoPresupuesto(usuario, presupuestoCategoria, 700000.0);

        assertEquals(700000.0, presupuestoCategoria.getMontoTotal(), 0.001);
    }
}
