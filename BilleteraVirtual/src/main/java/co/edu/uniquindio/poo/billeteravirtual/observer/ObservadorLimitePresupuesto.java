package co.edu.uniquindio.poo.billeteravirtual.observer;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;
import javafx.scene.control.Label;

public class ObservadorLimitePresupuesto implements Observador {
    private Label etiquetaAviso;
    private Presupuesto presupuesto;

    public ObservadorLimitePresupuesto(Label etiquetaAviso, Presupuesto presupuesto) {
        this.etiquetaAviso = etiquetaAviso;
        this.presupuesto = presupuesto;
    }

    @Override
    public void actualizar() {
        if (presupuesto.getMontoGastado() > presupuesto.getMontoTotal()) {
            etiquetaAviso.setText("¡Límite de presupuesto superado!");
            etiquetaAviso.setStyle("-fx-text-fill: red;");
        } else {
            etiquetaAviso.setText("Presupuesto dentro del límite.");
            etiquetaAviso.setStyle("-fx-text-fill: green;");
        }
    }
}