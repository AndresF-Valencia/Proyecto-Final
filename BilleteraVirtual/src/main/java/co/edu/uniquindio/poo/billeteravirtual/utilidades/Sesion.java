package co.edu.uniquindio.poo.billeteravirtual.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;

public class Sesion {
    private static Usuario usuarioActual;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}

