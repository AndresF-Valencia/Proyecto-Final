package co.edu.uniquindio.poo.billeteravirtual.model.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase decoradora que extiende las funcionalidades de un {@link Usuario}
 * permitiendo asociar y gestionar presupuestos.
 */
public class UsuarioConPresupuesto implements IPresupuesto {

    /**
     * Usuario original al que se le añade funcionalidad de presupuestos.
     */
    private Usuario usuarioOriginal;

    /**
     * Lista de presupuestos asociados al usuario.
     */
    private List<Presupuesto> presupuestos = new ArrayList<>();

    /**
     * Constructor que inicializa el decorador con el usuario original.
     *
     * @param usuarioOriginal Usuario a decorar.
     */
    public UsuarioConPresupuesto(Usuario usuarioOriginal) {
        this.usuarioOriginal = usuarioOriginal;
    }

    /**
     * Devuelve el usuario original.
     *
     * @return Usuario original.
     */
    public Usuario getUsuario() {
        return usuarioOriginal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void agregarPresupuesto(Presupuesto presupuesto) {
        presupuestos.add(presupuesto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sePuedeRealizarTransaccion(double monto, String categoria) {
        for (Presupuesto p : presupuestos) {
            if (p.isEsGeneral() || (p.getCategoria().equalsIgnoreCase(categoria))) {
                if (p.getMontoGastado() + monto > p.getMontoTotal()) {
                    return false;
                }
            } else {
                if (p.getCategoria().equalsIgnoreCase(categoria)) {
                    if (p.getMontoGastado() + monto > p.getMontoTotal()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registrarGasto(double monto, String categoria) {
        for (Presupuesto p : presupuestos) {
            if (p.isEsGeneral() || (p.getCategoria().equalsIgnoreCase(categoria))) {
                p.setMontoGastado(p.getMontoGastado() + monto);
            } else {
                if (p.getCategoria().equalsIgnoreCase(categoria)) {
                    p.setMontoGastado(p.getMontoGastado() + monto);
                }
            }
        }
    }
}
