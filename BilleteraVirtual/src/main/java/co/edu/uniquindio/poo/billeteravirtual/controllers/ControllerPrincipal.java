package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;

import java.util.List;

public class ControllerPrincipal {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;

    public ControllerPrincipal(ViewFuncionalidades viewFuncionalidades) {
        this.view = viewFuncionalidades;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
    }

    public void saldoPrincipal() {
        List<Cuenta> cuentas = usuarioActual.getCuentas();
        double saldo = 0;
        view.getTxtSaldoPrincipal().setText("$"+saldo);
        for (Cuenta cuenta : cuentas) {
            saldo += cuenta.getSaldo1();
        }
    }
}
