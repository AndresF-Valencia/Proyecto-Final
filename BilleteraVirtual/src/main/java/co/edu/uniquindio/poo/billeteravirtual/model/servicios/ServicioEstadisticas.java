package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioEstadisticas {

    private final ServicioUsuario servicioUsuario;

    public ServicioEstadisticas() {
        this.servicioUsuario = ServicioUsuario.getInstancia();
    }

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

