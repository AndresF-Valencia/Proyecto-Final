package co.edu.uniquindio.poo.billeteravirtual.model.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioConPresupuesto implements IPresupuesto{
    private Usuario usuarioOriginal;
    private List<Presupuesto> presupuestos = new ArrayList<>();

    public UsuarioConPresupuesto(Usuario usuarioOriginal) {
        this.usuarioOriginal = usuarioOriginal;
    }

    public Usuario getUsuario() {
        return usuarioOriginal;
    }

    @Override
    public void agregarPresupuesto(Presupuesto presupuesto) {
        presupuestos.add(presupuesto);
    }

    @Override
    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

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

