package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proporciona estadísticas basadas en las transacciones de los usuarios.
 */
public class ServicioEstadisticas {
    private static ServicioEstadisticas instancia;
    private final ServicioUsuario servicioUsuario;

    private ServicioEstadisticas() {
        this.servicioUsuario = ServicioUsuario.getInstancia();
    }

    /**
     * Retorna la instancia única del servicio.
     *
     * @return Instancia del servicio de estadísticas.
     */
    public static ServicioEstadisticas getInstancia() {
        if (instancia == null) {
            instancia = new ServicioEstadisticas();
        }
        return instancia;
    }

    /**
     * Obtiene un mapa con los gastos agrupados por categoría.
     * Considera transacciones de tipo "COMPRA" y "RETIRO".
     *
     * @return Mapa con categorías y su gasto total acumulado.
     */
    public Map<String, Double> obtenerGastosPorCategoria() {
        Map<String, Double> categorias = new HashMap<>();

        List<Usuario> usuarios = servicioUsuario.getUsuariosRegistrados();
        for (Usuario usuario : usuarios) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                for (Transaccion t : cuenta.getTransacciones()) {
                    if ("COMPRA".equalsIgnoreCase(t.getTipo()) || "RETIRO".equalsIgnoreCase(t.getTipo())) {
                        String categoria = t.getDescripcion(); // Asegúrate de que esta sea la categoría
                        categorias.put(categoria, categorias.getOrDefault(categoria, 0.0) + t.getMonto());
                    }
                }
            }
        }

        return categorias;
    }
}
