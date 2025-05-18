package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ControllerTransaccionesAdmin{

    private final ViewFuncionalidadesAdmin view;
    private final ServicioTransaccion servicioTransaccion;

    public ControllerTransaccionesAdmin(ViewFuncionalidadesAdmin view) {
        this.view = view;
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
    }

    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneCuentas.setVisible(vistaActiva.equals("Cuentas"));
        view.paneStats.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));;
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
    }
    public void iniciarVista() {
        view.anchorPaneTransacciones.setVisible(true);
        cambiarVista("Transacciones");
        cargarTransacciones();
    }

    public void cargarTransacciones() {
        List<Transaccion> transacciones = servicioTransaccion.obtenerTodasLasTransacciones(); // Asegúrate de tener este método

        view.columnaFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        view.columnaTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        view.columnaMonto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMonto()).asObject().asString());
        view.columnaDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));

        view.tablaTransacciones.getItems().setAll(transacciones);
    }

}