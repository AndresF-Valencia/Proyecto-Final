package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class Presupuesto implements Cloneable{
    private int idPresupuesto;
    private String descripcion;
    private Double montoTotal;
    private double montoGastado;

    //Constructor de presupuesto
    public Presupuesto(int idPresupuesto, String descripcion, Double montoTotal, double montoGastado) {
        this.idPresupuesto = idPresupuesto;
        this.descripcion = descripcion;
        this.montoTotal = montoTotal;
        this.montoGastado = montoGastado;
    }
    //Getters y Setters de Presupuesto
    public int getIdPresupuesto() {
        return idPresupuesto;
    }
    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Double getMontoTotal() {
        return montoTotal;
    }
    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
    public double getMontoGastado() {
        return montoGastado;
    }
    public void setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
    }
    //Metodo To String de Presupuesto

    @Override
    public Presupuesto clone() {
        try {
            return (Presupuesto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clonación fallida", e);
        }
    }

    @Override
    public String toString() {
        return "Presupuesto{" +
                "idPresupuesto=" + idPresupuesto +
                ", descripcion='" + descripcion + '\'' +
                ", montoTotal=" + montoTotal +
                ", montoGastado=" + montoGastado +
                '}';
    }
}
