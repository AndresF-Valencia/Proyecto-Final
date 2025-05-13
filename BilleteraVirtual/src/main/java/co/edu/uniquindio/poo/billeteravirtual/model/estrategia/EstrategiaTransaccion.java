package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

public interface EstrategiaTransaccion {
    //Metodo procesar que heredan todas las transacciones
    void procesar(Transaccion transaccion, Presupuesto presupuesto);
}
