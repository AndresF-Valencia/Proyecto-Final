package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;

public class ControllerDatos {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;


    public ControllerDatos(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
    }

    public void cambiarVista(String vistaActiva){
        view.anchorPaneVerDatosUsuario.setVisible(true);
        view.paneEditarInformacion.setVisible(false);
    }

    public void verDatosUsuario() {
        cambiarVista("Ver Datos");
        if (usuarioActual != null) {
            System.out.println("Usuario actual: " + usuarioActual);
            view.anchorPaneVerDatosUsuario.setVisible(true);
            view.paneEditarInformacion.setVisible(false);
            view.textNombreUsuario.setText(usuarioActual.getNombre());
            view.textCorreoUsuario.setText(usuarioActual.getCorreo());
            view.textDocumentoUsuario.setText(usuarioActual.getCedula());
            view.textTelefonoUsuario.setText(usuarioActual.getTelefono());
        } else {
            System.out.println("usuarioActual es null");
        }
    }

    public void mostrarPanelEditarDatos( ){
        System.out.println("Se presionó Modificar Datos");
        view.camposInformacion.setVisible(false);
        view.paneEditarInformacion.setVisible(true);
    }


    public void editarDatosUsuario() {
        if (view.txtCorreoEditar.getText().isBlank() || view.txtTelefonoEditar.getText().isBlank() || view.txtNombreEditar.getText().isBlank()) {
            System.out.println("Por favor completa todos los campos.");
            return;
        }
        if (usuarioActual != null) {
            // Actualiza los datos del usuario
            usuarioActual.setCorreo(view.txtCorreoEditar.getText());
            usuarioActual.setTelefono(view.txtTelefonoEditar.getText());
            usuarioActual.setNombre(view.txtNombreEditar.getText());

            // Vuelve al panel de visualización
            view.camposInformacion.setVisible(true);
            view.paneEditarInformacion.setVisible(false);

            // Actualiza los textos en pantalla con los nuevos datos
            view.textNombreUsuario.setText(usuarioActual.getNombre());
            view.textCorreoUsuario.setText(usuarioActual.getCorreo());
            view.textTelefonoUsuario.setText(usuarioActual.getTelefono());

            System.out.println("Usuario actualizado: " + usuarioActual);
        }
    }



    public void volver() {
        view.anchorPaneVerDatosUsuario.setVisible(false);
    }
}
