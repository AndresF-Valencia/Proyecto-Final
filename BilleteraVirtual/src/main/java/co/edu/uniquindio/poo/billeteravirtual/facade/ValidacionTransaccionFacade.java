package co.edu.uniquindio.poo.billeteravirtual.facade;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;

public class ValidacionTransaccionFacade {
    public static void validarTransaccion(Transaccion transaccion) throws IllegalArgumentException {
        validarMonto(transaccion.getMonto());
        validarTipo(transaccion.getTipo());
        validarDescripcion(transaccion.getDescripcion());
        validarFecha(transaccion.getFecha());
    }

    private static void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
    }

    private static void validarTipo(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio.");
        }
        // Podrías validar tipos válidos como "Ingreso", "Egreso", etc.
    }

    private static void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.length() < 3) {
            throw new IllegalArgumentException("La descripción es demasiado corta.");
        }
    }

    private static void validarFecha(java.util.Date fecha) {
        if (fecha == null || fecha.after(new java.util.Date())) {
            throw new IllegalArgumentException("La fecha no puede ser futura o nula.");
        }
    }
}
