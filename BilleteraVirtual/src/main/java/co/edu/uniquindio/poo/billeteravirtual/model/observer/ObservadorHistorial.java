package co.edu.uniquindio.poo.billeteravirtual.model.observer;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import javafx.scene.control.TableView;

import java.util.List;

public class ObservadorHistorial implements Observador {
    private TableView<Transaccion> tabla;
    private List<Transaccion> historial;

    public ObservadorHistorial(TableView<Transaccion> tabla, List<Transaccion> historial) {
        this.tabla = tabla;
        this.historial = historial;
    }

    @Override
    public void actualizar() {
        tabla.getItems().setAll(historial);
    }
}
