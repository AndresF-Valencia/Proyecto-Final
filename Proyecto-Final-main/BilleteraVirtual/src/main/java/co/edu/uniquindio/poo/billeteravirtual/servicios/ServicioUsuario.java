package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionUsuario;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioUsuario {
    private final Map<String, Usuario> usuarios = new HashMap<>();

    // Registrar un nuevo usuario
    public void registrarUsuario(Usuario usuario) throws ExcepcionUsuario {
        if (usuarios.containsKey(usuario.getCedula())) {
            throw new ExcepcionUsuario("Ya existe un usuario con la cédula: ");
        }
        usuarios.put(usuario.getCedula(), usuario);
        System.out.println("Usuario registrado exitosamente.");
    }

    // Obtener un usuario por cédula
    public Usuario obtenerUsuario(String cedula) throws ExcepcionUsuario {
        Usuario usuario = usuarios.get(cedula);
        if (usuario == null) {
            throw new ExcepcionUsuario("No se encontró un usuario con la cédula: " +cedula);
        }
        return usuario;
    }

    // Eliminar un usuario
    public void eliminarUsuario(String cedula) throws ExcepcionUsuario {
        if (!usuarios.containsKey(cedula)) {
            throw new ExcepcionUsuario("No existe un usuario con la cédula: " + cedula);
        }
        usuarios.remove(cedula);
        System.out.println("Usuario eliminado exitosamente.");
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    // Verificar login de usuario
    public boolean autenticarUsuario(String cedula, String claveAcceso) throws ExcepcionUsuario {
        Usuario usuario = obtenerUsuario(cedula);
        if (!usuario.getClaveAcceso().equals(claveAcceso)) {
            throw new ExcepcionUsuario("Clave de acceso incorrecta.");
        }
        return true;
    }
}
