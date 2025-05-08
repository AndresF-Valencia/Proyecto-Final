package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ControllerCuenta {
    public Usuario usuarioActual;
    public ServicioCuenta servicioCuenta;
    private final ViewFuncionalidades view;

    public ControllerCuenta(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.servicioCuenta = new ServicioCuenta();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
    }

    public void cambiarVista(String vistaActiva){
        view.anchorPaneGestionarCuenta.setVisible(vistaActiva.equals("Gestionar Cuenta"));
        view.anchorPaneRegistroCuenta.setVisible(vistaActiva.equals("Registrar Cuenta"));
    }

    public void agregarCuenta(){
        cambiarVista("Registrar Cuenta");
    }

    public void gestionarCuentas(){
        cambiarVista("Gestionar Cuenta");
    }

    public void registrarCuenta() {
        String numeroCuenta = view.getCampoNumeroCuenta().getText();
        String tipoCuenta = view.getComboTipoCuenta().getValue();
        String bancoCuenta = view.getCampoTitular().getText();

        if(numeroCuenta.isEmpty() || tipoCuenta.isEmpty() || bancoCuenta.isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane,"Debe llenar todos los campos");
        }
        servicioCuenta.registrarCuenta(numeroCuenta, tipoCuenta, bancoCuenta, usuarioActual);
        cargarCuentas();
        Logger.getInstance().mostrarToast(view.rootPane,"Cuenta Registrada con exito");
        view.getCampoNumeroCuenta().clear();
        view.getCampoTitular().clear();
    }

    public void consultarCuenta(){
        Cuenta cuentaSeleccionada = view.comboCuentas.getValue();

        if (cuentaSeleccionada != null) {
            view.textTitular.setText(cuentaSeleccionada.getBancoCuenta());
            view.textNumeroCuenta.setText(cuentaSeleccionada.getNumeroCuenta());
            view.textTipoCuenta.setText(cuentaSeleccionada.getTipoCuenta());
            view.textSaldo.setText(cuentaSeleccionada.getSaldo());

        } else {
            Logger.getInstance().mostrarToast(view.rootPane,"Debe seleccionar una cuenta");
        }
    }

    public void eliminarCuenta(){
        Cuenta cuentaSeleccionada = view.comboCuentas.getValue();
        if (cuentaSeleccionada != null) {
            usuarioActual.getCuentas().remove(cuentaSeleccionada);
            cargarCuentas();
        }
    }

    public void regresar(){
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
    }

    public void cargarCuentas() {
        List<Cuenta> cuentasUsuario = servicioCuenta.obtenerCuentasDe(usuarioActual);
        System.out.println(cuentasUsuario.toString());
        view.comboCuentas.getItems().setAll(cuentasUsuario);
    }

    public void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/billeteravirtual/interfazUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) view.btnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
