package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.adapter.EstadisticasReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporteCSV;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReportePDF;
import co.edu.uniquindio.poo.billeteravirtual.model.decoradores.UsuarioConPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.*;
import co.edu.uniquindio.poo.billeteravirtual.model.facade.TransaccionFacade;
import co.edu.uniquindio.poo.billeteravirtual.model.observer.Observador;
import co.edu.uniquindio.poo.billeteravirtual.model.observer.SujetoObservable;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.*;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.List;

public class ControllerTransacciones implements Observador {
    public Usuario usuarioActual;
    public ServicioTransaccion servicioTransaccion;
    private final ViewFuncionalidades view;
    private final TransaccionFacade transaccionFacade;
    private final ServicioCuenta servicioCuenta;
    private final ServicioPresupuesto servicioPresupuesto;

    public ControllerTransacciones(ViewFuncionalidades view) {
        this.view = view;
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioPresupuesto = ServicioPresupuesto.getInstancia();
        TransaccionFactory.setServicioCuenta(servicioCuenta);
        SujetoObservable.agregarObservador(this);

        this.transaccionFacade = new TransaccionFacade(servicioCuenta,servicioTransaccion,new TransaccionFactory());
    }

    public void cambiarVista(String vistaActiva) {
        view.PanePrincipal.setVisible(vistaActiva.equals("Principal"));
        view.panePasarDinero.setVisible(vistaActiva.equals("Pasar"));
        view.PaneMeterDinero.setVisible(vistaActiva.equals("Meter"));
        view.PaneSacarDinero.setVisible(vistaActiva.equals("Sacar"));
        view.PaneTienda.setVisible(vistaActiva.equals("Comprar"));
        view.PaneVerMas.setVisible(vistaActiva.equals("Ver transacciones"));
        view.anchorPaneGestionarCuenta.setVisible(false);
        view.anchorPaneRegistroCuenta.setVisible(false);
        view.anchorPaneVerDatosUsuario.setVisible(false);
        view.anchorPaneCambiarContrasena.setVisible(false);
        view.anchorPanePresupuesto.setVisible(false);
    }

    public void agregarDinero() {
        try {
            Cuenta cuentaDestino = view.comboSelecionCuenta.getValue();
            String textoCantidad = view.getCantidadIngresar().getText();
            double cantidad = Double.parseDouble(textoCantidad);
            String codigoGenerado = new GeneradorCodigo().generarCodigo();
            String cuentaOrigen = Transaccion.CUENTAEXTERNA;

            if (mostrarErrorSi(cuentaDestino == null,"❌ Complete todos los campos.")) {
                return;
            }

            if (mostrarErrorSi(cantidad <= 0,"❌ La cantidad debe ser mayor que 0.")) {
                return;
            }

            transaccionFacade.procesarTransaccion(codigoGenerado, LocalDate.now(),"DEPOSITO", cantidad, "Metio dinero a su cuenta", cuentaOrigen, cuentaDestino.getNumeroCuenta());
            SujetoObservable.notificarObservadores();
            SujetoObservable.notificarSaldo();
            Logger.getInstance().mostrarToast(view.rootPane, "Transaccion exitosa");

            // Limpia el campo
            view.cantidadIngresar.clear();


        } catch (NumberFormatException e) {
            Logger.getInstance().mostrarToast(view.rootPane, "El valor no es valido");
        }
    }

    public void transferirDinero() {
        Cuenta cuentaOrigen = view.comboSelecionCuenta2.getValue();
        String numeroCuentaDestino = view.numeroCuenta.getText();
        String textoCantidad = view.cantidadIngresar2.getText();
        String codigoGenerado = new GeneradorCodigo().generarCodigo();

        if (mostrarErrorSi(cuentaOrigen == null || numeroCuentaDestino.isEmpty() || textoCantidad.isEmpty(),"❌ Complete todos los campos.")) {
            return;
        }
        double cantidad = Double.parseDouble(textoCantidad);

        if (mostrarErrorSi(cantidad <= 0,"❌ La cantidad debe ser mayor que cero.")) {
            return;
        }

        if (mostrarErrorSi(cuentaOrigen.getNumeroCuenta().equals(numeroCuentaDestino),"❌ No puede transferir a la misma cuenta.")) {
            return;
        }

        Cuenta cuentaDestino = ServicioCuenta.obtenerCuentaPorNumero(numeroCuentaDestino);
        if (mostrarErrorSi(cuentaDestino == null,"❌ Cuenta destino no encontrada.")) {
            return;
        }

        double saldoOrigen = (cuentaOrigen.getSaldo1());
        if (mostrarErrorSi(saldoOrigen < cantidad,"⚠️ Saldo insuficiente.")) {
            return;
        }

        UsuarioConPresupuesto decorado = null;
        if (!servicioPresupuesto.obtenerPresupuestos(usuarioActual).isEmpty()) {
            decorado = servicioPresupuesto.crearUsuarioConPresupuesto(usuarioActual);
        }

        if (decorado != null) {
            if (mostrarErrorSi(!decorado.sePuedeRealizarTransaccion(cantidad, Presupuesto.PRESUPUESTO_GENERAL), "❌ Excede el presupuesto de compra.")) {
                return;
            }
            decorado.registrarGasto(cantidad, Presupuesto.PRESUPUESTO_GENERAL);
        }

        transaccionFacade.procesarTransaccion(codigoGenerado,LocalDate.now(),"TRANSFERENCIA", cantidad,"Paso plata a " + cuentaDestino.getUsuario().getNombre(),cuentaOrigen.getNumeroCuenta(),cuentaDestino.getNumeroCuenta());
        SujetoObservable.notificarObservadores();
        SujetoObservable.notificarSaldo();
        Logger.getInstance().mostrarToast(view.rootPane, "Transaccion exitosa");

        // Limpiar campos
        view.numeroCuenta.clear();
        view.cantidadIngresar2.clear();
    }

    public void retirarDinero() {
        try {
            Cuenta cuentaOrigen = view.comboSelecionCuenta1.getValue();
            String textoCantidad = view.cantidadIngresar1.getText();
            String codigoGenerado = new GeneradorCodigo().generarCodigo();
            double cantidad = Double.parseDouble(textoCantidad);
            String cuentaDestino = Transaccion.CUENTAEXTERNA;

            if (mostrarErrorSi(cuentaOrigen == null,"❌ No se ha seleccionado ninguna cuenta.")) {
                return;
            }

            double saldoCuenta = cuentaOrigen.getSaldo1();

            if (mostrarErrorSi(cantidad <= 0,"❌ La cantidad debe ser mayor a 0.")) {
                return;
            }

            if (mostrarErrorSi(saldoCuenta < cantidad,"⚠️ Saldo insuficiente para realizar el retiro.")){
                return;
            }


            transaccionFacade.procesarTransaccion(codigoGenerado,LocalDate.now(),"RETIRO", cantidad,"Retiro dinero", cuentaOrigen.getNumeroCuenta(), cuentaDestino);
            SujetoObservable.notificarObservadores();
            SujetoObservable.notificarSaldo();
            String codigoRetiro = new GeneradorCodigo().generarCodigo();

            Logger.getInstance().mostrarToast(view.rootPane, "✅ Retiro exitoso. Código de retiro: " + codigoRetiro);
            view.cantidadIngresar1.clear();

        } catch (Exception e) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ Error al realizar el retiro: " + e.getMessage());
        }
    }

    public void cargarTransacciones() {
        List<Transaccion> transacciones = servicioTransaccion.obtenerTransaccionesPorCliente(usuarioActual.getCedula());

        view.columnaFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        view.columnaTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        view.columnaMonto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMonto()).asObject());
        view.columnaDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));

        view.tablaTransacciones.getItems().setAll(transacciones);
    }

    public void iniciarVista() {
        ocultarElementoDePrincipalExceptoVerMas();
        view.PaneVerMas.setVisible(true);
        cargarTransacciones();
    }

    private void ocultarElementoDePrincipalExceptoVerMas(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.PaneVerMas);
        });
    }


    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }

    public void meterDinero(){
        cambiarVista("Meter");
    }

    public void pasarDinero(){
        cambiarVista("Pasar");
    }

    public void retirar(){
        cambiarVista("Sacar");
    }

    @Override
    public void actualizar() {
        cargarTransacciones();
    }

    @Override
    public void actualizarSaldo() {
        double saldoTotal = servicioCuenta.obtenerSaldo(usuarioActual);
        view.txtSaldoPrincipal.setText("$" + String.format("%.2f", saldoTotal));
    }

    public String actualizarSaldoInicio(){
        double saldo = servicioCuenta.obtenerSaldo(usuarioActual);
        String saldoTotal = String.format("%.2f", saldo);
        return saldoTotal;
    }

    public void generarReporteUsuario(){
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/user.pdf";
        ExportadorReportePDF exportador = new ExportadorReportePDF();
        ReporteCliente reporte = new ReporteCliente(exportador, usuarioActual.getCedula());

        double ingresos = reporte.getTotalIngresos();
        double gastos = reporte.getTotalGastos();

        exportador.setTotalesCliente(ingresos, gastos);

        reporte.generar(rutaArchivo);
    }

    public void generarReporteAdmin(EstadisticasReporte estadisticasReporte){
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/admin.pdf";
        ExportadorReportePDF exportador = new ExportadorReportePDF();

        exportador.setEstadisticas(estadisticasReporte);

        ReporteAdmin reporte = new ReporteAdmin(exportador);
        reporte.generar(rutaArchivo);
    }

    public void generarReporteAdminCSV(EstadisticasReporte estadisticasReporte) {
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/admin.csv"; // O la ruta que prefieras
        ExportadorReporteCSV exportador = new ExportadorReporteCSV();

        exportador.setEstadisticas(estadisticasReporte);

        ReporteAdmin reporte = new ReporteAdmin(exportador);
        reporte.generar(rutaArchivo);
    }

    public void generarReporteUsuarioCSV() {
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/user.csv";
        ExportadorReporteCSV exportador = new ExportadorReporteCSV();

        ReporteCliente reporte = new ReporteCliente(exportador, usuarioActual.getCedula());

        double ingresos = reporte.getTotalIngresos();
        double gastos = reporte.getTotalGastos();
        exportador.setTotalesCliente(ingresos, gastos);

        reporte.generar(rutaArchivo);
    }
}
