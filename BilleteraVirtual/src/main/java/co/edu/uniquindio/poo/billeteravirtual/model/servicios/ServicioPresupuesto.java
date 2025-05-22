package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.decoradores.UsuarioConPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.*;

/**
 * Gestiona los presupuestos asociados a los usuarios.
 */
public class ServicioPresupuesto {
    private static ServicioPresupuesto instancia;
    public Map<Usuario, List<Presupuesto>> presupuestosPorUsuario = new HashMap<>();
    public List<Presupuesto> presupuestos = new ArrayList<>();

    private ServicioPresupuesto() {}

    /**
     * Retorna la instancia única del servicio.
     *
     * @return Instancia del servicio de presupuestos.
     */
    public static ServicioPresupuesto getInstancia() {
        if (instancia == null) {
            instancia = new ServicioPresupuesto();
        }
        return instancia;
    }

    /**
     * Agrega un presupuesto a un usuario.
     *
     * @param usuario    Usuario al que se le asigna el presupuesto.
     * @param presupuesto Presupuesto a agregar.
     */
    public void agregarPresupuesto(Usuario usuario, Presupuesto presupuesto) {
        List<Presupuesto> lista = presupuestosPorUsuario.getOrDefault(usuario, new ArrayList<>());
        lista.add(presupuesto);
        presupuestosPorUsuario.put(usuario, lista);
    }

    /**
     * Obtiene los presupuestos de un usuario.
     *
     * @param usuario Usuario consultado.
     * @return Lista de presupuestos del usuario.
     */
    public List<Presupuesto> obtenerPresupuestos(Usuario usuario) {
        return presupuestosPorUsuario.getOrDefault(usuario, Collections.emptyList());
    }

    /**
     * Crea un usuario decorado con sus presupuestos.
     *
     * @param usuario Usuario base.
     * @return Usuario con presupuestos agregados.
     */
    public UsuarioConPresupuesto crearUsuarioConPresupuesto(Usuario usuario) {
        UsuarioConPresupuesto decorado = new UsuarioConPresupuesto(usuario);
        List<Presupuesto> presupuestos = obtenerPresupuestos(usuario);
        for (Presupuesto p : presupuestos) {
            decorado.agregarPresupuesto(p);
        }
        return decorado;
    }

    /**
     * Obtiene el monto gastado en una categoría para un usuario.
     *
     * @param usuario   Usuario consultado.
     * @param categoria Categoría del presupuesto.
     * @return Monto gastado.
     */
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

    /**
     * Obtiene el monto total asignado en una categoría para un usuario.
     *
     * @param usuario   Usuario consultado.
     * @param categoria Categoría del presupuesto.
     * @return Monto total.
     */
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

    /**
     * Obtiene el monto disponible restante en una categoría para un usuario.
     *
     * @param usuario   Usuario consultado.
     * @param categoria Categoría del presupuesto.
     * @return Monto disponible.
     */
    public double obtenerMontoDisponible(Usuario usuario, String categoria) {
        return obtenerMontoTotal(usuario, categoria) - obtenerMontoGastado(usuario, categoria);
    }

    /**
     * Elimina un presupuesto de un usuario.
     *
     * @param usuario    Usuario al que pertenece el presupuesto.
     * @param presupuesto Presupuesto a eliminar.
     */
    public void eliminarPresupuesto(Usuario usuario, Presupuesto presupuesto) {
        List<Presupuesto> lista = presupuestosPorUsuario.get(usuario);
        if (lista != null) {
            lista.remove(presupuesto);
        }
    }

    /**
     * Actualiza el monto total de un presupuesto de un usuario.
     *
     * @param usuario      Usuario al que pertenece el presupuesto.
     * @param presupuesto  Presupuesto a modificar.
     * @param nuevoMonto   Nuevo monto total.
     */
    public void actualizarMontoPresupuesto(Usuario usuario, Presupuesto presupuesto, double nuevoMonto) {
        List<Presupuesto> lista = presupuestosPorUsuario.get(usuario);
        if (lista != null && lista.contains(presupuesto)) {
            presupuesto.setMontoTotal(nuevoMonto);
        }
    }
}
