package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioUsuario;
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
    public ServicioUsuario servicioUsuario;

    public ControllerCuenta(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.servicioCuenta = new ServicioCuenta();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
    }

    public void cambiarVista(String vistaActiva){
        view.anchorPaneGestionarCuenta.setVisible(vistaActiva.equals("Gestionar Cuenta"));
        view.anchorPaneRegistroCuenta.setVisible(vistaActiva.equals("Registrar Cuenta"));
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneCambiarContrasena.setVisible(false);
        view.anchorPanePrincipal.setVisible(false);
    }

    public void agregarCuenta(){
        cambiarVista("Registrar Cuenta");
    }

    public void gestionarCuentas(){
        cambiarVista("Gestionar Cuenta");
    }

    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void limpiarCamposRegistroCuenta() {
        view.getCampoNumeroCuenta().clear();
        view.getCampoTitular().clear();
        view.getComboTipoCuenta().getSelectionModel().clearSelection();
    }

    public void registrarCuenta() {
        String numeroCuenta = view.getCampoNumeroCuenta().getText();
        String tipoCuenta = view.getComboTipoCuenta().getValue();
        String bancoCuenta = view.getCampoTitular().getText();

        if(camposVacios(numeroCuenta, tipoCuenta, bancoCuenta)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Debe llenar todos los campos");
            return;
        }

        if (servicioCuenta.existeNumeroCuenta(numeroCuenta)) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ El número de cuenta ya está en uso.");
            return;
        }

        servicioCuenta.registrarCuenta(numeroCuenta, tipoCuenta, bancoCuenta, usuarioActual);
        cargarCuentas();
        Logger.getInstance().mostrarToast(view.rootPane, "✅ Cuenta registrada con éxito");
        limpiarCamposRegistroCuenta();
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
        }else{
            Logger.getInstance().mostrarToast(view.rootPane,"Debe seleccionar una cuenta");
        }
    }

    public void Inicio(){
        view.anchorPanePrincipal.setVisible(true);
        view.PanePrincipal.setVisible(true);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneCambiarContrasena.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneTienda.setVisible(false);

    }

    public void regresar(){
        view.anchorPanePrincipal.setVisible(true);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneCambiarContrasena.setVisible(false);
    }

    public void cargarCuentas() {
        List<Cuenta> cuentasUsuario = servicioCuenta.obtenerCuentasDe(usuarioActual);
        System.out.println(cuentasUsuario.toString());
        view.comboCuentas.getItems().setAll(cuentasUsuario);
    }

    public void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        for(Usuario u: servicioUsuario.getUsuariosRegistrados()){
            System.out.println(u.toString());
        }
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
