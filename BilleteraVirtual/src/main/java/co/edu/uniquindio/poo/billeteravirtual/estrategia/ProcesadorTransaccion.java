package co.edu.uniquindio.poo.billeteravirtual.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class ProcesadorTransaccion {
    private EstrategiaTransaccion estrategia;

    public void setEstrategia(EstrategiaTransaccion estrategia) {
        this.estrategia = estrategia;
    }

    public void procesar(Transaccion transaccion, Presupuesto presupuesto) {
        estrategia.procesar(transaccion, presupuesto);
    }
}
