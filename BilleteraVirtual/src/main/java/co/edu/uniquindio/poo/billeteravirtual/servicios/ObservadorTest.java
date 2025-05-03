package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Billetera;

public class ObservadorTest {
    public static void main(String[] args) {
        Billetera billetera = new Billetera();

        // Observador de consola
        billetera.agregarObservador(() -> {
            System.out.println("Saldo actualizado: " + billetera.getSaldo());
        });

        billetera.agregarDinero(50);
        billetera.retirarDinero(10);
    }
}
