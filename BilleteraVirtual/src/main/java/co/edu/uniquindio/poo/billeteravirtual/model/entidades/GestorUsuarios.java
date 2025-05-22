package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria que gestiona la lista de usuarios del sistema.
 */
public class GestorUsuarios {

    private static final List<Usuario> listaUsuarios = new ArrayList<>();

    /**
     * Agrega un usuario a la lista.
     * @param usuario Usuario a agregar.
     */
    public static void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    /**
     * Elimina un usuario de la lista.
     * @param usuario Usuario a eliminar.
     */
    public static void eliminarUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
    }

    /**
     * Retorna la lista completa de usuarios registrados.
     * @return Lista de usuarios.
     */
    public static List<Usuario> obtenerUsuarios() {
        return listaUsuarios;
    }

    /**
     * Limpia la lista de usuarios.
     */
    public static void limpiarUsuarios() {
        listaUsuarios.clear();
    }
}

