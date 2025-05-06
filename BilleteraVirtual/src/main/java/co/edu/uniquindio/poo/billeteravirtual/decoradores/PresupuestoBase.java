package co.edu.uniquindio.poo.billeteravirtual.decoradores;

public interface PresupuestoBase {
    double getMontoTotal();
    double getMontoGastado();
    String getDescripcion();
    void agregarGasto(double monto);
}