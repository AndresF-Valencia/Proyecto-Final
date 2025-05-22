package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ControllerCuentaAdmin {

    public Usuario usuarioActual;
    private final ViewFuncionalidadesAdmin view;
    private final ServicioCuenta servicioCuenta;
    private final ServicioUsuario servicioUsuario;

    public ControllerCuentaAdmin(ViewFuncionalidadesAdmin ViewFuncionalidadesAdmin) {
        this.view = ViewFuncionalidadesAdmin;
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
        cargarCuentasTabla();
        cargarComboCuentas();
    }

    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneStats.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));
        view.paneAgregarCuenta.setVisible(vistaActiva.equals("Agregar Cuenta"));
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.AnchorpaneEstadisticas.setVisible(vistaActiva.equals("Estadisticas"));
        view.anchorPaneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
    }

    // Carga todas las cuentas en la tabla
    public void cargarCuentasTabla() {
        List<Cuenta> cuentas = servicioCuenta.getCuentas();
        ObservableList<Cuenta> observableCuentas = FXCollections.observableArrayList(cuentas);
        view.tablaCuentas.setItems(observableCuentas);
        view.tablaCuentas.refresh(); // Fuerza el refresco
    }

    public void cargarComboCuentas() {
        ObservableList<String> tipos = FXCollections.observableArrayList("Ahorros", "Corriente");
        view.comboTipoCuenta.setItems(tipos);
    }

    public void mostrarCuenta() {
        view.paneCuentas.setVisible(true);
        cambiarVista("Gestion Cuentas");
    }

    public void crearCuenta() {
        cambiarVista("Agregar Cuenta");
        view.labelBienvenida.setVisible(false);
    }


    // Elimina una cuenta seleccionada de la tabla
    public void eliminarCuenta() {
        Cuenta cuenta = view.tablaCuentas.getSelectionModel().getSelectedItem();
        if (cuenta != null) {
            cuenta.getUsuario().getCuentas().remove(cuenta); // Eliminar del usuario
            servicioCuenta.getCuentas().remove(cuenta);      // Eliminar del servicio
            Logger.getInstance().mostrarToast(view.paneCuentas, "✅ Cuenta eliminada");
            cargarCuentasTabla();
            cargarComboCuentas();
        } else {
            Logger.getInstance().mostrarToast(view.paneCuentas, "❌ Seleccione una cuenta para eliminar");
        }
    }

    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Agrega una nueva cuenta desde los campos del paneAgregarCuenta
    public void registrarCuenta() {
        String bancoCuenta = view.txtBanco.getText();
        String numeroCuenta = view.txtNumeroCuenta.getText();
        String tipoCuenta = String.valueOf(view.comboTipoCuenta.getValue());

        if (camposVacios(bancoCuenta, numeroCuenta, tipoCuenta == null ? "" : tipoCuenta.toString())) {
            Logger.getInstance().mostrarToast(view.rootPane, "Debe llenar todos los campos");
            return;
        }

        if (servicioCuenta.existeNumeroCuenta(numeroCuenta)) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ El número de cuenta ya está en uso.");
            return;
        }

        servicioCuenta.registrarCuenta(numeroCuenta, tipoCuenta, bancoCuenta, usuarioActual);
        Logger.getInstance().mostrarToast(view.rootPane, "✅ Cuenta registrada con éxito");
        cargarCuentasTabla();
    }

    private void limpiarCamposAgregarCuenta() {
        view.txtBanco.clear();
        view.txtNumeroCuenta.clear();
        view.comboTipoCuenta.setValue(null);
    }

    public void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        System.out.println("Sesión cerrada, usuario actual: " + Sesion.getInstancia().getUsuarioActual());

        // Solo si quieres debug de los usuarios registrados
        for (Usuario u : servicioUsuario.getUsuariosRegistrados()) {
            System.out.println(u);
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