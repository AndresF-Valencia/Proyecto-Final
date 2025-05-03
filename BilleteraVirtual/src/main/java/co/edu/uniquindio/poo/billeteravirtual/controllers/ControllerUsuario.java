package co.edu.uniquindio.poo.billeteravirtual.controllers;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewUsuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuario {
    private String codigoGenerado;
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
        view.getRecuperar().setVisible(vistaActiva.equals("RecuperarUsuario"));
    }

    public void mostrarRegistro() {
        cambiarVista("RegistroUsuario");
        view.getDatosUsuario().setVisible(true);
    }

    public void mostrarRecuperacion() {
        cambiarVista("RecuperarUsuario");
        view.getPanePalabraClave().setVisible(true);
    }

    public void mostrarInicioSesion() {
        cambiarVista("IniciarSesion");
    }

    public void finalizarRegistro() {
        String nombre = view.getTextNombre().getText();
        String correo = view.getTextCorreo().getText();
        String telefono = view.getTextTelefono().getText();
        String cedula = view.getTextCedula().getText();
        String palabraClave = view.getTextPalabraClave().getText();
        String clave = view.getClave().getText();
        String verificarClave = view.getVerificarClave().getText();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || palabraClave.isEmpty()) {
            Logger.getInstance().mostrarToast(view.rootPane, "Por favor llene todos los campos");
            return;
        }

        if (clave == null || !clave.equals(verificarClave)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Las contraseñas no coinciden");
            return;
        }

        Usuario usuario = new Usuario.UsuarioBuilder()
                .Nombre(nombre)
                .Cedula(cedula)
                .Telefono(telefono)
                .Correo(correo)
                .PalabraClave(palabraClave)
                .ClaveAcceso(clave)
                .build();

        usuariosRegistrados.add(usuario);
        System.out.println("Usuario registrado: " + usuario);
        Logger.getInstance().mostrarToast(view.rootPane, "Usuario registrado con exito");
        cambiarVista("Bienvenida");
    }

    public void continuar() {
        view.getDatosUsuario().setVisible(false);
        view.getPonerContrasena().setVisible(true);
    }

    public void iniciarSesion() {
        String cedula = view.getCedulaIS().getText();
        String clave = view.getClaveIS().getText();

        for (Usuario u : usuariosRegistrados) {
            String cedulaUsuario = u.getCedula();
            String claveUsuario = u.getClaveAcceso();
            if (cedulaUsuario.equals(cedula)) {
                if (claveUsuario.equals(clave)) {
                    //cambiar esto despues
                    cambiarVista("Bienvenida");
                }
            }
        }
    }

    public void verificarPalabraClave(){
        String palabraclave = view.getTextRecuperar().getText();
        for (Usuario u : usuariosRegistrados) {
            String palabra = u.getPalabraclave();
            if(palabra.equals(palabraclave)){
                generarCodigoRecuperacion();
                view.getPanePalabraClave().setVisible(false);
                view.getPaneCodigo().setVisible(true);
                break;
            }
        }
    }

    public void verificarCodigo() {
        String codigoIngresado = view.getTextCodigo().getText();
        if (codigoGenerado != null && codigoGenerado.equals(codigoIngresado)) {
            Logger.getInstance().mostrarToast(view.rootPane,"✅ Código correcto.");
        } else {
            Logger.getInstance().mostrarToast(view.rootPane,"❌ Código incorrecto.");
        }
    }

    public void generarCodigoRecuperacion(){
        codigoGenerado = new GeneradorCodigo().generarCodigo();
        Logger.getInstance().mostrarToast(view.rootPane ,"Codigo de recuperacion: " + codigoGenerado);
    }

    public List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

}

