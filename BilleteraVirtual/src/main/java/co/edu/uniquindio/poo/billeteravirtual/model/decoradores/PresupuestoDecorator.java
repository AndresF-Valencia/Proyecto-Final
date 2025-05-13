package co.edu.uniquindio.poo.billeteravirtual.model.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;

public abstract class PresupuestoDecorator extends Presupuesto {
    protected Presupuesto presupuestoOriginal;

    public PresupuestoDecorator(Presupuesto presupuestoOriginal) {
        super(presupuestoOriginal.getIdPresupuesto(), presupuestoOriginal.getDescripcion(),
                presupuestoOriginal.getMontoTotal(), presupuestoOriginal.getMontoGastado());
        this.presupuestoOriginal = presupuestoOriginal;
    }

    public Presupuesto getPresupuestoOriginal() {
        return presupuestoOriginal;
    }

    @Override
    public void agregarGasto(double gasto) {
        presupuestoOriginal.agregarGasto(gasto);
    }

    @Override
    public double getMontoGastado() {
        return presupuestoOriginal.getMontoGastado();
    }

    @Override
    public String getDescripcion() {
        return presupuestoOriginal.getDescripcion();
    }
}
