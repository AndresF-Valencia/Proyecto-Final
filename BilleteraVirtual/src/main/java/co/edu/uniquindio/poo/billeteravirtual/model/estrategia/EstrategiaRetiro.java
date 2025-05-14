package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

public class EstrategiaRetiro implements EstrategiaTransaccion {
    private ServicioCuenta servicioCuenta;

    public EstrategiaRetiro(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }

    @Override
    public void procesar(Transaccion transaccion) {
        String idCuentaOrigen = transaccion.getCuentaOrigen();
        for (Cuenta c : servicioCuenta.getCuentas()){
            if(c.getIdCuenta().equals(idCuentaOrigen)){
                c.setSaldo(c.getSaldo1() - transaccion.getMonto());
            }
        }
    }
}
