package co.edu.uniquindio.poo.billeteravirtual.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class PresupuestoConLimite extends PresupuestoDecorator {
    private double limite;

    public PresupuestoConLimite(Presupuesto presupuesto, double limite) {
        super(presupuesto);  // Pasamos el presupuesto base
        this.limite = limite;
    }

    @Override
    public void agregarGasto(double gasto) {
        if (gasto <= limite) {
            super.agregarGasto(gasto);
        } else {
            System.out.println("El gasto excede el límite.");
        }
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Control de Límite";
    }
}
