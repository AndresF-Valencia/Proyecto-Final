package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

/**
 * Estrategia para procesar transacciones de tipo compra.
 * Esta estrategia disminuye el saldo de la cuenta origen según el monto de la transacción.
 */
public class EstrategiaCompras implements EstrategiaTransaccion {

    /** Servicio para acceder y manipular las cuentas. */
    private ServicioCuenta servicioCuenta;

    /**
     * Constructor que recibe el servicio de cuentas.
     *
     * @param servicioCuenta Servicio para gestión de cuentas.
     */
    public EstrategiaCompras(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }

    /**
     * Procesa la transacción de compra, descontando el monto de la cuenta origen.
     *
     * @param transaccion La transacción a procesar.
     */
    @Override
    public void procesar(Transaccion transaccion) {
        String numCuentaOrigen = transaccion.getCuentaOrigen();

        for (Cuenta c : servicioCuenta.getCuentas()) {
            if (c.getNumeroCuenta().equals(numCuentaOrigen)) {
                c.setSaldo(c.getSaldo1() - transaccion.getMonto());
            }
        }
    }
}
