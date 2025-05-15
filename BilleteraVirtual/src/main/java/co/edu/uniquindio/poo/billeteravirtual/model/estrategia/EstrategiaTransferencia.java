package co.edu.uniquindio.poo.billeteravirtual.model.estrategia;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;

public class EstrategiaTransferencia implements EstrategiaTransaccion{
    private ServicioCuenta servicioCuenta;

    public EstrategiaTransferencia(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }

    @Override
    public void procesar(Transaccion transaccion) {
        String numCuentaOrigen = transaccion.getCuentaOrigen();
        String numCuentaDestino = transaccion.getCuentaDestino();

        for(Cuenta origen: servicioCuenta.getCuentas()){
            if(origen.getNumeroCuenta().equals(numCuentaOrigen)){
                for(Cuenta destino: servicioCuenta.getCuentas()){
                    if(destino.getNumeroCuenta().equals(numCuentaDestino)){
                        origen.setSaldo(origen.getSaldo1() - transaccion.getMonto());
                        destino.setSaldo(destino.getSaldo1() + transaccion.getMonto());
                        break;
                    }
                }
            }
        }
    }
}
