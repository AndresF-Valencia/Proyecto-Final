package co.edu.uniquindio.poo.billeteravirtual.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class TransaccionIngreso implements EstrategiaTransaccion{
    @Override
    public void procesar(Transaccion transaccion, Presupuesto presupuesto) {
        presupuesto.setMontoTotal(presupuesto.getMontoTotal() + transaccion.getMonto());
    }
}
