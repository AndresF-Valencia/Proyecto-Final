package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

import java.util.*;

public class ServicioTransaccion {
    /**
     * Singleton que maneja las transacciones agrupadas por tipo.
     */
    private static final Map<String, List<Transaccion>> tipoTransacciones = new HashMap<>();
    private static ServicioTransaccion instancia;

    private ServicioTransaccion() {
        tipoTransacciones.put("TRANSFERENCIA", new ArrayList<>());
        tipoTransacciones.put("DEPOSITO", new ArrayList<>());
        tipoTransacciones.put("RETIRO", new ArrayList<>());
        tipoTransacciones.put("COMPRA", new ArrayList<>());
    }

    public static ServicioTransaccion getInstancia() {
        if (instancia == null) {
            instancia = new ServicioTransaccion();
        }
        return instancia;
    }

    /**
     * Agrega una transacción a la lista correspondiente a su tipo.
     * @param tipo Tipo de la transacción.
     * @param transaccion Transacción a agregar.
     */
    public void agregarTransaccionPorTipo(String tipo, Transaccion transaccion) {
        if (!tipoTransacciones.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo de transacción no permitido: " + tipo);
        }
        tipoTransacciones.get(tipo).add(transaccion);
    }

    /**
     * Obtiene todas las transacciones registradas sin importar el tipo.
     * @return Lista con todas las transacciones.
     */
    public static List<Transaccion> obtenerTodasLasTransacciones() {
        List<Transaccion> todasTransacciones = new ArrayList<>();
        for (List<Transaccion> transacciones : tipoTransacciones.values()) {
            todasTransacciones.addAll(transacciones);
        }
        return todasTransacciones;
    }

    /**
     * Obtiene las transacciones correspondientes a un tipo dado.
     * @param tipo Tipo de transacción.
     * @return Lista de transacciones del tipo especificado.
     */
    public List<Transaccion> obtenerTransaccionesPorTipo(String tipo) {
        if (!tipoTransacciones.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo de transacción no encontrado: " + tipo);
        }
        return tipoTransacciones.get(tipo);
    }

    /**
     * Obtiene las transacciones asociadas a un cliente identificado por su ID.
     * @param idCliente ID del cliente.
     * @return Lista de transacciones relacionadas con el cliente.
     */
    public List<Transaccion> obtenerTransaccionesPorCliente(String idCliente) {
        List<Transaccion> transaccionesPorCliente = new ArrayList<>();

        for (List<Transaccion> transacciones : tipoTransacciones.values()) {
            for (Transaccion transaccion : transacciones) {
                Cuenta cuentaOrigen = ServicioCuenta.obtenerCuentaPorNumero(transaccion.getCuentaOrigen());
                Cuenta cuentaDestino = ServicioCuenta.obtenerCuentaPorNumero(transaccion.getCuentaDestino());

                boolean esOrigenDelCliente = cuentaOrigen != null && cuentaOrigen.getUsuario().getCedula().equals(idCliente);
                boolean esDestinoDelCliente = cuentaDestino != null && cuentaDestino.getUsuario().getCedula().equals(idCliente);

                if (esOrigenDelCliente || esDestinoDelCliente) {
                    transaccionesPorCliente.add(transaccion);
                }
            }
        }
        return transaccionesPorCliente;
    }

    /**
     * Verifica si existe una transacción con un ID específico.
     * @param idTransaccion id de la transacción.
     * @return true si existe la transacción, false en caso contrario.
     */
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

    /**
     * Cuenta la cantidad de veces que se ha comprado cada producto.
     * @return Mapa con producto y su respectivo conteo de compras.
     */
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
