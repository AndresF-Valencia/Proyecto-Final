package co.edu.uniquindio.poo.billeteravirtual.model.servicios;


import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

import java.util.*;

public class ServicioTransaccion {

    // Mapa que mantiene las listas de transacciones por tipo
    private static final Map<String, List<Transaccion>> tipoTransacciones = new HashMap<>();

    // Instancia única del Singleton
    private static ServicioTransaccion instancia;

    // Constructor privado para evitar la creación de instancias fuera de la clase
    private ServicioTransaccion() {
        // Inicializar las listas de transacciones para tipos predefinidos
        tipoTransacciones.put("TRANSFERENCIA", new ArrayList<>());
        tipoTransacciones.put("DEPOSITO", new ArrayList<>());
        tipoTransacciones.put("RETIRO", new ArrayList<>());
        tipoTransacciones.put("COMPRA", new ArrayList<>());
    }

    // Método para obtener la instancia del Singleton
    public static ServicioTransaccion getInstancia() {
        if (instancia == null) {
            instancia = new ServicioTransaccion();
        }
        return instancia;
    }

    // Agregar transacción por tipo de transacción
    public void agregarTransaccionPorTipo(String tipo, Transaccion transaccion) {
        // Verificar si el tipo de transacción está permitido
        if (!tipoTransacciones.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo de transacción no permitido: " + tipo);
        }

        // Agregar la transacción a la lista correspondiente del tipo
        tipoTransacciones.get(tipo).add(transaccion);
    }

    // Obtener todas las transacciones
    public static List<Transaccion> obtenerTodasLasTransacciones() {
        List<Transaccion> todasTransacciones = new ArrayList<>();
        for (List<Transaccion> transacciones : tipoTransacciones.values()) {
            todasTransacciones.addAll(transacciones);
        }
        return todasTransacciones;
    }

    // Obtener las transacciones de un tipo específico
    public List<Transaccion> obtenerTransaccionesPorTipo(String tipo) {
        if (!tipoTransacciones.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo de transacción no encontrado: " + tipo);
        }
        return tipoTransacciones.get(tipo);
    }

    public static List<Transaccion> obtenerTransaccionesPorCliente(String idCliente) {
        List<Transaccion> transaccionesPorCliente = new ArrayList<>();

        for (List<Transaccion> transacciones : tipoTransacciones.values()) {
            for (Transaccion transaccion : transacciones) {

                Cuenta cuentaOrigen = ServicioCuenta.obtenerCuentaPorNumero(transaccion.getCuentaOrigen());

                // Verificar si la cuenta pertenece al cliente indicado
                if (cuentaOrigen != null && cuentaOrigen.getUsuario().getCedula().equals(idCliente)) {
                    transaccionesPorCliente.add(transaccion);
                }
            }
        }

        return transaccionesPorCliente;
    }

    // Método para verificar si existe una transacción por ID
    public boolean existeTransaccion(String idTransaccion) {
        for (List<Transaccion> transacciones : tipoTransacciones.values()) {
            for (Transaccion transaccion : transacciones) {
                if (transaccion.getIdTransaccion().equals(idTransaccion)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<String, Integer> contarProductosMasComprados() {
        Map<String, Integer> conteoProductos = new HashMap<>();
        List<Transaccion> compras = getCompras();

        for (Transaccion transaccion : compras) {
            String descripcion = transaccion.getDescripcion();

            if (descripcion.startsWith("Compra exitosa de ")) {
                String producto = descripcion.replace("Compra exitosa de ", "").trim().toLowerCase();
                conteoProductos.put(producto, conteoProductos.getOrDefault(producto, 0) + 1);
            }
        }

        return conteoProductos;
    }

    // Métodos adicionales para obtener las listas de transacciones por tipo
    public List<Transaccion> getTransferencias() {
        return tipoTransacciones.get("TRANSFERENCIA");
    }

    public List<Transaccion> getDepositos() {
        return tipoTransacciones.get("DEPOSITO");
    }

    public List<Transaccion> getRetiros() {
        return tipoTransacciones.get("RETIRO");
    }

    public List<Transaccion> getCompras() {
        return tipoTransacciones.get("COMPRA");
    }

    public List<Transaccion> getTransacciones() {
        return obtenerTodasLasTransacciones();
    }

}