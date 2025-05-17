package co.edu.uniquindio.poo.billeteravirtual.controllers;


import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;

public class ControllerFuncionalidadesAdmin {

    private final ViewFuncionalidadesAdmin view;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCuenta servicioCuenta;
    private final ServicioTransaccion servicioTransaccion;

    public ControllerFuncionalidadesAdmin(ViewFuncionalidadesAdmin view) {
        this.view = view;
        this.servicioUsuario = ServicioUsuario.getInstancia();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.servicioTransaccion = ServicioTransaccion.getInstancia();

        inicializarEventos();
    }

    private void inicializarEventos() {
        view.btnGestionUsuarios.setOnAction(e -> mostrarUsuarios());
        view.btnGestionCuentas.setOnAction(e -> mostrarCuentas());
        view.btnEstadisticas.setOnAction(e -> mostrarEstadisticas());
        view.btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }

    private void mostrarUsuarios() {
        ocultarTodosLosPanes();
        view.paneUsuarios.setVisible(true);
        // Aquí cargarías los usuarios en la tabla
    }

    private void mostrarCuentas() {
        ocultarTodosLosPanes();
        view.paneCuentas.setVisible(true);
        // Cargar cuentas en la tabla
    }

    private void mostrarEstadisticas() {
        ocultarTodosLosPanes();
        view.paneStats.setVisible(true);
        // Llenar gráficos y estadística
    }

    private void cerrarSesion() {
        Logger.getInstance().mostrarToast(view.rootPane, "Sesión cerrada");
        // Aquí cambia la escena a la pantalla de login
    }

    private void ocultarTodosLosPanes() {
        view.paneUsuarios.setVisible(false);
        view.paneCuentas.setVisible(false);
        view.paneStats.setVisible(false);
    }
}

