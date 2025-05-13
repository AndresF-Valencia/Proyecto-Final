package co.edu.uniquindio.poo.billeteravirtual.model.observer;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Billetera;
import java.awt.*;


public class ObservadorSaldo implements Observador{
    private Label etiquetaSaldo;
    private Billetera billetera;

    public ObservadorSaldo(Label etiquetaSaldo, Billetera billetera) {
        this.etiquetaSaldo = etiquetaSaldo;
        this.billetera = billetera;
    }

    @Override
    public void actualizar() {
        etiquetaSaldo.setText("Saldo: $" + billetera.getSaldo());
    }
}
