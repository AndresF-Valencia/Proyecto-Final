package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.TransaccionFactory;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.facade.TransaccionFacade;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.GeneradorCodigo;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ControllerTransacciones {
    public Usuario usuarioActual;
    public ServicioTransaccion servicioTransaccion;
    private final ViewFuncionalidades view;
    private final TransaccionFacade transaccionFacade;
    private final ServicioCuenta servicioCuenta;

    public ControllerTransacciones(ViewFuncionalidades view) {
        this.view = view;
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        TransaccionFactory.setServicioCuenta(servicioCuenta);

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
    }

    public void agregarDinero() {
        try {
            Cuenta cuentaSeleccionada = view.comboSelecionCuenta.getValue();
            String textoCantidad = view.getCantidadIngresar().getText();
            double cantidad = Double.parseDouble(textoCantidad);
            String codigoGenerado = new GeneradorCodigo().generarCodigo();

            if (mostrarErrorSi(cuentaSeleccionada == null,"❌ Complete todos los campos.")) {
                return;
            }

            if (mostrarErrorSi(cantidad <= 0,"❌ La cantidad debe ser mayor que 0.")) {
                return;
            }

            transaccionFacade.procesarTransaccion(codigoGenerado, LocalDate.now(),"DEPOSITO", cantidad, "Metio dinero a su cuenta", Transaccion.CUENTAEXTERNA ,cuentaSeleccionada.getIdCuenta());
            cargarTransacciones();
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

        transaccionFacade.procesarTransaccion(codigoGenerado,LocalDate.now(),"TRANSFERENCIA", cantidad,"Paso plata a " + cuentaDestino.getUsuario().getNombre(),cuentaOrigen.getIdCuenta(),cuentaDestino.getIdCuenta());
        cargarTransacciones();
        Logger.getInstance().mostrarToast(view.rootPane, "Transaccion exitosa");

        // Limpiar campos
        view.numeroCuenta.clear();
        view.cantidadIngresar2.clear();
    }

    public void retirarDinero() {
        try {
            Cuenta cuentaSeleccionada = view.comboSelecionCuenta1.getValue();
            String textoCantidad = view.cantidadIngresar1.getText();
            String codigoGenerado = new GeneradorCodigo().generarCodigo();
            double cantidad = Double.parseDouble(textoCantidad);

            if (mostrarErrorSi(cuentaSeleccionada == null,"❌ No se ha seleccionado ninguna cuenta.")) {
                return;
            }

            double saldoCuenta = cuentaSeleccionada.getSaldo1();

            if (mostrarErrorSi(cantidad <= 0,"❌ La cantidad debe ser mayor a 0.")) {
                return;
            }

            if (mostrarErrorSi(saldoCuenta < cantidad,"⚠️ Saldo insuficiente para realizar el retiro.")){
                return;
            }

            transaccionFacade.procesarTransaccion(codigoGenerado,LocalDate.now(),"RETIRO", cantidad,"Retiro dinero",cuentaSeleccionada.getIdCuenta(), Transaccion.CUENTAEXTERNA);
            cargarTransacciones();
            String codigoRetiro = new GeneradorCodigo().generarCodigo();

            Logger.getInstance().mostrarToast(view.rootPane, "✅ Retiro exitoso. Código de retiro: " + codigoRetiro);
            view.cantidadIngresar1.clear();

        } catch (Exception e) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ Error al realizar el retiro: " + e.getMessage());
        }
    }

    public void cargarTransacciones() {
        List<Transaccion> transacciones = ServicioTransaccion.obtenerTransaccionesPorCliente(usuarioActual.getCedula());
        System.out.println("Transacciones encontradas: " + transacciones.size());


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
}
