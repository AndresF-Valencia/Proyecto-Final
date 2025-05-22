package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

import java.util.ArrayList;

/**
 * Representa un usuario dentro del sistema de billetera virtual.
 * Un usuario puede tener múltiples cuentas asociadas y contiene información personal
 * y credenciales de acceso.
 */
public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String palabraclave;
    private String claveAcceso;
    private ArrayList<Cuenta> cuentas;

    /**
     * Constructor privado usado por el {@link UsuarioBuilder} para crear una instancia de Usuario.
     *
     * @param builder objeto UsuarioBuilder que contiene los datos del usuario.
     */
    public Usuario(UsuarioBuilder builder) {
        this.nombre = builder.nombre;
        this.cedula = builder.cedula;
        this.correo = builder.correo;
        this.telefono = builder.telefono;
        this.palabraclave = builder.palabraclave;
        this.claveAcceso = builder.claveAcceso;
        this.cuentas = new ArrayList<>();
    }

    /**
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Cédula del usuario.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @return Correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @return Número de teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @return Palabra clave de seguridad del usuario (para recuperación de cuenta).
     */
    public String getPalabraclave() {
        return palabraclave;
    }

    /**
     * @return Clave de acceso del usuario.
     */
    public String getClaveAcceso() {
        return claveAcceso;
    }

    /**
     * Establece un nuevo nombre para el usuario.
     * @param nombre Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece un nuevo correo electrónico para el usuario.
     * @param correo Correo del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Establece un nuevo número de teléfono para el usuario.
     * @param telefono Teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Establece una nueva cédula para el usuario.
     * @param cedula Cédula del usuario.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Establece una nueva palabra clave para el usuario.
     * @param palabraclave Palabra clave del usuario.
     */
    public void setPalabraclave(String palabraclave) {
        this.palabraclave = palabraclave;
    }

    /**
     * Establece una nueva clave de acceso para el usuario.
     * @param claveAcceso Clave de acceso del usuario.
     */
    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    /**
     * @return Lista de cuentas asociadas al usuario.
     */
    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     * Asigna la lista completa de cuentas al usuario.
     * @param cuentas Lista de cuentas.
     * @return El mismo objeto Usuario (para encadenar métodos).
     */
    public Usuario setCuentas(ArrayList<Cuenta> cuentas) {
        this.cuentas = cuentas;
        return this;
    }

    /**
     * Clase interna que implementa el patrón Builder para crear objetos Usuario de manera flexible.
     */
    public static class UsuarioBuilder {
        private String nombre;
        private String cedula;
        private String correo;
        private String telefono;
        private String palabraclave;
        private String claveAcceso;

        /**
         * Establece el nombre del usuario.
         * @param nombre Nombre del usuario.
         * @return El mismo builder.
         */
        public UsuarioBuilder Nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        /**
         * Establece la cédula del usuario.
         * @param cedula Cédula del usuario.
         * @return El mismo builder.
         */
        public UsuarioBuilder Cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        /**
         * Establece el correo electrónico del usuario.
         * @param correo Correo del usuario.
         * @return El mismo builder.
         */
        public UsuarioBuilder Correo(String correo) {
            this.correo = correo;
            return this;
        }

        /**
         * Establece el número de teléfono del usuario.
         * @param telefono Teléfono del usuario.
         * @return El mismo builder.
         */
        public UsuarioBuilder Telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        /**
         * Establece la palabra clave del usuario.
         * @param palabraclave Palabra clave del usuario.
         * @return El mismo builder.
         */
        public UsuarioBuilder PalabraClave(String palabraclave) {
            this.palabraclave = palabraclave;
            return this;
        }

        /**
         * Establece la clave de acceso del usuario.
         * @param claveAcceso Clave de acceso.
         * @return El mismo builder.
         */
        public UsuarioBuilder ClaveAcceso(String claveAcceso) {
            this.claveAcceso = claveAcceso;
            return this;
        }

        /**
         * Crea una instancia de Usuario con los datos establecidos.
         * @return Objeto Usuario construido.
         */
        public Usuario build(){
            return new Usuario(this);
        }
    }

    /**
     * Devuelve una representación en cadena del objeto Usuario.
     * @return Cadena con los atributos principales del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", palabraclave='" + palabraclave + '\'' +
                ", claveAcceso='" + claveAcceso + '\'' +
                '}';
    }
}
