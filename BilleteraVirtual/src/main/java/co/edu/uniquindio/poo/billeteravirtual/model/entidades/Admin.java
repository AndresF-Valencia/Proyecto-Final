package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

public class Admin extends Usuario {
    public Admin(AdminBuilder builder) {
        super(builder);
    }

    public static class AdminBuilder extends UsuarioBuilder {

        @Override
        public AdminBuilder Nombre(String nombre) {
            super.Nombre(nombre);
            return this;
        }

        @Override
        public AdminBuilder Cedula(String cedula) {
            super.Cedula(cedula);
            return this;
        }

        @Override
        public AdminBuilder Correo(String correo) {
            super.Correo(correo);
            return this;
        }

        @Override
        public AdminBuilder Telefono(String telefono) {
            super.Telefono(telefono);
            return this;
        }

        @Override
        public AdminBuilder PalabraClave(String palabraclave) {
            super.PalabraClave(palabraclave);
            return this;
        }

        @Override
        public AdminBuilder ClaveAcceso(String claveAcceso) {
            super.ClaveAcceso(claveAcceso);
            return this;
        }

        @Override
        public Admin build() {
            return new Admin(this);
        }
    }
}

