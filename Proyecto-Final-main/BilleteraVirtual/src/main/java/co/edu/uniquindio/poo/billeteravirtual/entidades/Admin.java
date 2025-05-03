package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class Admin extends Usuario {
    public Admin(AdminBuilder builder) {
        super(builder);
    }

    public static class AdminBuilder extends UsuarioBuilder {
        public Admin build() {
            return new Admin(this);
        }
    }
}
