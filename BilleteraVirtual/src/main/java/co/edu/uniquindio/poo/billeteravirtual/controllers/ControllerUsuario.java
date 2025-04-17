package co.edu.uniquindio.poo.billeteravirtual.controllers;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewUsuario;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuario {
    private final ViewUsuario view;
    private List<Usuario> usuariosRegistrados; // Lista persistente de usuarios

    public ControllerUsuario(ViewUsuario view) {
        this.view = view;
        this.usuariosRegistrados = new ArrayList<>();
    }

    private void cambiarVista(String vistaActiva) {
        view.getBienvenida().setVisible(vistaActiva.equals("Bienvenida"));
        view.getIniciarSesion().setVisible(vistaActiva.equals("IniciarSesion"));
        view.getRegistroUsuario().setVisible(vistaActiva.equals("RegistroUsuario"));
    }

    public void mostrarRegistro() {
        cambiarVista("RegistroUsuario");
        view.getDatosUsuario().setVisible(true);
    }

    public void mostrarInicioSesion() {
        cambiarVista("IniciarSesion");
    }

    public void finalizarRegistro() {
        String nombre = view.getTextNombre().getText();
        String correo = view.getTextCorreo().getText();
        String telefono = view.getTextTelefono().getText();
        String cedula = view.getTextCedula().getText();
        String clave = view.getClave().getText();
        String verificarClave = view.getVerificarClave().getText();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty()) {
            System.out.println("Por favor, complete todos los campos.");
            return;
        }

        if (clave == null || !clave.equals(verificarClave)) {
            System.out.println("Las contraseñas no coinciden.");
            return;
        }

        Usuario usuario = new Usuario.UsuarioBuilder()
                .Nombre(nombre)
                .Cedula(cedula)
                .Telefono(telefono)
                .Correo(correo)
                .ClaveAcceso(clave)
                .build();

        usuariosRegistrados.add(usuario);
        System.out.println("Usuario registrado: " + usuario);
        cambiarVista("Bienvenida");
    }

    public void continuar() {
        view.getDatosUsuario().setVisible(false);
        view.getPonerContrasena().setVisible(true);
    }

    public List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }
}

