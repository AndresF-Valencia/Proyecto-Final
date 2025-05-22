package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

/**
 * Clase que representa a un administrador del sistema, heredando de Usuario.
 */
public class Admin extends Usuario {

    /**
     * Constructor que crea un administrador usando el patrón Builder.
     * @param builder Instancia del constructor AdminBuilder.
     */
    public Admin(AdminBuilder builder) {
        super(builder);
    }

    /**
     * Builder específico para crear instancias de Admin.
     */
    public static class AdminBuilder extends UsuarioBuilder {

        /**
         * Construye una instancia de Admin.
         * @return nueva instancia de Admin.
         */
        public Admin build() {
            return new Admin(this);
        }
    }
}
