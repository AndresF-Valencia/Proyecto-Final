package co.edu.uniquindio.poo.billeteravirtual.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class TransaccionTransferencia implements EstrategiaTransaccion{
    @Override
    public void procesar(Transaccion transaccion, Presupuesto presupuesto) {
        // Restar del total si se presenta una transferencia
        presupuesto.setMontoTotal(presupuesto.getMontoTotal() - transaccion.getMonto());
    }
}
