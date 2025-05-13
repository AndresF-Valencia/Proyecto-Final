package co.edu.uniquindio.poo.billeteravirtual.model.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class SujetoObservable {
    private List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    public void eliminarObservador(Observador obs) {
        observadores.remove(obs);
    }

    public void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizar();
        }
    }
}
