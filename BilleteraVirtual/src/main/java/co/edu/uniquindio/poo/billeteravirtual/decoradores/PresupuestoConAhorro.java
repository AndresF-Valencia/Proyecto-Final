package co.edu.uniquindio.poo.billeteravirtual.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class PresupuestoConAhorro extends PresupuestoDecorator{
    private double ahorro;

    public PresupuestoConAhorro(Presupuesto presupuesto) {
        super(presupuesto);
        this.ahorro = 0;
    }

    @Override
    public void agregarGasto(double monto) {
        double porcentajeAhorro = 0.1;
        double montoAhorro = monto * porcentajeAhorro;
        ahorro += montoAhorro;
        super.agregarGasto(monto - montoAhorro);
    }

    public double getAhorro() {
        return ahorro;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Ahorro automático";
    }
}
