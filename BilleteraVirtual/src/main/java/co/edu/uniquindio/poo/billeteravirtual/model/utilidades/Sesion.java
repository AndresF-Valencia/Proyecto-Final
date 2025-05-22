package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

/**
 * Clase Singleton que representa la sesión actual de usuario en la aplicación.
 * Permite gestionar el usuario que ha iniciado sesión.
 */
public class Sesion {
    private static Sesion instancia;
    private Usuario usuarioActual;

    /**
     * Constructor privado para evitar instanciación externa.
     */
    private Sesion() {
    }

    /**
     * Obtiene la instancia única de la sesión.
     *
     * @return instancia única de Sesion.
     */
    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    /**
     * Inicia sesión asignando el usuario actual.
     *
     * @param usuario Usuario que inicia sesión.
     */
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Obtiene el usuario que actualmente tiene la sesión iniciada.
     *
     * @return Usuario actual o null si no hay sesión iniciada.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Cierra la sesión eliminando el usuario actual.
     */
    public void cerrarSesion() {
        usuarioActual = null;
    }
}
