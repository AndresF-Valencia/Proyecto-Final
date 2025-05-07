package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;

import javax.swing.text.View;
import java.util.List;

public class ControllerFuncionalidades {
    public Usuario usuarioActual = Sesion.getUsuarioActual();
    public ServicioCuenta servicioCuenta;
    private ViewFuncionalidades view;

    public ControllerFuncionalidades(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
    }

    public void cambiarVista(String vistaActiva){
        view.anchorPaneGestionarCuenta.setVisible(vistaActiva.equals("Gestinoar Cuenta"));
        view.anchorPaneRegistroCuenta.setVisible(vistaActiva.equals("Registrar Cuenta"));
    }

    public void agregarCuenta(){
        cambiarVista("Registrar Cuenta");
    }

    public void registrarCuenta() {
        String numeroCuenta = view.getCampoNumeroCuenta().getText();
        String tipoCuenta = view.getComboTipoCuenta().getValue();
        String bancoCuenta = view.getCampoTitular().getText();

        if(numeroCuenta.isEmpty() || tipoCuenta.isEmpty() || bancoCuenta.isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane,"Debe llenar todos los campos");
        }
        servicioCuenta.registrarCuenta(numeroCuenta, tipoCuenta, bancoCuenta, usuarioActual);
        Logger.getInstance().mostrarToast(view.rootPane,"Cuenta Registrada con exito");
    }

    public void cargarCuentas(Usuario usuarioActual) {
        List<Cuenta> cuentasUsuario = servicioCuenta.obtenerCuentasDe(usuarioActual);
        view.comboCuentas.getItems().setAll(cuentasUsuario);
    }
}
