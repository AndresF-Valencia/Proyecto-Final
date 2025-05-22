package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controlador encargado de gestionar la interfaz y operaciones relacionadas
 * con la administración de usuarios dentro de la aplicación.
 */
public class ControllerGestionUsuarios {

    private final ViewFuncionalidadesAdmin view;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCuenta servicioCuenta;
    private ObservableList<Usuario> listaUsuarios;

    /**
     * Constructor que inicializa el controlador con la vista administrativa,
     * y obtiene las instancias de los servicios de usuario y cuenta.
     *
     * @param viewFuncionalidadesAdmin La vista principal de funcionalidades administrativas.
     */
    public ControllerGestionUsuarios(ViewFuncionalidadesAdmin viewFuncionalidadesAdmin) {
        this.view = viewFuncionalidadesAdmin;
        this.servicioUsuario = ServicioUsuario.getInstancia();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.listaUsuarios = FXCollections.observableArrayList();
    }

    /**
     * Cambia la vista activa en la interfaz según el nombre del panel dado.
     *
     * @param vistaActiva Nombre del panel a mostrar.
     */
    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneCuentas.setVisible(vistaActiva.equals("Cuentas"));
        view.paneStats.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.AnchorpaneEstadisticas.setVisible(vistaActiva.equals("Estadisticas"));
        view.anchorPaneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
    }

    /**
     * Muestra el panel para gestionar usuarios y carga la lista de usuarios.
     */
    public void mostrarGestionUsuarios() {
        view.AnchorpaneUsuarios.setVisible(true);
        cambiarVista("Gestion Usuarios");
        cargarUsuarios();
    }

    /**
     * Muestra el panel para crear un nuevo usuario.
     */
    public void mostrarCrearUsuario() {
        cambiarVista("Crear Usuario");
    }

    /**
     * Muestra el panel para actualizar un usuario.
     */
    public void actualizar() {
        cambiarVista("Actualizar Usuario");
    }

    /**
     * Regresa a la vista de gestión de usuarios desde otras vistas relacionadas.
     */
    public void regresar() {
        cambiarVista("Gestion Usuarios");
    }

    /**
     * Regresa a la vista de bienvenida.
     */
    public void regresoTotal() {
        cambiarVista("Bienvenida");
    }

    /**
     * Carga la lista de usuarios registrados y la muestra en la tabla.
     */
    public void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(servicioUsuario.getUsuariosRegistrados());
        view.tablaUsuarios.setItems(listaUsuarios);
        view.tablaUsuarios.refresh();
    }

    /**
     * Crea un nuevo usuario a partir de los datos ingresados en el formulario,
     * validando campos obligatorios y existencia previa del usuario.
     */
    public void crearUsuario() {
        String nombre = view.txtNombreNuevo1.getText();
        String correo = view.txtCorreoNuevo1.getText();
        String telefono = view.txtTelefonoNuevo1.getText();
        String cedula = view.txtCedulaNuevo1.getText();
        String clave = view.txtClaveNuevo1.getText();
        String palabraClave = view.txtPalabraClaveNuevo1.getText();

        if (camposVacios(nombre, correo, telefono, cedula, clave, palabraClave)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Todos los campos son obligatorios.");
            return;
        }

        if (servicioUsuario.obtenerUsuario(cedula) != null) {
            Logger.getInstance().mostrarToast(view.rootPane, "Ya existe un usuario con esa cédula.");
            return;
        }

        servicioUsuario.registrarUsuario(nombre, correo, telefono, cedula, palabraClave, clave);
        Logger.getInstance().mostrarToast(view.rootPane, "Usuario creado exitosamente.");
        limpiarCampos();
        cargarUsuarios();
    }

    /**
     * Actualiza los datos del usuario seleccionado en la tabla
     * con la información ingresada en el formulario de actualización.
     */
    public void actualizarUsuario() {
        Usuario seleccionado = view.tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Logger.getInstance().mostrarToast(view.rootPane, "Seleccione un usuario para actualizar.");
            return;
        }

        String nombre = view.txtNombreNuevo.getText();
        String correo = view.txtCorreoNuevo.getText();
        String telefono = view.txtTelefonoNuevo.getText();

        if (camposVacios(nombre, correo, telefono)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Debe completar todos los campos.");
            return;
        }

        seleccionado.setNombre(nombre);
        seleccionado.setCorreo(correo);
        seleccionado.setTelefono(telefono);

        servicioUsuario.actualizarUsuario(seleccionado);
        Logger.getInstance().mostrarToast(view.rootPane, "Usuario actualizado correctamente.");
        limpiarCampos();
        cargarUsuarios();
    }

    /**
     * Elimina el usuario seleccionado en la tabla de usuarios.
     */
    public void eliminarUsuario() {
        Usuario seleccionado = view.tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Logger.getInstance().mostrarToast(view.rootPane, "Seleccione un usuario para eliminar.");
            return;
        }

        servicioUsuario.eliminarUsuario(seleccionado.getCedula());
        Logger.getInstance().mostrarToast(view.rootPane, "Usuario eliminado correctamente.");
        cargarUsuarios();
    }

    /**
     * Limpia los campos de texto del formulario de creación de usuario.
     */
    private void limpiarCampos() {
        view.txtNombreNuevo1.clear();
        view.txtCorreoNuevo1.clear();
        view.txtTelefonoNuevo1.clear();
        view.txtCedulaNuevo1.clear();
        view.txtClaveNuevo1.clear();
        view.txtPalabraClaveNuevo1.clear();
    }

    /**
     * Verifica si alguno de los campos proporcionados está vacío o nulo.
     *
     * @param campos Array de cadenas a verificar.
     * @return true si alguno está vacío o nulo, false en caso contrario.
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

