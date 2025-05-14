package co.edu.uniquindio.poo.billeteravirtual.model.facade;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.TransaccionFactory;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;

import java.time.LocalDate;
import java.util.List;

public class TransaccionFacade {
    private ServicioCuenta servicioCuenta;
    private TransaccionFactory transaccionFactory;
    private ServicioTransaccion servicioTransaccion;

    public TransaccionFacade(ServicioCuenta servicioCuenta, ServicioTransaccion servicioTransaccion, TransaccionFactory transaccionFactory) {
        this.servicioCuenta = servicioCuenta;
        this.servicioTransaccion = servicioTransaccion;
        this.transaccionFactory = transaccionFactory;
    }

    public void procesarTransaccion(String idTransaccion, LocalDate fecha, String tipo, double monto, String descripcion,String cuentaOrigen, String cuentaDestino) {
        try {
            validarCuentas(cuentaOrigen, cuentaDestino);
            validarMonto(monto);

            Transaccion transaccion = TransaccionFactory.crear(idTransaccion,fecha,tipo, monto,descripcion,cuentaOrigen, cuentaDestino);

            transaccion.ejecutar();

            servicioTransaccion.agregarTransaccionPorTipo(tipo, transaccion);

        } catch (IllegalArgumentException e) {
            System.err.println("Error en la transacción: " + e.getMessage());
        }
    }

    private void validarCuentas(String origen, String destino) {
        if (!servicioCuenta.existeCuenta(origen) || !servicioCuenta.existeCuenta(destino)) {
            throw new IllegalArgumentException("Una o ambas cuentas no existen.");
        }
    }

    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
    }

}
