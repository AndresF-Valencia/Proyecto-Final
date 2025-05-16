package co.edu.uniquindio.poo.billeteravirtual.model.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class SujetoObservable {
    private static List<Observador> observadores = new ArrayList<>();

    public static void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    public static void eliminarObservador(Observador obs) {
        observadores.remove(obs);
    }

    public static void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizar();
        }
    }

    public static void notificarSaldo() {
        for (Observador o : observadores) {
            o.actualizarSaldo();
        }
    }
}
