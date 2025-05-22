package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

/**
 * Interfaz que define la estrategia para procesar una transacción.
 */
public interface EstrategiaTransaccion {

    /**
     * Método para procesar una transacción.
     *
     * @param transaccion Transacción a procesar.
     */
    public void procesar(Transaccion transaccion);
}
