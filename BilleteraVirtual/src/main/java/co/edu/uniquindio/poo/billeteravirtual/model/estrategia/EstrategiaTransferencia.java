package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

/**
 * Estrategia para procesar transacciones de tipo transferencia.
 * Esta estrategia descuenta el monto de la cuenta origen y lo suma a la cuenta destino.
 */
public class EstrategiaTransferencia implements EstrategiaTransaccion {

    /** Servicio para acceder y manipular las cuentas. */
    private ServicioCuenta servicioCuenta;

    /**
     * Constructor que recibe el servicio de cuentas.
     *
     * @param servicioCuenta Servicio para gestión de cuentas.
     */
    public EstrategiaTransferencia(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }

    /**
     * Procesa la transacción de transferencia, descontando el monto de la cuenta origen
     * y sumándolo a la cuenta destino.
     *
     * @param transaccion La transacción a procesar.
     */
    @Override
    public void procesar(Transaccion transaccion) {
        String numCuentaOrigen = transaccion.getCuentaOrigen();
        String numCuentaDestino = transaccion.getCuentaDestino();

        for (Cuenta origen : servicioCuenta.getCuentas()) {
            if (origen.getNumeroCuenta().equals(numCuentaOrigen)) {
                for (Cuenta destino : servicioCuenta.getCuentas()) {
                    if (destino.getNumeroCuenta().equals(numCuentaDestino)) {
                        origen.setSaldo(origen.getSaldo1() - transaccion.getMonto());
                        destino.setSaldo(destino.getSaldo1() + transaccion.getMonto());
                        break;
                    }
                }
            }
        }
    }
}
