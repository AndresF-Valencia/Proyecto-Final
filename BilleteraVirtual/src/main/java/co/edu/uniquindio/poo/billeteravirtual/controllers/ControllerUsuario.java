package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewUsuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuario {
    private String codigoGenerado;
    private final ViewUsuario view;
    private final ServicioUsuario servicioUsuario;// Lista persistente de usuarios

    public ControllerUsuario(ViewUsuario view) {
        this.view = view;
        this.servicioUsuario = new ServicioUsuario();
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

        servicioUsuario.registrarUsuario(nombre,correo,telefono,cedula,palabraClave,clave);

        Logger.getInstance().mostrarToast(view.rootPane, "Usuario registrado con exito");
        cambiarVista("Bienvenida");
    }

    public void continuar() {
        view.getDatosUsuario().setVisible(false);
        view.getPonerContrasena().setVisible(true);
    }

    public boolean iniciarSesion() {
        String cedula = view.getCedulaIS().getText();
        String clave = view.getClaveIS().getText();
        Usuario usuarioEncontrado = servicioUsuario.obtenerUsuario(cedula);
        if (usuarioEncontrado != null) {
            Sesion.setUsuarioActual(usuarioEncontrado);
        }

        return servicioUsuario.autenticarUsuario(cedula, clave);
    }

    public void verificarPalabraClave(){
        String palabraclave = view.getTextRecuperar().getText();
        for (Usuario u : servicioUsuario.getUsuariosRegistrados()) {
            String palabra = u.getPalabraclave();
            if(palabra.equals(palabraclave)){
                generarCodigoRecuperacion();
                view.getPanePalabraClave().setVisible(false);
                view.getPaneCodigo().setVisible(true);
                break;
            }
        }
    }

    public boolean verificarCodigo() {
        String codigoIngresado = view.getTextCodigo().getText();
        if (codigoGenerado != null && codigoGenerado.equals(codigoIngresado)) {
            Logger.getInstance().mostrarToast(view.rootPane,"✅ Código correcto.");
            return true;
        } else {
            Logger.getInstance().mostrarToast(view.rootPane,"❌ Código incorrecto.");
        }
        return false;
    }

    public void generarCodigoRecuperacion(){
        codigoGenerado = new GeneradorCodigo().generarCodigo();
        Logger.getInstance().mostrarToast(view.rootPane ,"Codigo de recuperacion: " + codigoGenerado);
    }

    public void cambiarEscena(Stage stage, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            fadeIn.play();
        } catch (IOException e) {
            Logger.getInstance().mostrarToast(view.rootPane, "Cedula y/o clave incorrecta");
        }
    }


}

