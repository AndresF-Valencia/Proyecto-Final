package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControllerGestionUsuarios {

    private final ViewFuncionalidadesAdmin view;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCuenta servicioCuenta;
    private ObservableList<Usuario> listaUsuarios;

    public ControllerGestionUsuarios(ViewFuncionalidadesAdmin viewFuncionalidadesAdmin) {
        this.view = viewFuncionalidadesAdmin;
        this.servicioUsuario = ServicioUsuario.getInstancia();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.listaUsuarios = FXCollections.observableArrayList();
    }

    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneCuentas.setVisible(vistaActiva.equals("Cuentas"));
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneEstadisticas.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));
        view.paneActualizarUsuario.setVisible(vistaActiva.equals("Actualizar Usuario"));
    }

    public void mostrarGestionUsuarios() {
        cambiarVista("Gestion Usuarios");
        cargarUsuarios();
    }

    public void mostrarCrearUsuario() {
        cambiarVista("Crear Usuario");
    }
    public void actualizar(){
        cambiarVista("Actualizar Usuario");
    }

    public void regresar(){
        cambiarVista("Gestion Usuarios");
    }


    public void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(servicioUsuario.getUsuariosRegistrados());
        view.tablaUsuarios.setItems(listaUsuarios);
        view.tablaUsuarios.refresh();
    }

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

    private void limpiarCampos() {
        view.txtNombreNuevo1.clear();
        view.txtCorreoNuevo1.clear();
        view.txtTelefonoNuevo1.clear();
        view.txtCedulaNuevo1.clear();
        view.txtClaveNuevo1.clear();
        view.txtPalabraClaveNuevo1.clear();
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
