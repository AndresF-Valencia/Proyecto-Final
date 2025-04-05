package co.edu.uniquindio.poo.billeteravirtual.entidades;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private double saldo;
    private String claveAcceso;
    private ArrayList<Transaccion> transacciones;

    public Usuario(UsuarioBuilder builder) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.saldo = 1000000.0;
        this.transacciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(ArrayList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public static class UsuarioBuilder {
        private String nombre;
        private String cedula;
        private String correo;
        private String telefono;
        private double saldo;
        private String claveAcceso;

        public UsuarioBuilder Nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UsuarioBuilder Cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public UsuarioBuilder Correo(String correo) {
            this.correo = correo;
            return this;
        }

        public UsuarioBuilder Telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public UsuarioBuilder Saldo(double saldo) {
            this.saldo = saldo;
            return this;
        }

        public UsuarioBuilder ClaveAcceso(String claveAcceso) {
            this.claveAcceso = claveAcceso;
            return this;
        }

        public Usuario build(){
            return new Usuario(this);
        }
    }

}

