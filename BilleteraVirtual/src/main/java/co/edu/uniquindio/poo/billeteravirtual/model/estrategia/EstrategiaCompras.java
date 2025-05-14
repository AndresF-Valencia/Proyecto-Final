package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

public class EstrategiaCompras implements EstrategiaTransaccion{
    private ServicioCuenta servicioCuenta;
    public EstrategiaCompras(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }
    @Override
    public void procesar(Transaccion transaccion) {
        String idCuentaorigen = transaccion.getCuentaOrigen();

        for(Cuenta c: servicioCuenta.getCuentas()){
            if(c.getIdCuenta().equals(idCuentaorigen)){
                c.setSaldo(c.getSaldo1() - transaccion.getMonto());
            }
        }
    }
}
