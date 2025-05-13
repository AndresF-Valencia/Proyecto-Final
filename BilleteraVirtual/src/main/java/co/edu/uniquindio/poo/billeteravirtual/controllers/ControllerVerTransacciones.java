package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ControllerVerTransacciones {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;

    public ControllerVerTransacciones(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
    }

    public void iniciarVista() {
        ocultarElementoDePrincipalExceptoVerMas();
        view.PaneVerMas.setVisible(true);
        cargarTransacciones();
    }

    private void ocultarElementoDePrincipalExceptoVerMas(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.PaneVerMas);
        });
    }

    public void restaurarVistaPrincipal() {
        view.PaneTienda.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);
        view.PaneVerMas.setVisible(false);

        // Muestra solo el principal
        view.PanePrincipal.setVisible(true);
        view.PanePrincipal.toFront(); // Esto asegura que se muestre correctamente
    }

    public void cargarTransacciones() {
        List<Transaccion> transacciones = ServicioTransaccion.obtenerTransaccionesPorCliente(usuarioActual.getCedula());

        view.columnaFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        view.columnaTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        view.columnaMonto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMonto()).asObject());
        view.columnaDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));

        view.tablaTransacciones.getItems().setAll(transacciones);
    }
}
