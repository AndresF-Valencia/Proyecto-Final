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

/**
 * Controlador principal para gestionar las transacciones financieras del usuario en la aplicación.
 * Implementa el patrón Observador para actualizar la vista cuando hay cambios en las transacciones o saldo.
 * Gestiona depósitos, retiros, transferencias y generación de reportes, además de controlar las vistas relacionadas.
 */
public class ControllerTransacciones implements Observador {
    public Usuario usuarioActual;
    public ServicioTransaccion servicioTransaccion;
    private final ViewFuncionalidades view;
    private final TransaccionFacade transaccionFacade;
    private final ServicioCuenta servicioCuenta;
    private final ServicioPresupuesto servicioPresupuesto;

    /**
     * Constructor que inicializa el controlador con la vista proporcionada,
     * configura servicios, fachadas y registra el controlador como observador.
     *
     * @param view La vista principal de funcionalidades del usuario.
     */
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

    /**
     * Cambia la vista activa del panel principal según la opción seleccionada.
     * Solo el panel correspondiente a la vista activa será visible.
     *
     * @param vistaActiva Nombre del panel a mostrar ("Principal", "Pasar", "Meter", "Sacar", "Comprar", "Ver transacciones").
     */
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

    /**
     * Realiza un depósito (agregar dinero) a la cuenta seleccionada desde la interfaz.
     * Valida los campos y muestra mensajes de error o éxito según corresponda.
     */
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

    /**
     * Realiza una transferencia de dinero desde una cuenta origen a una cuenta destino.
     * Valida campos, saldo, presupuesto y condiciones lógicas para la transferencia.
     */
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

    /**
     * Realiza el retiro de dinero desde la cuenta seleccionada.
     * Valida que la cuenta tenga saldo suficiente y que la cantidad ingresada sea válida.
     */
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

    /**
     * Carga todas las transacciones asociadas al usuario actual y las muestra en la tabla de la interfaz.
     */
    public void cargarTransacciones() {
        List<Transaccion> transacciones = servicioTransaccion.obtenerTransaccionesPorCliente(usuarioActual.getCedula());

        view.columnaFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        view.columnaTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        view.columnaMonto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMonto()).asObject());
        view.columnaDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));

        view.tablaTransacciones.getItems().setAll(transacciones);
    }

    /**
     * Inicializa la vista principal mostrando únicamente el panel de "Ver transacciones".
     */
    public void iniciarVista() {
        ocultarElementoDePrincipalExceptoVerMas();
        view.PaneVerMas.setVisible(true);
        cargarTransacciones();
    }

    /**
     * Oculta todos los elementos dentro del panel principal excepto el panel "Ver transacciones".
     */
    private void ocultarElementoDePrincipalExceptoVerMas(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.PaneVerMas);
        });
    }

    /**
     * Muestra un mensaje de error mediante un toast si se cumple la condición dada.
     *
     * @param condicion Condición para mostrar el error.
     * @param mensaje Mensaje que se mostrará si la condición es verdadera.
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
     * Cambia la vista para mostrar el panel de ingresar dinero (depósitos).
     */
    public void meterDinero(){
        cambiarVista("Meter");
    }

    /**
     * Cambia la vista para mostrar el panel de transferencia de dinero entre cuentas.
     */
    public void pasarDinero(){
        cambiarVista("Pasar");
    }

    /**
     * Cambia la vista para mostrar el panel de retiro de dinero.
     */
    public void retirar(){
        cambiarVista("Sacar");
    }

    /**
     * Método llamado al notificar el cambio en las transacciones.
     * Recarga la tabla de transacciones en la vista.
     */
    @Override
    public void actualizar() {
        cargarTransacciones();
    }

    /**
     * Método llamado para actualizar el saldo mostrado en la interfaz.
     */
    @Override
    public void actualizarSaldo() {
        double saldo = servicioCuenta.obtenerSaldo(usuarioActual);
        String saldoTotal = String.valueOf(saldo);
        view.txtSaldoPrincipal.setText("$" + saldoTotal);
    }

    /**
     * Obtiene el saldo actual del usuario como cadena para mostrarlo en la interfaz.
     *
     * @return El saldo total en formato cadena.
     */
    public String actualizarSaldoInicio(){
        double saldo = servicioCuenta.obtenerSaldo(usuarioActual);
        String saldoTotal = String.valueOf(saldo);
        return saldoTotal;
    }

    /**
     * Genera un reporte en formato PDF con las estadísticas de ingresos y gastos del usuario.
     * El archivo se guarda en una ruta fija.
     */
    public void generarReporteUsuario(){
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/user.pdf";
        ExportadorReportePDF exportador = new ExportadorReportePDF();
        ReporteCliente reporte = new ReporteCliente(exportador, usuarioActual.getCedula());

        double ingresos = reporte.getTotalIngresos();
        double gastos = reporte.getTotalGastos();

        exportador.setTotalesCliente(ingresos, gastos);

        reporte.generar(rutaArchivo);
    }

    /**
     * Genera un reporte en formato CSV con las estadísticas de ingresos y gastos del usuario.
     * El archivo se guarda en una ruta fija.
     */
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
