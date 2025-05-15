package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.decoradores.UsuarioConPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.GeneradorCodigo;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ControllerCuenta {
    public Usuario usuarioActual;
    public ServicioCuenta servicioCuenta;
    private final ViewFuncionalidades view;
    public ServicioUsuario servicioUsuario;
    public ServicioPresupuesto servicioPresupuesto;

    public ControllerCuenta(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
        this.servicioPresupuesto = ServicioPresupuesto.getInstancia();
    }

    public void cambiarVista(String vistaActiva){
        view.anchorPaneGestionarCuenta.setVisible(vistaActiva.equals("Gestionar Cuenta"));
        view.anchorPaneRegistroCuenta.setVisible(vistaActiva.equals("Registrar Cuenta"));
        view.anchorPanePresupuesto.setVisible(vistaActiva.equals("Presupuesto"));
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneCambiarContrasena.setVisible(false);
        view.anchorPanePrincipal.setVisible(false);
    }

    public void agregarCuenta(){
        cambiarVista("Registrar Cuenta");
    }

    public void gestionarCuentas(){
        cambiarVista("Gestionar Cuenta");
    }

    public void gestionarPresupuesto(){
        cambiarVista("Presupuesto");
        view.PaneCrearPresupuesto.setVisible(true);
    }

    public void verEstado(){
        view.PaneCrearPresupuesto.setVisible(false);
        view.PaneEstadoPresupuesto.setVisible(true);
        view.PaneEliminarPresupuesto.setVisible(false);
        view.PaneActualizarPresupuesto.setVisible(false);
    }

    public void actualizar(){
        view.PaneActualizarPresupuesto.setVisible(true);
        view.PaneCrearPresupuesto.setVisible(false);
        view.PaneEstadoPresupuesto.setVisible(false);
        view.PaneEliminarPresupuesto.setVisible(false);
    }

    public void eliminar(){
        view.PaneEliminarPresupuesto.setVisible(true);
        view.PaneActualizarPresupuesto.setVisible(false);
        view.PaneCrearPresupuesto.setVisible(false);
        view.PaneEstadoPresupuesto.setVisible(false);
    }

    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void limpiarCamposRegistroCuenta() {
        view.getCampoNumeroCuenta().clear();
        view.getCampoTitular().clear();
        view.getComboTipoCuenta().getSelectionModel().clearSelection();
    }

    public void registrarCuenta() {
        String numeroCuenta = view.getCampoNumeroCuenta().getText();
        String tipoCuenta = view.getComboTipoCuenta().getValue();
        String bancoCuenta = view.getCampoTitular().getText();

        if(camposVacios(numeroCuenta, tipoCuenta, bancoCuenta)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Debe llenar todos los campos");
            return;
        }

        if (servicioCuenta.existeNumeroCuenta(numeroCuenta)) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ El número de cuenta ya está en uso.");
            return;
        }

        servicioCuenta.registrarCuenta(numeroCuenta, tipoCuenta, bancoCuenta, usuarioActual);
        cargarCuentas();
        Logger.getInstance().mostrarToast(view.rootPane, "✅ Cuenta registrada con éxito");
        limpiarCamposRegistroCuenta();
    }

    public void consultarCuenta(){
        Cuenta cuentaSeleccionada = view.comboCuentas.getValue();

        if (cuentaSeleccionada != null) {
            view.textTitular.setText(cuentaSeleccionada.getBancoCuenta());
            view.textNumeroCuenta.setText(cuentaSeleccionada.getNumeroCuenta());
            view.textTipoCuenta.setText(cuentaSeleccionada.getTipoCuenta());
            view.textSaldo.setText(cuentaSeleccionada.getSaldo());

        } else {
            Logger.getInstance().mostrarToast(view.rootPane,"Debe seleccionar una cuenta");
        }
    }

    public void eliminarCuenta(){
        Cuenta cuentaSeleccionada = view.comboCuentas.getValue();
        if (cuentaSeleccionada != null) {
            usuarioActual.getCuentas().remove(cuentaSeleccionada);
            cargarCuentas();
        }else{
            Logger.getInstance().mostrarToast(view.rootPane,"Debe seleccionar una cuenta");
        }
    }

    public void Inicio(){
        view.anchorPanePrincipal.setVisible(true);
        view.PanePrincipal.setVisible(true);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneGestionarCuenta.setVisible(false);
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneCambiarContrasena.setVisible(false);
        view.anchorPanePresupuesto.setVisible(false);
        view.PaneCrearPresupuesto.setVisible(false);
        view.PaneEstadoPresupuesto.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneTienda.setVisible(false);
        view.PaneVerMas.setVisible(false);

    }

    public void cargarCuentas() {
        List<Cuenta> cuentasUsuario = ServicioCuenta.obtenerCuentasDe(usuarioActual);
        view.comboCuentas.getItems().setAll(cuentasUsuario);
        view.comboSelecionCuenta.getItems().setAll(cuentasUsuario);
        view.comboSelecionCuenta1.getItems().setAll(cuentasUsuario);
        view.comboSelecionCuenta2.getItems().setAll(cuentasUsuario);

    }

    public void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        for(Usuario u: servicioUsuario.getUsuariosRegistrados()){
            System.out.println(u.toString());
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

    public void inicializarCuentas(){
        String numCuenta1 = new GeneradorCodigo().generarCodigo();
        String numCuenta2 = new GeneradorCodigo().generarCodigo();
        String idCuenta = new GeneradorCodigo().generarCodigo();
        String idCuenta1 = new GeneradorCodigo().generarCodigo();
        if (usuarioActual.getCuentas().isEmpty()) {
            Cuenta cuenta1 = new Cuenta(idCuenta, numCuenta1, "Cuenta Ahorro", "Davivienda", usuarioActual);
            Cuenta cuenta2 = new Cuenta(idCuenta1, numCuenta2, "Cuenta Ahorro", "Bancolombia", usuarioActual);

            usuarioActual.getCuentas().add(cuenta1);
            usuarioActual.getCuentas().add(cuenta2);

            servicioCuenta.getCuentas().add(cuenta1);
            servicioCuenta.getCuentas().add(cuenta2);
        }
    }

    public void crearPresupuesto() {
        String idPresupuesto = new GeneradorCodigo().generarCodigo();
        String nombre = view.campoNombrePresupuesto.getText();
        String categoria = view.comboCategoriaPresupuesto.getValue();
        String monto = view.campoMonto.getText();
        double montoTotal = Double.parseDouble(monto);
        boolean esGeneral = categoria.equals("General");
        Presupuesto presupuesto = new Presupuesto(idPresupuesto, nombre, montoTotal, esGeneral);
        presupuesto.setCategoria(categoria);

        servicioPresupuesto.agregarPresupuesto(usuarioActual, presupuesto);

        Logger.getInstance().mostrarToast(view.rootPane, "✅Presupuesto creado correctamente");
    }

    public void consultarEstadoPresupuesto() {
        String nombrePresupuesto = view.campoNombrePresupuestoConsultar.getText();
        List<Presupuesto> presupuestos = servicioPresupuesto.obtenerPresupuestos(usuarioActual);

        if(presupuestos.isEmpty()){
            Logger.getInstance().mostrarToast(view.rootPane, "No se encontro el presupuesto");
            return;
        }
        for(Presupuesto p: presupuestos){
            if(p.getNombre().equals(nombrePresupuesto)){
                String categoria = p.getCategoria();
                double gastado = servicioPresupuesto.obtenerMontoGastado(usuarioActual, categoria);
                double total = servicioPresupuesto.obtenerMontoTotal(usuarioActual, categoria);
                double disponible = servicioPresupuesto.obtenerMontoDisponible(usuarioActual, categoria);
                view.txtMontoGastado.setText(String.valueOf(gastado));
                view.txtMontoDisponible.setText(String.valueOf(total));
                view.txtMontoDisponible.setText(String.valueOf(disponible));
            }
        }
    }

    public void eliminarPresupuesto() {
        String nombrePresupuesto = view.campoNombrePresupuestoConsultar.getText();
        List<Presupuesto> presupuestos = servicioPresupuesto.obtenerPresupuestos(usuarioActual);

        if(presupuestos.isEmpty()){
            Logger.getInstance().mostrarToast(view.rootPane, "No se encontro el presupuesto");
            return;
        }
        for (Presupuesto p: presupuestos){
            if(p.getNombre().equals(nombrePresupuesto)){
                servicioPresupuesto.eliminarPresupuesto(usuarioActual, p);
                Logger.getInstance().mostrarToast(view.rootPane, "✅ Presupuesto eliminado correctamente.");
            }
        }
    }

    public void actualizarPresupuesto() {
        String nombrePresupuesto = view.campoNombrePresupuestoConsultar.getText();
        String monto = view.campoMontoTotal.getText();
        List<Presupuesto> presupuestos = servicioPresupuesto.obtenerPresupuestos(usuarioActual);

        if (presupuestos.isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane, "No se encontro el presupuesto");
            return;
        }

        if (monto.isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane, "Llene todos los campos");
        }

        try {
            double nuevoMonto = Double.parseDouble(monto);
            for (Presupuesto p : presupuestos) {
                if (nuevoMonto < p.getMontoGastado()) {
                    Logger.getInstance().mostrarToast(view.rootPane, "❌ El nuevo monto no puede ser menor al ya gastado.");
                    return;
                }
                if (p.getNombre().equals(nombrePresupuesto)) {
                    servicioPresupuesto.actualizarMontoPresupuesto(usuarioActual, p, nuevoMonto);
                    Logger.getInstance().mostrarToast(view.rootPane, "✅ Presupuesto actualizado correctamente.");
                }

            }

        }catch (NumberFormatException e){
        Logger.getInstance().mostrarToast(view.rootPane, "Monto invalido");
        }
    }

}
