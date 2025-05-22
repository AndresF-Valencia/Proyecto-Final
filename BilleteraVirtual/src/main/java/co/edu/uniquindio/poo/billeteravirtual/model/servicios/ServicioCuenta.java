package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.GeneradorCodigo;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las operaciones relacionadas con cuentas bancarias.
 */
public class ServicioCuenta {
    private static ServicioCuenta instancia;
    public List<Cuenta> cuentas = new ArrayList<>();

    private ServicioCuenta() {
    }

    /**
     * Retorna la instancia única del servicio.
     *
     * @return Instancia del servicio.
     */
    public static ServicioCuenta getInstancia() {
        if (instancia == null) {
            instancia = new ServicioCuenta();
        }
        return instancia;
    }

    /**
     * Registra una nueva cuenta para el usuario.
     *
     * @param numeroCuenta Número único de la cuenta.
     * @param tipoCuenta   Tipo de cuenta (ahorros, corriente, etc.).
     * @param bancoCuenta  Nombre del banco asociado.
     * @param usuario      Usuario al que se asigna la cuenta.
     * @throws IllegalArgumentException si el número de cuenta ya existe.
     */
    public void registrarCuenta(String numeroCuenta, String tipoCuenta, String bancoCuenta, Usuario usuario) {
        if (existeNumeroCuenta(numeroCuenta)) {
            throw new IllegalArgumentException("❌ El número de cuenta ya está en uso.");
        }

        String codigo = new GeneradorCodigo().generarCodigo();
        Cuenta cuenta = new Cuenta(codigo, numeroCuenta, tipoCuenta, bancoCuenta, usuario);
        usuario.getCuentas().add(cuenta);
        cuentas.add(cuenta);
    }

    /**
     * Retorna la lista de cuentas asociadas a un usuario.
     *
     * @param usuario Usuario del cual se desean obtener las cuentas.
     * @return Lista de cuentas del usuario.
     */
    public List<Cuenta> obtenerCuentasDe(Usuario usuario) {
        return usuario.getCuentas();
    }

    /**
     * Verifica si un número de cuenta ya está registrado.
     *
     * @param numeroCuenta Número de cuenta a verificar.
     * @return true si ya existe, false en caso contrario.
     */
    public boolean existeNumeroCuenta(String numeroCuenta) {
        return cuentas.stream().anyMatch(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }

    /**
     * Busca una cuenta por su número a través de todos los usuarios registrados.
     *
     * @param numeroCuenta Número de cuenta buscado.
     * @return La cuenta correspondiente o null si no se encuentra.
     */
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

    /**
     * Calcula el saldo total sumando todas las cuentas del usuario.
     *
     * @param usuario Usuario del cual se desea conocer el saldo.
     * @return Suma de los saldos de todas sus cuentas.
     */
    public double obtenerSaldo(Usuario usuario) {
        List<Cuenta> cuentas = obtenerCuentasDe(usuario);
        double total = 0;
        for (Cuenta cuenta : cuentas) {
            total += cuenta.getSaldo1();
        }
        return total;
    }

    /**
     * Retorna la lista de todas las cuentas registradas.
     *
     * @return Lista de cuentas.
     */
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     * Reemplaza la lista de cuentas registrada.
     *
     * @param cuentas Nueva lista de cuentas.
     * @return Instancia del servicio (para encadenamiento).
     */
    public ServicioCuenta setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
        return this;
    }

    /**
     * Verifica si una cuenta existe por su número.
     * También retorna true si el número es "Corresponsal Bancario".
     *
     * @param numCuenta Número de cuenta a verificar.
     * @return true si existe, false en caso contrario.
     */
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
