package co.edu.uniquindio.poo.billeteravirtual.model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Administra una lista de observadores y envía notificaciones.
 */
public abstract class SujetoObservable {
    private static List<Observador> observadores = new ArrayList<>();

    /**
     * Agrega un observador a la lista.
     *
     * @param obs Observador que se desea registrar.
     */
    public static void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    /**
     * Elimina un observador de la lista.
     *
     * @param obs Observador que se desea eliminar.
     */
    public static void eliminarObservador(Observador obs) {
        observadores.remove(obs);
    }

    /**
     * Notifica a todos los observadores registrados con una actualización general.
     */
    public static void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizar();
        }
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio en el saldo.
     */
    public static void notificarSaldo() {
        for (Observador o : observadores) {
            o.actualizarSaldo();
        }
    }
}

