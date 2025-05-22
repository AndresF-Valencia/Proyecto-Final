package co.edu.uniquindio.poo.billeteravirtual.model.facade;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.TransaccionFactory;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;

import java.time.LocalDate;

/**
 * Fachada que encapsula la lógica para procesar una transacción,
 * delegando la creación y ejecución a las clases correspondientes.
 */
public class TransaccionFacade {
    private ServicioCuenta servicioCuenta;
    private TransaccionFactory transaccionFactory;
    private ServicioTransaccion servicioTransaccion;

    /**
     * Crea una instancia de TransaccionFacade con los servicios necesarios.
     *
     * @param servicioCuenta       Servicio para validar y obtener cuentas.
     * @param servicioTransaccion  Servicio para registrar transacciones.
     * @param transaccionFactory   Fábrica para crear objetos Transaccion.
     */
    public TransaccionFacade(ServicioCuenta servicioCuenta, ServicioTransaccion servicioTransaccion, TransaccionFactory transaccionFactory) {
        this.servicioCuenta = servicioCuenta;
        this.servicioTransaccion = servicioTransaccion;
        this.transaccionFactory = transaccionFactory;
    }

    /**
     * Procesa una transacción de cualquier tipo: depósito, retiro, transferencia, etc.
     * Valida las cuentas y el monto antes de ejecutarla.
     *
     * @param idTransaccion Identificador único de la transacción.
     * @param fecha Fecha en que se realiza la transacción.
     * @param tipo Tipo de transacción (ej. "deposito", "retiro", "transferencia").
     * @param monto Monto de la transacción.
     * @param descripcion Descripción de la transacción.
     * @param cuentaOrigen Número de cuenta de origen.
     * @param cuentaDestino Número de cuenta de destino.
     */
    public void procesarTransaccion(String idTransaccion, LocalDate fecha, String tipo, double monto, String descripcion, String cuentaOrigen, String cuentaDestino) {
        try {
            validarCuentas(cuentaOrigen, cuentaDestino);
            validarMonto(monto);

            Transaccion transaccion = TransaccionFactory.crear(idTransaccion, fecha, tipo, monto, descripcion, cuentaOrigen, cuentaDestino);
            transaccion.ejecutar();

            servicioTransaccion.agregarTransaccionPorTipo(tipo, transaccion);

            Cuenta cuenta = ServicioCuenta.obtenerCuentaPorNumero(cuentaOrigen);
            if (cuenta != null) {
                cuenta.getTransacciones().add(transaccion);
            }

            if (!cuentaOrigen.equals(cuentaDestino)) {
                Cuenta cuentaDest = ServicioCuenta.obtenerCuentaPorNumero(cuentaDestino);
                if (cuentaDest != null) {
                    cuentaDest.getTransacciones().add(transaccion);
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error en la transacción: " + e.getMessage());
        }
    }

    /**
     * Valida que ambas cuentas (origen y destino) existan.
     *
     * @param origen Número de cuenta de origen.
     * @param destino Número de cuenta de destino.
     * @throws IllegalArgumentException si alguna cuenta no existe.
     */
    private void validarCuentas(String origen, String destino) {
        if (!servicioCuenta.existeCuenta(origen) || !servicioCuenta.existeCuenta(destino)) {
            throw new IllegalArgumentException("Una o ambas cuentas no existen.");
        }
    }

    /**
     * Valida que el monto de la transacción sea mayor a cero.
     *
     * @param monto Monto a validar.
     * @throws IllegalArgumentException si el monto es menor o igual a cero.
     */
    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
    }
}
