package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import co.edu.uniquindio.poo.billeteravirtual.model.estrategia.EstrategiaTransaccion;

import java.time.LocalDate;
import java.util.Date;

public class Transaccion{
    private String idTransaccion;
    private LocalDate fecha;
    private String tipo;
    private double monto;
    private String descripcion;
    private String cuentaOrigen;
    private String cuentaDestino;
    private EstrategiaTransaccion estrategia;
    public static final String CUENTAEXTERNA = "Corresponsal Bancario";

    //Constructor de Transacción
    public Transaccion(String idTransaccion, LocalDate fecha, String tipo, double monto, String descripcion,String cuentaOrigen, String cuentaDestino) {
        this.idTransaccion = idTransaccion;
        this.fecha = LocalDate.now();
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }
    //Getters y Setters de Transacción
    public String getCuentaOrigen() {
        return cuentaOrigen;
    }
    public Transaccion setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
        return this;
    }
    public String getIdTransaccion() {
        return idTransaccion;
    }
    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
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

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public Transaccion setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
        return this;
    }

    public void setEstrategia(EstrategiaTransaccion estrategia) {
        this.estrategia = estrategia;
    }


    public void ejecutar() {
        if (estrategia != null) {
            estrategia.procesar(this);
        }
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion='" + idTransaccion + '\'' +
                ", fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", cuentaOrigen='" + cuentaOrigen + '\'' +
                ", cuentaDestino='" + cuentaDestino + '\'' +
                '}';
    }
}
