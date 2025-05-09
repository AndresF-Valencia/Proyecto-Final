package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;


public class ControllerCambioClave {

    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;

    public ControllerCambioClave(ViewFuncionalidades view) {
        this.view = view;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        inicializarEventos();
    }

    public void inicializarEventos() {
        view.btnCambiarContrasena.setOnAction(e -> mostrarPanelCambioClave());
        view.btnGuardarCambioClave.setOnAction(e -> guardarCambioClave());
        view.btnVolverCambioClave.setOnAction(e -> volver());
    }

    private void mostrarPanelCambioClave() {
        view.anchorPaneCambiarContrasena.setVisible(true);
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
    }

    private void guardarCambioClave() {
        String claveActual = view.pfClaveActual.getText();
        String nuevaClave = view.pfNuevaClave.getText();
        String confirmarClave = view.pfConfirmarClave.getText();

        if (claveActual.isEmpty() || nuevaClave.isEmpty() || confirmarClave.isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane, "Debe llenar todos los campos.");
            return;
        }

        if (!usuarioActual.getClaveAcceso().equals(claveActual)) {
            Logger.getInstance().mostrarToast(view.rootPane, "La clave actual no es correcta.");
            return;
        }

        if (!nuevaClave.equals(confirmarClave)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Las nuevas claves no coinciden.");
            return;
        }

        usuarioActual.setClaveAcceso(nuevaClave);
        Logger.getInstance().mostrarToast(view.rootPane, "Clave actualizada con éxito.");

        // Limpiar campos y volver
        view.pfClaveActual.clear();
        view.pfNuevaClave.clear();
        view.pfConfirmarClave.clear();
        volver();
    }

    private void volver() {
        view.anchorPaneCambiarContrasena.setVisible(false);
    }
}