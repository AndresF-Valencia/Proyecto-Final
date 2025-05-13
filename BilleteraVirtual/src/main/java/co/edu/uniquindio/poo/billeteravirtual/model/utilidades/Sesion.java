package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

public class Sesion {
    private static Sesion instancia;
    private Usuario usuarioActual;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }
}

