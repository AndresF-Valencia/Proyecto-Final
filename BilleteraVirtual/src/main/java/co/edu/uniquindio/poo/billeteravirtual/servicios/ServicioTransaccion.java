package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;

import java.util.*;

public class ServicioTransaccion {

    private Map<Integer, Transaccion> transacciones = new HashMap<>();

    //Registrar una Transacción
    public void registrarTransaccion(int idTransaccion, Date fecha,String tipo, double monto, String descripcion) throws ExcepcionTransaccion {
        if (transacciones.containsKey(idTransaccion)) {
            throw new ExcepcionTransaccion("Ya existe una Transacion con el id: " + idTransaccion);
        }
        transacciones.put(idTransaccion, new Transaccion(idTransaccion, fecha, tipo, monto, descripcion));
        System.out.println("Transaccion registrada con id: " + idTransaccion);
    }

    //Obtener transacción por ID
    public Transaccion obtenerTransaccion(int idTransaccion, String tipo) throws ExcepcionTransaccion {
        Transaccion transaccion = transacciones.get(idTransaccion);
        if (transaccion == null) {
            throw new ExcepcionTransaccion("No existe una Transaccion con el id: " + idTransaccion);
        }
        return transaccion;
    }

    //Eliminar transacción
    public void eliminarTransaccion(int idTransaccion) throws ExcepcionTransaccion {
        if(!transacciones.containsKey(idTransaccion)) {
            throw new ExcepcionTransaccion("No existe una Transaccion con el id: " + idTransaccion);
        }
        transacciones.remove(idTransaccion);
        System.out.println("Transaccion eliminada con id: " + idTransaccion);
    }

    //Listar todas las transacciónes
    public List<Transaccion> listarTransaciones(){
        return new ArrayList<Transaccion>(transacciones.values());
    }
}
