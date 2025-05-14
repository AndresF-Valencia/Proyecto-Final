package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

public interface EstrategiaTransaccion {
    public void procesar(Transaccion transaccion);
}
