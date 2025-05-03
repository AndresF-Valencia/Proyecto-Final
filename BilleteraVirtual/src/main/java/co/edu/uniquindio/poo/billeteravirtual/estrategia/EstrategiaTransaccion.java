package co.edu.uniquindio.poo.billeteravirtual.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;

public interface EstrategiaTransaccion {
    //Metodo procesar que heredan todas las transacciones
    void procesar(Transaccion transaccion, Presupuesto presupuesto);
}
