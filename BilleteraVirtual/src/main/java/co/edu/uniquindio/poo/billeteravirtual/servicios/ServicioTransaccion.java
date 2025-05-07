package co.edu.uniquindio.poo.billeteravirtual.servicios;


import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.facade.ValidacionTransaccionFacade;

import java.util.*;

public class ServicioTransaccion {

    private static Map<String, Transaccion> transacciones = new HashMap<>();


    // Obtener todas las transacciones
    public static List<Transaccion> obtenerTodasLasTransacciones() {
        return new ArrayList<>(transacciones.values());
    }

    // Listar todas las transacciones
    public List<Transaccion> listarTransaciones(){
        return new ArrayList<Transaccion>(transacciones.values());
    }

    // Obtener las transacciones de un cliente específico por su idUsuario
    public static List<Transaccion> obtenerTransaccionesPorCliente(String idCliente) {
        List<Transaccion> transaccionesPorCliente = new ArrayList<>();

        for (Transaccion transaccion : transacciones.values()) {
            if (transaccion.getIdUsuario().equals(idCliente)) {
                transaccionesPorCliente.add(transaccion);
            }
        }

        return transaccionesPorCliente;
    }

    public Transaccion obtenerTransaccion(String idTransaccion, String tipo) {
        Transaccion transaccion = transacciones.get(idTransaccion);
        return transaccion;
    }



    // Método opcional para verificar si existe una transacción por ID
    public boolean existeTransaccion(String idTransaccion) {
        return transacciones.containsKey(idTransaccion);
    }
}
