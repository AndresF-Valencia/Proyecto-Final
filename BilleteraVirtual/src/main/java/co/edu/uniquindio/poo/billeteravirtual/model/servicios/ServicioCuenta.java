package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.GeneradorCodigo;

import java.util.ArrayList;
import java.util.List;

public class ServicioCuenta {
    private Usuario usuario;
    public List<Cuenta> cuentas = new ArrayList<>();

    public void registrarCuenta(String numeroCuenta, String tipoCuenta, String bancoCuenta, Usuario usuario) {
        if (existeNumeroCuenta(numeroCuenta)) {
            throw new IllegalArgumentException("❌ El número de cuenta ya está en uso.");
        }

        String codigo = new GeneradorCodigo().generarCodigo();
        Cuenta cuenta = new Cuenta(codigo, numeroCuenta, tipoCuenta, bancoCuenta, usuario);
        usuario.getCuentas().add(cuenta);
        cuentas.add(cuenta);
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

    public boolean existeNumeroCuenta(String numeroCuenta) {
        return cuentas.stream().anyMatch(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }

    public static Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        for (Usuario usuario : ServicioUsuario.getInstancia().getUsuariosRegistrados()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                    return cuenta;
                }
            }
        }
        return null;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public ServicioCuenta setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
        return this;
    }
}
