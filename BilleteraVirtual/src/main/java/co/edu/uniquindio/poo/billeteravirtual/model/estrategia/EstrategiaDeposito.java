package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

public class EstrategiaDeposito implements EstrategiaTransaccion {
    private ServicioCuenta servicioCuenta;

    public EstrategiaDeposito(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }

    @Override
    public void procesar(Transaccion transaccion) {
        String idCuentaDestino = transaccion.getCuentaDestino();
        for (Cuenta c : servicioCuenta.getCuentas()){
            if(c.getIdCuenta().equals(idCuentaDestino)){
                c.setSaldo(c.getSaldo1() + transaccion.getMonto());
                break;
            }
        }
    }
}
