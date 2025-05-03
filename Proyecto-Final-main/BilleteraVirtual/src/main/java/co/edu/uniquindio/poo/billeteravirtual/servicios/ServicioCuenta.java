package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionCuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;

import java.util.HashMap;
import java.util.Map;

public class ServicioCuenta {

    private Map<Integer, Cuenta> cuentas = new HashMap();

    public void registrarCuenta(int idCuenta, String numeroCuenta, String tipoCuenta) throws ExcepcionCuenta {
        if (cuentas.containsKey(idCuenta)) {
            throw new ExcepcionCuenta("La ID de la cuenta ya existe");
        }
        cuentas.put(idCuenta, new Cuenta(idCuenta, numeroCuenta, tipoCuenta));
        System.out.println("Cuenta Registrada Exitosamente");
    }

    public Cuenta obtenerCuenta(int idCuenta) throws ExcepcionCuenta {
        Cuenta cuenta = cuentas.get(idCuenta);
        if (cuenta == null) {
            throw new ExcepcionCuenta("La cuenta no existe");
        }
        return cuenta;
    }

    public void eliminarCuenta(int idCuenta) throws ExcepcionCuenta {
        if (!cuentas.containsKey(idCuenta)) {
            throw new ExcepcionCuenta("La cuenta no existe");
        }
        cuentas.remove(idCuenta);
        System.out.println("Cuenta Eliminada Exitosamente");
    }
}
