package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

/**
 * Estrategia para procesar transacciones de tipo depósito.
 * Esta estrategia incrementa el saldo de la cuenta destino según el monto de la transacción.
 */
public class EstrategiaDeposito implements EstrategiaTransaccion {

    /** Servicio para acceder y manipular las cuentas. */
    private ServicioCuenta servicioCuenta;

    /**
     * Constructor que recibe el servicio de cuentas.
     *
     * @param servicioCuenta Servicio para gestión de cuentas.
     */
    public EstrategiaDeposito(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }

    /**
     * Procesa la transacción de depósito, sumando el monto a la cuenta destino.
     *
     * @param transaccion La transacción a procesar.
     */
    @Override
    public void procesar(Transaccion transaccion) {
        String numCuentaDestino = transaccion.getCuentaDestino();
        for (Cuenta c : servicioCuenta.getCuentas()) {
            if (c.getNumeroCuenta().equals(numCuentaDestino)) {
                c.setSaldo(c.getSaldo1() + transaccion.getMonto());
                break;
            }
        }
    }
}
