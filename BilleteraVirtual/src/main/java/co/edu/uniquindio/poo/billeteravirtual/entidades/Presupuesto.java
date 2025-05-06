package co.edu.uniquindio.poo.billeteravirtual.entidades;

import co.edu.uniquindio.poo.billeteravirtual.decoradores.PresupuestoBase;

public class Presupuesto implements PresupuestoBase, Cloneable{
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

    @Override
    public double getMontoTotal() {
        return montoTotal;
    }

    @Override
    public double getMontoGastado() {
        return montoGastado;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void agregarGasto(double monto) {
        this.montoGastado += monto;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
    }

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

