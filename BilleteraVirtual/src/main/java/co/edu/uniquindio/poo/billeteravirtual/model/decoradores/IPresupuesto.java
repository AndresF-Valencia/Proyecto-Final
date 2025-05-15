package co.edu.uniquindio.poo.billeteravirtual.model.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;

import java.util.List;

public interface IPresupuesto {
    public void agregarPresupuesto(Presupuesto presupuesto);
    public List<Presupuesto> getPresupuestos();
    public boolean sePuedeRealizarTransaccion(double monto, String categoria);
    public void registrarGasto(double monto, String categoria);
}
