package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import co.edu.uniquindio.poo.billeteravirtual.model.observer.SujetoObservable;

public class Billetera extends SujetoObservable {
    private double saldo;

    public double getSaldo() {
        return saldo;
    }

    public void agregarDinero(double monto) {
        saldo += monto;
        notificarObservadores();
    }

    public void retirarDinero(double monto) {
        saldo -= monto;
        notificarObservadores();
    }
}
