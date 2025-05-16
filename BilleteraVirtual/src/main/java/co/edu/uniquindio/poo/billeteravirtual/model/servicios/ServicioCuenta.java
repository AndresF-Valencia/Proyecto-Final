package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.GeneradorCodigo;

import java.util.ArrayList;
import java.util.List;

public class ServicioCuenta {
    private static ServicioCuenta instancia;
    public List<Cuenta> cuentas = new ArrayList<>();

    private ServicioCuenta() {
    }

    public static ServicioCuenta getInstancia() {
        if (instancia == null) {
            instancia = new ServicioCuenta();
        }
        return instancia;
    }

    public void registrarCuenta(String numeroCuenta, String tipoCuenta, String bancoCuenta, Usuario usuario) {
        if (existeNumeroCuenta(numeroCuenta)) {
            throw new IllegalArgumentException("❌ El número de cuenta ya está en uso.");
        }

        String codigo = new GeneradorCodigo().generarCodigo();
        Cuenta cuenta = new Cuenta(codigo, numeroCuenta, tipoCuenta, bancoCuenta, usuario);
        usuario.getCuentas().add(cuenta);
        cuentas.add(cuenta);
    }

    public List<Cuenta> obtenerCuentasDe(Usuario usuario) {
        return usuario.getCuentas();
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

    public double obtenerSaldo(Usuario usuario) {
        List<Cuenta> cuentas = obtenerCuentasDe(usuario);
        double total = 0;
        for (Cuenta cuenta : cuentas) {
            total += cuenta.getSaldo1();
        }
        return total;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public ServicioCuenta setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
        return this;
    }

    public boolean existeCuenta(String numCuenta) {
        if ("Corresponsal Bancario".equals(numCuenta)) {
            return true;
        }
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numCuenta)) {
                return true;
            }
        }
        return false;
    }

}
