package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionUsuario;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServicioUsuarioTest {

    private ServicioUsuario servicioUsuario;

    @Before
    public void setUp() {
        servicioUsuario = new ServicioUsuario();
    }

    @Test
    public void testRegistrarYObtenerUsuario() throws ExcepcionUsuario {
        // Crear un usuario con el builder
        Usuario usuario = new Usuario.UsuarioBuilder()
                .Nombre("Juan")
                .Cedula("123")
                .Correo("juan@email.com")
                .Telefono("3120000000")
                .ClaveAcceso("clave123")
                .build();

        // Registrar el usuario
        servicioUsuario.registrarUsuario(usuario);

        // Obtener el usuario por cédula
        Usuario resultado = servicioUsuario.obtenerUsuario("123");

        // Verificar
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("123", resultado.getCedula());
    }


}




