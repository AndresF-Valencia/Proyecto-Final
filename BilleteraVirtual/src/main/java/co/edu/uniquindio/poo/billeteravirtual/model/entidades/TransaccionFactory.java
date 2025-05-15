package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaCompras;
import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaDeposito;
import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaRetiro;
import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaTransferencia;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

import java.time.LocalDate;

public class TransaccionFactory {
    private static ServicioCuenta servicioCuenta;

    public static void setServicioCuenta(ServicioCuenta sc) {
        servicioCuenta = sc;
    }

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
            default:
                throw new IllegalArgumentException("Tipo de transacción no soportado.");
        }
        return t;
    }
}
