package co.edu.uniquindio.poo.billeteravirtual.model.servicios;
import co.edu.uniquindio.poo.billeteravirtual.model.decoradores.UsuarioConPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.*;

public class ServicioPresupuesto {
    private static ServicioPresupuesto instancia;
    public Map<Usuario, List<Presupuesto>> presupuestosPorUsuario = new HashMap<>();
    public List<Presupuesto> presupuestos = new ArrayList<>();


    private ServicioPresupuesto() {}

    public static ServicioPresupuesto getInstancia() {
        if (instancia == null) {
            instancia = new ServicioPresupuesto();
        }
        return instancia;
    }

    // Agregar presupuesto a un usuario
    public void agregarPresupuesto(Usuario usuario, Presupuesto presupuesto) {
        List<Presupuesto> lista = presupuestosPorUsuario.getOrDefault(usuario, new ArrayList<>());
        lista.add(presupuesto);
        presupuestosPorUsuario.put(usuario, lista);
    }

    // Obtener presupuestos de un usuario
    public List<Presupuesto> obtenerPresupuestos(Usuario usuario) {
        return presupuestosPorUsuario.getOrDefault(usuario, Collections.emptyList());
    }

    // Crear el decorador con presupuestos para un usuario dado
    public UsuarioConPresupuesto crearUsuarioConPresupuesto(Usuario usuario) {
        UsuarioConPresupuesto decorado = new UsuarioConPresupuesto(usuario);
        List<Presupuesto> presupuestos = obtenerPresupuestos(usuario);
        for (Presupuesto p : presupuestos) {
            decorado.agregarPresupuesto(p);
        }
        return decorado;
    }

    public double obtenerMontoGastado(Usuario usuario, String categoria) {
        List<Presupuesto> presupuestos = obtenerPresupuestos(usuario);
        for (Presupuesto p : presupuestos) {
            if (p.isEsGeneral() && "General".equalsIgnoreCase(categoria)) {
                return p.getMontoGastado();
            } else if (p.getCategoria().equalsIgnoreCase(categoria)) {
                return p.getMontoGastado();
            }
        }
        return 0.0;
    }

    public double obtenerMontoTotal(Usuario usuario, String categoria) {
        List<Presupuesto> presupuestos = obtenerPresupuestos(usuario);
        for (Presupuesto p : presupuestos) {
            if (p.isEsGeneral() && "General".equalsIgnoreCase(categoria)) {
                return p.getMontoTotal();
            } else if (p.getCategoria().equalsIgnoreCase(categoria)) {
                return p.getMontoTotal();
            }
        }
        return 0.0;
    }

    public double obtenerMontoDisponible(Usuario usuario, String categoria) {
        return obtenerMontoTotal(usuario, categoria) - obtenerMontoGastado(usuario, categoria);
    }


    public void eliminarPresupuesto(Usuario usuario, Presupuesto presupuesto) {
        List<Presupuesto> lista = presupuestosPorUsuario.get(usuario);
        if (lista != null) {
            lista.remove(presupuesto);
        }
    }

    public void actualizarMontoPresupuesto(Usuario usuario, Presupuesto presupuesto, double nuevoMonto) {
        List<Presupuesto> lista = presupuestosPorUsuario.get(usuario);
        if (lista != null && lista.contains(presupuesto)) {
            presupuesto.setMontoTotal(nuevoMonto);
        }
    }

}
