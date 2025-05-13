package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;

public class TransaccionGasto implements EstrategiaTransaccion {
    @Override
    //Metodo implementado
    public void procesar(Transaccion transaccion, Presupuesto presupuesto) {
        double nuevoGastado = presupuesto.getMontoGastado() + transaccion.getMonto();
        presupuesto.setMontoGastado(nuevoGastado);
    }
}
