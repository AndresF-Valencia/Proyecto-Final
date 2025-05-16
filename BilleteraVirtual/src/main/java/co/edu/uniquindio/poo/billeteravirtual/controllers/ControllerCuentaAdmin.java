package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;

import java.util.List;

public class ControllerCuentaAdmin {

    public Usuario usuarioActual;
    private final ViewFuncionalidadesAdmin view;
    private final ServicioCuenta servicioCuenta;

    public ControllerCuentaAdmin(ViewFuncionalidadesAdmin ViewFuncionalidadesAdmin) {
        this.view = ViewFuncionalidadesAdmin;
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        cargarCuentasTabla();
        cargarComboCuentas();
    }

    public void cambiarVistaCuenta(String vistaActiva) {
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
        view.paneAgregarCuenta.setVisible(vistaActiva.equals("Agregar Cuenta"));
        view.paneActualizarCuenta.setVisible(vistaActiva.equals("Actualizar Cuenta"));

    }
    // Carga todas las cuentas en la tabla
    public void cargarCuentasTabla() {
        List<Cuenta> cuentas = servicioCuenta.getCuentas();
        view.tablaCuentas.getItems().setAll(cuentas);
    }


    public void mostrarCuenta() {
        cambiarVistaCuenta("Gestion Cuentas");
    }

    public void crearCuenta() {
        cambiarVistaCuenta("Agregar Cuenta");
    }

    public void actualizarCuenta() {
        cambiarVistaCuenta("Actualizar Cuenta");
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
    }


    // Actualiza el tipo de una cuenta seleccionada
    public void actualizarTipoCuenta() {
        Cuenta cuenta = view.comboSeleccionCuenta.getValue();
        String nuevoTipo = String.valueOf(view.comboActualizarTipoCuenta.getValue());

        if (cuenta == null || nuevoTipo == null) {
            Logger.getInstance().mostrarToast(view.paneCuentas, "❌ Seleccione una cuenta y un tipo");
            return;
        }

        cuenta.setTipoCuenta(nuevoTipo);
        Logger.getInstance().mostrarToast(view.paneCuentas, "✅ Tipo de cuenta actualizado");
        cargarCuentasTabla();
    }

    // Carga las cuentas al ComboBox del paneActualizarCuenta
    public void cargarComboCuentas() {
        List<Cuenta> cuentas = servicioCuenta.getCuentas();
        view.comboSeleccionCuenta.getItems().setAll(cuentas);
    }

    private void limpiarCamposAgregarCuenta() {
        view.txtBanco.clear();
        view.txtNumeroCuenta.clear();
        view.comboTipoCuenta.setValue(null);
    }
}