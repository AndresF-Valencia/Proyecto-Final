package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ControllerPasarDinero{
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;

public ControllerPasarDinero(ViewFuncionalidades view) {
    this.view = view;
    this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
}

public void iniciarVista() {
    ocultarElementoDePrincipalExceptoPasarDinero();
    view.panePasarDinero.setVisible(true);
    cargarCuentas();
}
    private void ocultarElementoDePrincipalExceptoPasarDinero(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.panePasarDinero);
        });
    }

    public void restaurarVistaPrincipal() {
        view.PaneTienda.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);
        view.PaneVerMas.setVisible(false);

        // Muestra solo el principal
        view.PanePrincipal.setVisible(true);
        view.PanePrincipal.toFront(); // Esto asegura que se muestre correctamente
    }


private void cargarCuentas() {
    List<Cuenta> cuentas = ServicioCuenta.obtenerCuentasDe(usuarioActual);
    view.comboSelecionCuenta2.getItems().setAll(cuentas);
}

public void pasarDinero() {
    Cuenta cuentaOrigen = view.comboSelecionCuenta2.getValue();
    String numeroCuentaDestino = view.numeroCuenta.getText();
    String textoCantidad = view.cantidadIngresar2.getText();

    if (cuentaOrigen == null || numeroCuentaDestino.isBlank() || textoCantidad.isBlank()) {
        Logger.getInstance().mostrarToast(view.rootPane, "❌ Complete todos los campos.");
        return;
    }

    double cantidad;
    try {
        cantidad = Double.parseDouble(textoCantidad);
    } catch (NumberFormatException e) {
        Logger.getInstance().mostrarToast(view.rootPane, "❌ Cantidad inválida.");
        return;
    }

    if (cantidad <= 0) {
        Logger.getInstance().mostrarToast(view.rootPane, "❌ La cantidad debe ser mayor que cero.");
        return;
    }

    if (cuentaOrigen.getNumeroCuenta().equals(numeroCuentaDestino)) {
        Logger.getInstance().mostrarToast(view.rootPane, "❌ No puede transferir a la misma cuenta.");
        return;
    }

    Cuenta cuentaDestino = ServicioCuenta.obtenerCuentaPorNumero(numeroCuentaDestino);
    if (cuentaDestino == null) {
        Logger.getInstance().mostrarToast(view.rootPane, "❌ Cuenta destino no encontrada.");
        return;
    }

    double saldoOrigen = Double.parseDouble(cuentaOrigen.getSaldo());
    if (saldoOrigen < cantidad) {
        Logger.getInstance().mostrarToast(view.rootPane, "⚠️ Saldo insuficiente.");
        return;
    }

    // Transferencia
    cuentaOrigen.agregarSaldo(-cantidad);
    cuentaDestino.agregarSaldo(cantidad);

    Transaccion transaccionSalida = new Transaccion(
            usuarioActual.getCedula(),                            // ID del usuario que transfiere
            UUID.randomUUID().toString(),                     // ID único para la transacción
            new Date(),                                       // Fecha actual
            "Transferencia salida",                           // Tipo
            cantidad,                                         // Monto
            "Transferencia enviada a cuenta " + numeroCuentaDestino // Descripción
    );

// Crear transacción de entrada para cuenta destino
    Transaccion transaccionEntrada = new Transaccion(
            cuentaDestino.getUsuario().getCedula(),               // ID del usuario destino
            UUID.randomUUID().toString(),                     // Otro ID único
            new Date(),
            "Transferencia entrada",
            cantidad,
            "Transferencia recibida de cuenta " + cuentaOrigen.getNumeroCuenta()
    );

// Guardar las transacciones
    ServicioTransaccion.agregarTransaccion(transaccionSalida);
    ServicioTransaccion.agregarTransaccion(transaccionEntrada);

    Logger.getInstance().mostrarToast(view.rootPane, "✅ Se transfirieron $" + cantidad + " a la cuenta " + numeroCuentaDestino);

    // Limpiar campos
    view.numeroCuenta.clear();
    view.cantidadIngresar2.clear();
}
}

