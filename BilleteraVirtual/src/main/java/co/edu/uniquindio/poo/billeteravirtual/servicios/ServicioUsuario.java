package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioUsuario {
    public List<Usuario> usuariosRegistrados = new ArrayList<>();

    // Registrar un nuevo usuario
    public void registrarUsuario(String nombre, String correo, String telefono, String cedula, String palabraClave, String clave){
        Usuario usuario = new Usuario.UsuarioBuilder()
                .Nombre(nombre)
                .Cedula(cedula)
                .Telefono(telefono)
                .Correo(correo)
                .PalabraClave(palabraClave)
                .ClaveAcceso(clave)
                .build();

        usuariosRegistrados.add(usuario);
    }

    // Obtener un usuario por cédula
    public Usuario obtenerUsuario(String cedula)  {
        Usuario usuarioEncontrado = null;
        for(Usuario usuario : usuariosRegistrados){
            if(usuario.getCedula().equals(cedula)){
                usuarioEncontrado = usuario;
            }
        }
        return usuarioEncontrado;
    }

    // Eliminar un usuario
    public void eliminarUsuario(String cedula) {

        System.out.println("Usuario eliminado exitosamente.");
    }


    // Verificar login de usuario
    public boolean autenticarUsuario(String cedula, String claveAcceso) {
        boolean autenticado = false;
        for (Usuario u : usuariosRegistrados) {
            String cedulaUsuario = u.getCedula();
            String claveUsuario = u.getClaveAcceso();
            if (cedulaUsuario.equals(cedula) && claveUsuario.equals(claveAcceso)) {
                autenticado = true;
                break;
            }
        }
        return autenticado;
    }

    public List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }
}
