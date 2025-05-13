package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;

public class TransaccionIngreso implements EstrategiaTransaccion{
    @Override
    public void procesar(Transaccion transaccion, Presupuesto presupuesto) {
        presupuesto.setMontoTotal(presupuesto.getMontoTotal() + transaccion.getMonto());
    }
}
