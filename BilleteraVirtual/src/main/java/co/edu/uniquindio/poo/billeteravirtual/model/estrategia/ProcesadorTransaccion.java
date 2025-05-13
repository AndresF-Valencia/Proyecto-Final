package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;

public class ProcesadorTransaccion {
    private EstrategiaTransaccion estrategia;

    public void setEstrategia(EstrategiaTransaccion estrategia) {
        this.estrategia = estrategia;
    }

    public void procesar(Transaccion transaccion, Presupuesto presupuesto) {
        estrategia.procesar(transaccion, presupuesto);
    }
}
