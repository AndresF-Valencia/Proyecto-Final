package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioUsuario;
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

    private void ocultarElementosDePrincipalExceptoMeterDinero() {
        view.anchorPanePrincipal.getChildren().forEach(nodo -> {
            nodo.setVisible(nodo == view.PaneMeterDinero);
        });
    }

    public void restaurarVistaPrincipal() {
        view.anchorPanePrincipal.getChildren().forEach(nodo -> nodo.setVisible(true));
        view.PaneMeterDinero.setVisible(false); // Evitar mostrar doble
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
                System.out.println("No se ha seleccionado ninguna cuenta.");
                return;
            }

            String textoCantidad = view.cantidadIngresar.getText();
            double cantidad = Double.parseDouble(textoCantidad);

            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor a 0.");
                return;
            }

            cuentaSeleccionada.agregarSaldo(cantidad); // Asegúrate que exista este método
            System.out.println("Saldo actualizado: " + cuentaSeleccionada.getSaldo());

            // Limpia el campo
            view.cantidadIngresar.clear();

        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida. Ingrese un número.");
        }
    }

}
