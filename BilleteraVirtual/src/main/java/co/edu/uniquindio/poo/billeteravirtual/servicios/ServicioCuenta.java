package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.GeneradorCodigo;

import java.util.ArrayList;
import java.util.List;

public class ServicioCuenta {
    private Usuario usuario;
    public List<Cuenta> cuentas = new ArrayList<>();

    public void registrarCuenta(String numeroCuenta, String tipoCuenta, String bancoCuenta, Usuario usuario) {
        String codigo= new GeneradorCodigo().generarCodigo();
        Cuenta cuenta = new Cuenta(codigo, numeroCuenta, tipoCuenta, bancoCuenta, usuario);
        usuario.getCuentas().add(cuenta);
    }

    public static List<Cuenta> obtenerCuentasDe(Usuario usuario) {
        List<Cuenta> cuentasDelUsuario = new ArrayList<>();
        for (Cuenta cuenta : usuario.getCuentas()) {
            if (cuenta.getUsuario().equals(usuario)) {
                cuentasDelUsuario.add(cuenta);
            }
        }
        return cuentasDelUsuario;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public ServicioCuenta setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
        return this;
    }
}
