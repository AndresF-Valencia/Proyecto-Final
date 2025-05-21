package co.edu.uniquindio.poo.billeteravirtual.test;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServicioUsuarioTest {

    private ServicioUsuario servicio;

    @Before
    public void setUp() {
        servicio = ServicioUsuario.getInstancia();
        servicio.setUsuariosRegistrados(new ArrayList<>()); // limpiar lista antes de cada test
    }

    @Test
    public void registrarUsuario_DeberiaAgregarUsuarioNuevo() {
        servicio.registrarUsuario("Ana", "ana@mail.com", "123", "1", "gato", "clave123");

        Usuario u = servicio.obtenerUsuario("1");
        assertNotNull(u);
        assertEquals("Ana", u.getNombre());
    }

    @Test
    public void registrarUsuario_Repetido_NoDebeAgregar() {
        servicio.registrarUsuario("Juan", "juan@mail.com", "456", "2", "perro", "clave456");
        servicio.registrarUsuario("Juan", "juan@mail.com", "456", "2", "perro", "clave456"); // duplicado

        assertEquals(1, servicio.getUsuariosRegistrados().size());
    }

    @Test
    public void autenticarUsuario_DatosCorrectos_DebeAutenticar() {
        servicio.registrarUsuario("Laura", "laura@mail.com", "789", "3", "loro", "clave789");

        boolean autenticado = servicio.autenticarUsuario("3", "clave789");
        assertTrue(autenticado);
    }

    @Test
    public void autenticarUsuario_DatosIncorrectos_NoDebeAutenticar() {
        servicio.registrarUsuario("Pepe", "pepe@mail.com", "111", "4", "conejo", "clave111");

        boolean autenticado = servicio.autenticarUsuario("4", "claveIncorrecta");
        assertFalse(autenticado);
    }

    @Test
    public void eliminarUsuario_DeberiaEliminarUsuario() {
        servicio.registrarUsuario("Luz", "luz@mail.com", "222", "5", "pez", "clave222");
        servicio.eliminarUsuario("5");

        assertNull(servicio.obtenerUsuario("5"));
    }

    @Test
    public void actualizarUsuario_DeberiaActualizarClaveYPregunta() {
        servicio.registrarUsuario("Marta", "marta@mail.com", "333", "6", "raton", "clave333");

        Usuario actualizado = new Usuario.UsuarioBuilder()
                .Nombre("Marta")
                .Correo("marta@mail.com")
                .Telefono("333")
                .Cedula("6")
                .PalabraClave("nuevoRaton")
                .ClaveAcceso("nuevaClave")
                .build();

        servicio.actualizarUsuario(actualizado);

        Usuario result = servicio.obtenerUsuario("6");
        assertEquals("nuevaClave", result.getClaveAcceso());
        assertEquals("nuevoRaton", result.getPalabraclave());
    }
}

