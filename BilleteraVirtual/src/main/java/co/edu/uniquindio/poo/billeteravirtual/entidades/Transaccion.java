package co.edu.uniquindio.poo.billeteravirtual.entidades;

import java.util.Date;

public class Transaccion {
    private int idTransaccion;
    private Date fecha;
    private String tipo;
    private double monto;
    private String descripcion;

    //Constructor de Transacción
    public Transaccion(int idTransaccion, Date fecha, String tipo, double monto, String descripcion) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
    }
    //Getters y Setters de Transacción
    public int getIdTransaccion() {
        return idTransaccion;
    }
    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Metodo ToString para Transacción
    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion=" + idTransaccion +
                ", fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
