package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {

    private static final List<Usuario> listaUsuarios = new ArrayList<>();

    public static void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public static void eliminarUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
    }

    public static List<Usuario> obtenerUsuarios() {
        return listaUsuarios;
    }

    public static void limpiarUsuarios() {
        listaUsuarios.clear();
    }
}
