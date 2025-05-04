package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.facade.ValidacionTransaccionFacade;

import java.util.*;

public class ServicioTransaccion {

    private static Map<String, Transaccion> transacciones = new HashMap<>();

    // Registrar una transacción
    public void registrarTransaccion(String idUsuario, String idTransaccion, Date fecha, String tipo, double monto, String descripcion) throws ExcepcionTransaccion {
        if (transacciones.containsKey(idTransaccion)) {
            throw new ExcepcionTransaccion("Ya existe una Transacción con el id: " + idTransaccion);
        }

        // Crear la nueva transacción (idUsuario ahora se pasa primero)
        Transaccion nuevaTransaccion = new Transaccion(idUsuario, idTransaccion, fecha, tipo, monto, descripcion);

        // Validar la transacción utilizando el facade
        ValidacionTransaccionFacade.validarTransaccion(nuevaTransaccion);

        // Guardar la transacción en el mapa
        transacciones.put(idTransaccion, nuevaTransaccion);
        System.out.println("Transacción registrada con id: " + idTransaccion);
    }

    // Obtener una transacción por su ID
    public Transaccion obtenerTransaccion(String idTransaccion) throws ExcepcionTransaccion {
        Transaccion transaccion = transacciones.get(idTransaccion);
        if (transaccion == null) {
            throw new ExcepcionTransaccion("No existe una Transacción con el id: " + idTransaccion);
        }
        return transaccion;
    }

    // Eliminar una transacción por su ID
    public void eliminarTransaccion(String idTransaccion) throws ExcepcionTransaccion {
        if (!transacciones.containsKey(idTransaccion)) {
            throw new ExcepcionTransaccion("No existe una Transacción con el id: " + idTransaccion);
        }
        transacciones.remove(idTransaccion);
        System.out.println("Transacción eliminada con id: " + idTransaccion);
    }

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

    public Transaccion obtenerTransaccion(String idTransaccion, String tipo) throws ExcepcionTransaccion {
        Transaccion transaccion = transacciones.get(idTransaccion);
        if (transaccion == null) {
            throw new ExcepcionTransaccion("No existe una Transaccion con el id: " + idTransaccion);
        }
        return transaccion;
    }



    // Método opcional para verificar si existe una transacción por ID
    public boolean existeTransaccion(String idTransaccion) {
        return transacciones.containsKey(idTransaccion);
    }
}
