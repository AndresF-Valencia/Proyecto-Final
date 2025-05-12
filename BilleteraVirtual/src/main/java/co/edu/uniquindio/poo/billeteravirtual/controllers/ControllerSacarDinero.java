package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;

import java.util.List;
import java.util.Random;

public class ControllerSacarDinero {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;

    public ControllerSacarDinero(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
    }
    public void iniciarVista() {
        ocultarElementosDePrincipalExceptoSacarDinero();
        view.PaneSacarDinero.setVisible(true);
        cargarCuentas();
    }
    private void ocultarElementosDePrincipalExceptoSacarDinero(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.PaneSacarDinero);
        });
    }

    public void restaurarVistaPrincipal() {
        view.PaneTienda.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);

        // Muestra solo el principal
        view.PanePrincipal.setVisible(true);
        view.PanePrincipal.toFront(); // Esto asegura que se muestre correctamente
    }

    public void cargarCuentas() {
        List<Cuenta> cuentasUsuario = ServicioCuenta.obtenerCuentasDe(usuarioActual);
        System.out.println(cuentasUsuario.toString());
        view.comboSelecionCuenta1.getItems().setAll(cuentasUsuario);
    }

    public void retirarDinero() {
        try {
            Cuenta cuentaSeleccionada = view.comboSelecionCuenta1.getValue();

            if (cuentaSeleccionada == null) {
                Logger.getInstance().mostrarToast(view.rootPane, "❌ No se ha seleccionado ninguna cuenta.");
                return;
            }

            String textoCantidad = view.cantidadIngresar1.getText();

            double cantidad;
            try {
                cantidad = Double.parseDouble(textoCantidad);
            } catch (NumberFormatException e) {
                Logger.getInstance().mostrarToast(view.rootPane, "❌ Cantidad inválida. Ingrese un número.");
                return;
            }

            if (cantidad <= 0) {
                Logger.getInstance().mostrarToast(view.rootPane, "❌ La cantidad debe ser mayor a 0.");
                return;
            }

            double saldoCuenta = Double.parseDouble(cuentaSeleccionada.getSaldo());

            if (saldoCuenta < cantidad) {
                Logger.getInstance().mostrarToast(view.rootPane, "⚠️ Saldo insuficiente para realizar el retiro.");
                return;
            }

            cuentaSeleccionada.agregarSaldo(-cantidad);

            String codigoRetiro = generarCodigoRetiro();

            Logger.getInstance().mostrarToast(view.rootPane, "✅ Retiro exitoso. Código de retiro: " + codigoRetiro);
            view.cantidadIngresar1.clear();

        } catch (Exception e) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ Error al realizar el retiro: " + e.getMessage());
        }
    }




    private String generarCodigoRetiro() {
        Random random = new Random();
        int numero = 100000 + random.nextInt(900000);  // Genera un código de 6 dígitos
        return String.valueOf(numero);
    }


}
