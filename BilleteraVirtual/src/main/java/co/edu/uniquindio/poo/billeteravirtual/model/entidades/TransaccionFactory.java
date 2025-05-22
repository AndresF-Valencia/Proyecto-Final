package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaCompras;
import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaDeposito;
import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaRetiro;
import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaTransferencia;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

import java.time.LocalDate;

/**
 * Fábrica para crear transacciones con su estrategia correspondiente.
 */
public class TransaccionFactory {
    private static ServicioCuenta servicioCuenta;

    /**
     * Establece el servicio de cuentas usado por las estrategias.
     *
     * @param sc Servicio de cuentas a utilizar.
     */
    public static void setServicioCuenta(ServicioCuenta sc) {
        servicioCuenta = sc;
    }

    /**
     * Crea una nueva transacción del tipo especificado, asociando la estrategia correspondiente.
     *
     * @param idTransaccion ID de la transacción.
     * @param fecha Fecha de la transacción.
     * @param tipo Tipo de transacción ("DEPOSITO", "RETIRO", "TRANSFERENCIA", "COMPRA").
     * @param monto Monto de la transacción.
     * @param descripcion Descripción.
     * @param cuentaOrigen Cuenta desde la que se origina la transacción.
     * @param cuentaDestino Cuenta de destino.
     * @return Objeto Transaccion configurado.
     * @throws IllegalArgumentException Si el tipo no es válido.
     */
    public static Transaccion crear(String idTransaccion, LocalDate fecha, String tipo, double monto, String descripcion,String cuentaOrigen, String cuentaDestino) {
        Transaccion t = new Transaccion(idTransaccion,fecha,tipo, monto,descripcion,cuentaOrigen, cuentaDestino);

        switch (tipo.toUpperCase()) {
            case "DEPOSITO":
                t.setEstrategia(new EstrategiaDeposito(servicioCuenta));
                break;
            case "RETIRO":
                t.setEstrategia(new EstrategiaRetiro(servicioCuenta));
                break;
            case "TRANSFERENCIA":
                t.setEstrategia(new EstrategiaTransferencia(servicioCuenta));
                break;
            case "COMPRA":
                t.setEstrategia(new EstrategiaCompras(servicioCuenta));
                break;
            default:
                throw new IllegalArgumentException("Tipo de transacción no soportado.");
        }
        return t;
    }
}
