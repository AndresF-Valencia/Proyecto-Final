package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import java.util.Date;

public class Transaccion implements TransaccionClonable{
    private String idUsuario;
    private String idTransaccion;
    private Date fecha;
    private String tipo;
    private double monto;
    private String descripcion;

    //Constructor de Transacción
    public Transaccion(String idUsuario, String idTransaccion, Date fecha, String tipo, double monto, String descripcion) {
        this.idUsuario = idUsuario;
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
    }
    //Getters y Setters de Transacción
    public String getIdUsuario() {
        return idUsuario;
    }
    public Transaccion setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }
    public String getIdTransaccion() {
        return idTransaccion;
    }
    public void setIdTransaccion(String idTransaccion) {
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

    @Override
    public Transaccion clonar() {
        try {
            return (Transaccion) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar transacción", e);
        }
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
