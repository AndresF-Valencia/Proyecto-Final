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
        String numCuentaorigen = transaccion.getCuentaOrigen();

        for(Cuenta c: servicioCuenta.getCuentas()){
            if(c.getNumeroCuenta().equals(numCuentaorigen)){
                c.setSaldo(c.getSaldo1() - transaccion.getMonto());
            }
        }
    }
}
