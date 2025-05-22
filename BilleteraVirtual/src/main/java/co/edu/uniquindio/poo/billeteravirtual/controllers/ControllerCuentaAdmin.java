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

/**
 * Controlador para la gestión de cuentas desde la interfaz administrativa.
 * Proporciona funcionalidades para visualizar, crear, eliminar y registrar cuentas bancarias,
 * así como controlar la navegación entre paneles de la vista.
 */
public class ControllerCuentaAdmin {

    /** Usuario actualmente autenticado en la sesión */
    public Usuario usuarioActual;

    /** Vista gráfica asociada a las funcionalidades administrativas */
    private final ViewFuncionalidadesAdmin view;

    /** Servicio para gestión de cuentas */
    private final ServicioCuenta servicioCuenta;

    /** Servicio para gestión de usuarios */
    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor que inicializa el controlador con su vista y servicios.
     * Carga inicialmente las cuentas y tipos de cuenta disponibles.
     *
     * @param ViewFuncionalidadesAdmin vista gráfica del módulo administrativo.
     */
    public ControllerCuentaAdmin(ViewFuncionalidadesAdmin ViewFuncionalidadesAdmin) {
        this.view = ViewFuncionalidadesAdmin;
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
        cargarCuentasTabla();
        cargarComboCuentas();
    }

    /**
     * Cambia el panel visible en la interfaz según la vista activa indicada.
     *
     * @param vistaActiva nombre del panel que debe mostrarse.
     */
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

    /**
     * Carga todas las cuentas existentes y las muestra en la tabla correspondiente.
     */
    public void cargarCuentasTabla() {
        List<Cuenta> cuentas = servicioCuenta.getCuentas();
        ObservableList<Cuenta> observableCuentas = FXCollections.observableArrayList(cuentas);
        view.tablaCuentas.setItems(observableCuentas);
        view.tablaCuentas.refresh(); // Refresca para asegurar actualización visual
    }

    /**
     * Carga los tipos de cuenta disponibles en el combo box para selección.
     */
    public void cargarComboCuentas() {
        ObservableList<String> tipos = FXCollections.observableArrayList("Ahorros", "Corriente");
        view.comboTipoCuenta.setItems(tipos);
    }

    /**
     * Muestra el panel de gestión de cuentas.
     */
    public void mostrarCuenta() {
        view.paneCuentas.setVisible(true);
        cambiarVista("Gestion Cuentas");
    }

    /**
     * Muestra el panel para agregar una nueva cuenta.
     */
    public void crearCuenta() {
        cambiarVista("Agregar Cuenta");
        view.labelBienvenida.setVisible(false);
    }

    /**
     * Elimina la cuenta seleccionada en la tabla tanto del usuario como del servicio.
     * Muestra mensajes de confirmación o error según corresponda.
     */
    public void eliminarCuenta() {
        Cuenta cuenta = view.tablaCuentas.getSelectionModel().getSelectedItem();
        if (cuenta != null) {
            cuenta.getUsuario().getCuentas().remove(cuenta);
            servicioCuenta.getCuentas().remove(cuenta);
            Logger.getInstance().mostrarToast(view.paneCuentas, "✅ Cuenta eliminada");
            cargarCuentasTabla();
            cargarComboCuentas();
        } else {
            Logger.getInstance().mostrarToast(view.paneCuentas, "❌ Seleccione una cuenta para eliminar");
        }
    }

    /**
     * Verifica si alguno de los campos provistos está vacío o nulo.
     *
     * @param campos array de cadenas a validar.
     * @return true si algún campo está vacío o nulo, false en caso contrario.
     */
    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra una nueva cuenta bancaria para el usuario actual,
     * validando que todos los campos estén completos y que el número
     * de cuenta no esté duplicado.
     * Actualiza la tabla de cuentas al finalizar.
     */
    public void registrarCuenta() {
        String bancoCuenta = view.txtBanco.getText();
        String numeroCuenta = view.txtNumeroCuenta.getText();
        String tipoCuenta = String.valueOf(view.comboTipoCuenta.getValue());

        if (camposVacios(bancoCuenta, numeroCuenta, tipoCuenta == null ? "" : tipoCuenta)) {
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

    /**
     * Limpia los campos del formulario de agregar cuenta para preparar una nueva entrada.
     */
    private void limpiarCamposAgregarCuenta() {
        view.txtBanco.clear();
        view.txtNumeroCuenta.clear();
        view.comboTipoCuenta.setValue(null);
    }

    /**
     * Cierra la sesión actual del usuario y redirige a la pantalla de login.
     * Además, imprime en consola los usuarios registrados para depuración.
     * Maneja excepciones en la carga de la interfaz.
     */
    public void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        System.out.println("Sesión cerrada, usuario actual: " + Sesion.getInstancia().getUsuarioActual());

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
