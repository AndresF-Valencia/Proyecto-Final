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
        String idCuentaOrigen = transaccion.getCuentaOrigen();
        String idCuentaDestino = transaccion.getCuentaDestino();

        for(Cuenta origen: servicioCuenta.getCuentas()){
            if(origen.getIdCuenta().equals(idCuentaOrigen)){
                for(Cuenta destino: servicioCuenta.getCuentas()){
                    if(destino.getIdCuenta().equals(idCuentaDestino)){
                        origen.setSaldo(origen.getSaldo1() - transaccion.getMonto());
                        destino.setSaldo(destino.getSaldo1() + transaccion.getMonto());
                        break;
                    }
                }
            }
        }
    }
}
