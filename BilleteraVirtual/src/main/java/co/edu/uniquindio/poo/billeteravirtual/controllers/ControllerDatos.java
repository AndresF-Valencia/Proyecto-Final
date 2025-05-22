package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;

/**
 * Controlador para gestionar la vista y edición de datos del usuario.
 * Permite visualizar y modificar la información personal y cambiar la contraseña.
 */
public class ControllerDatos {

    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;
    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor que inicializa el controlador con la vista y obtiene el usuario actual en sesión.
     *
     * @param viewFuncionalidades La vista con la que interactúa el controlador.
     */
    public ControllerDatos(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
    }

    /**
     * Cambia la vista activa según el panel indicado.
     *
     * @param vistaActiva Nombre del panel a mostrar.
     */
    public void cambiarVista(String vistaActiva){
        view.anchorPaneVerDatosUsuario.setVisible(vistaActiva.equals("Ver Datos"));
        view.anchorPaneCambiarContrasena.setVisible(vistaActiva.equals("Cambiar Contrasena"));
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
        view.anchorPanePrincipal.setVisible(false);
    }

    /**
     * Muestra los datos del usuario actual en la vista.
     */
    public void verDatosUsuario() {
        cambiarVista("Ver Datos");
        if (usuarioActual != null) {
            view.camposInformacion.setVisible(true);
            view.paneEditarInformacion.setVisible(false);
            actualizarVistaDatosUsuario();
        }
    }

    /**
     * Muestra el panel para editar los datos del usuario.
     */
    public void mostrarPanelEditarDatos() {
        view.camposInformacion.setVisible(false);
        view.paneEditarInformacion.setVisible(true);
        view.btnModificarDatos.setVisible(false);
        view.btnGuardarCambios.setVisible(true);
    }

    /**
     * Guarda los cambios realizados a los datos del usuario actual.
     */
    public void editarDatosUsuario() {
        if (view.getTxtCorreoEditar().getText().isEmpty()
                || view.getTxtTelefonoEditar().getText().isEmpty()
                || view.getTxtNombreEditar().getText().isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane, "Por favor completa todos los campos.");
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

    /**
     * Muestra el panel para cambiar la contraseña.
     */
    public void mostrarCambiarContrasena() {
        cambiarVista("Cambiar Contrasena");
    }

    /**
     * Valida y guarda el cambio de contraseña y palabra clave.
     */
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

        if (mostrarErrorSi(!usuarioActual.getClaveAcceso().equals(claveActual), "La clave actual no es correcta.")) return;
        if (mostrarErrorSi(!nuevaClave.equals(confirmarClave), "Las claves nuevas no coinciden.")) return;
        if (mostrarErrorSi(!usuarioActual.getPalabraclave().equals(palabraClaveActual), "Las palabras clave no coinciden.")) return;

        usuarioActual.setClaveAcceso(nuevaClave);
        usuarioActual.setPalabraclave(palabraClaveNueva);
        servicioUsuario.actualizarUsuario(usuarioActual);
        Logger.getInstance().mostrarToast(view.rootPane, "Clave actualizada con éxito.");
        limpiarCampos();
    }

    /**
     * Actualiza los campos de la vista con los datos del usuario actual.
     */
    private void actualizarVistaDatosUsuario() {
        view.textNombreUsuario.setText(usuarioActual.getNombre());
        view.textCorreoUsuario.setText(usuarioActual.getCorreo());
        view.textDocumentoUsuario.setText(usuarioActual.getCedula());
        view.textTelefonoUsuario.setText(usuarioActual.getTelefono());
    }

    /**
     * Limpia todos los campos de texto y contraseñas en la vista.
     */
    private void limpiarCampos() {
        view.getPfClaveActual().clear();
        view.getPfNuevaClave().clear();
        view.getPfConfirmarClave().clear();
        view.getTxtPalabraClaveActual().clear();
        view.getTxtNuevaPalabraClave().clear();
        view.getTxtNombreEditar().clear();
        view.getTxtCorreoEditar().clear();
        view.getTxtTelefonoEditar().clear();
    }

    /**
     * Muestra un mensaje de error si la condición es verdadera.
     *
     * @param condicion Condición para mostrar el error.
     * @param mensaje Mensaje a mostrar si la condición es verdadera.
     * @return true si se mostró el error, false en caso contrario.
     */
    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }

    /**
     * Verifica si algún campo de texto está vacío o nulo.
     *
     * @param campos Campos a verificar.
     * @return true si algún campo está vacío o nulo, false si todos están llenos.
     */
    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
