package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;

import java.util.List;

public class ControllerMeterDinero {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;


    public ControllerMeterDinero(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
    }

    public void iniciarVista() {
        ocultarElementosDePrincipalExceptoMeterDinero();
        view.PaneMeterDinero.setVisible(true);
        cargarCuentas();
    }

    private void ocultarElementosDePrincipalExceptoMeterDinero(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.PaneMeterDinero);
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
        view.comboSelecionCuenta.getItems().setAll(cuentasUsuario);
    }

    public void agregarDinero() {
        try {
            Cuenta cuentaSeleccionada = view.comboSelecionCuenta.getValue();

            if (cuentaSeleccionada == null) {
                Logger.getInstance().mostrarToast(view.rootPane, "❌ Complete todos los campos.");
                return;
            }

            String textoCantidad = view.cantidadIngresar.getText();
            double cantidad = Double.parseDouble(textoCantidad);

            if (cantidad <= 0) {
                Logger.getInstance().mostrarToast(view.rootPane, "❌ La cantidad debe ser mayor que 0.");
                return;
            }

            cuentaSeleccionada.agregarSaldo(cantidad);
            System.out.println("Saldo actualizado: " + cuentaSeleccionada.getSaldo());


            // Limpia el campo
            view.cantidadIngresar.clear();


        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida. Ingrese un número.");
        }
    }

}
