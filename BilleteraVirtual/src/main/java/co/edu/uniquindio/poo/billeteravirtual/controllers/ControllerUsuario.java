package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Admin;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.*;
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
        this.servicioUsuario = ServicioUsuario.getInstancia();
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

        if (camposVacios(nombre,correo,telefono,cedula,palabraClave,clave,verificarClave)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Por favor llene todos los campos");
            return;
        }

        if(mostrarErrorSi(clave == null || !clave.equals(verificarClave),"Las contraseñas no coinciden")) return;

        servicioUsuario.registrarUsuario(nombre,correo,telefono,cedula,palabraClave,clave);
        limpiarCampos();

        view.getPonerContrasena().setVisible(false);

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

        boolean autenticado = servicioUsuario.autenticarUsuario(cedula, clave);
        if (autenticado) {
            Usuario usuarioEncontrado = servicioUsuario.obtenerUsuario(cedula);
            Sesion.getInstancia().iniciarSesion(usuarioEncontrado);
        } else{
            Logger.getInstance().mostrarToast(view.rootPane, "Clave y/o Cedula incorrecta");
        }

        return autenticado;
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

    public void cambiarEscenaAdmin(Stage stage, String rutaFXML) {
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

    public void iniciarDatos(){
        if (!servicioUsuario.verificarExistenciaUsuario("1028")) {
            Usuario usuario = new Usuario.UsuarioBuilder()
                    .Nombre("Andres")
                    .Cedula("1028")
                    .Telefono("321")
                    .Correo("andres@gmail.com")
                    .PalabraClave("Pantera")
                    .ClaveAcceso("1021")
                    .build();
            Usuario usuario1 = new Usuario.UsuarioBuilder()
                    .Nombre("Pablo")
                    .Cedula("1092")
                    .Telefono("123")
                    .Correo("juan@gmail.com")
                    .PalabraClave("marcelo")
                    .ClaveAcceso("1234")
                    .build();
            Admin admin = (Admin) new Admin.AdminBuilder()
                    .Nombre("admin")
                    .Cedula("123")
                    .Telefono("40029592")
                    .Correo("admin@gmail.com")
                    .PalabraClave("admin")
                    .ClaveAcceso("123")
                    .build();

            servicioUsuario.getUsuariosRegistrados().add(admin);

            servicioUsuario.getUsuariosRegistrados().add(usuario);
            servicioUsuario.getUsuariosRegistrados().add(usuario1);
        }
    }

    private void limpiarCampos(){
        view.getTextNombre().clear();
        view.getTextCorreo().clear();
        view.getTextTelefono().clear();
        view.getTextCedula().clear();
        view.getTextPalabraClave().clear();
        view.getClave().clear();
        view.getVerificarClave().clear();
    }

    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }

    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}

