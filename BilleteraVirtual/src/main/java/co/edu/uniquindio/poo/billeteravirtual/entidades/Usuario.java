package co.edu.uniquindio.poo.billeteravirtual.entidades;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String palabraclave;
    private String claveAcceso;
    private ArrayList<Transaccion> transacciones;

    public Usuario(UsuarioBuilder builder) {
        this.nombre = builder.nombre;
        this.cedula = builder.cedula;
        this.correo = builder.correo;
        this.telefono = builder.telefono;
        this.palabraclave = builder.palabraclave;
        this.claveAcceso = builder.claveAcceso;
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

    public String getPalabraclave() {
        return palabraclave;
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
        private String palabraclave;
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

        public UsuarioBuilder PalabraClave(String palabraclave) {
            this.palabraclave = palabraclave;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", palabraclave='" + palabraclave + '\'' +
                ", claveAcceso='" + claveAcceso + '\'' +
                ", transacciones=" + transacciones +
                '}';
    }
}