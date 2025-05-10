package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;

public class ControllerDatos {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;
    private final ServicioUsuario servicioUsuario;


    public ControllerDatos(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
    }

    public void cambiarVista(String vistaActiva){
        view.anchorPaneVerDatosUsuario.setVisible(vistaActiva.equals("Ver Datos"));
        view.anchorPaneCambiarContrasena.setVisible(vistaActiva.equals("Cambiar Contrasena"));
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
        view.anchorPanePrincipal.setVisible(false);
    }

    public void verDatosUsuario() {
        cambiarVista("Ver Datos");
        if (usuarioActual != null) {
            view.camposInformacion.setVisible(true);
            view.paneEditarInformacion.setVisible(false);
            actualizarVistaDatosUsuario();
        }
    }

    public void mostrarPanelEditarDatos( ){
        view.camposInformacion.setVisible(false);
        view.paneEditarInformacion.setVisible(true);
        view.btnModificarDatos.setVisible(false);
        view.btnGuardarCambios.setVisible(true);
    }


    public void editarDatosUsuario() {
        if (view.getTxtCorreoEditar().getText().isEmpty() || view.getTxtTelefonoEditar().getText().isEmpty() || view.getTxtNombreEditar().getText().isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane,"Por favor completa todos los campos.");
            return;
        }
        if (usuarioActual != null) {
            usuarioActual.setCorreo(view.txtCorreoEditar.getText());
            usuarioActual.setTelefono(view.txtTelefonoEditar.getText());
            usuarioActual.setNombre(view.txtNombreEditar.getText());
            limpiarCampos();
            actualizarVistaDatosUsuario();
            view.camposInformacion.setVisible(true);
            view.paneEditarInformacion.setVisible(false);
            view.btnModificarDatos.setVisible(true);
            view.btnGuardarCambios.setVisible(false);

        }
    }

    public void mostrarCambiarContrasena() {
        cambiarVista("Cambiar Contrasena");
    }

    public void guardarCambioClave() {
        String claveActual = view.getPfClaveActual().getText();
        String nuevaClave = view.getPfNuevaClave().getText();
        String confirmarClave = view.getPfConfirmarClave().getText();
        String palabraClaveActual = view.getTxtPalabraClaveActual().getText();
        String palabraClaveNueva = view.getTxtNuevaPalabraClave().getText();
        if (camposVacios(claveActual, nuevaClave, confirmarClave, palabraClaveActual, palabraClaveNueva)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Debe llenar todos los campos.");
            return;
        }

        if(mostrarErrorSi(!usuarioActual.getClaveAcceso().equals(claveActual),"La clave actual no es correcta."))return;
        if(mostrarErrorSi(!nuevaClave.equals(confirmarClave),"Las claves nuevas no coinciden."))return;
        if(mostrarErrorSi(!usuarioActual.getPalabraclave().equals(palabraClaveActual),"Las palabra claves no coinciden."))return;

        usuarioActual.setClaveAcceso(nuevaClave);
        usuarioActual.setPalabraclave(palabraClaveNueva);
        servicioUsuario.actualizarUsuario(usuarioActual);
        Logger.getInstance().mostrarToast(view.rootPane, "Clave actualizada con éxito.");
        limpiarCampos();
    }

    private void actualizarVistaDatosUsuario() {
        view.textNombreUsuario.setText(usuarioActual.getNombre());
        view.textCorreoUsuario.setText(usuarioActual.getCorreo());
        view.textDocumentoUsuario.setText(usuarioActual.getCedula());
        view.textTelefonoUsuario.setText(usuarioActual.getTelefono());
    }

    private void limpiarCampos(){
        view.getPfClaveActual().clear();
        view.getPfNuevaClave().clear();
        view.getPfConfirmarClave().clear();
        view.getTxtPalabraClaveActual().clear();
        view.getTxtNuevaPalabraClave().clear();
        view.getTxtNombreEditar().clear();
        view.getTxtCorreoEditar().clear();
        view.getTxtTelefonoEditar().clear();
    }

    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }

    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
