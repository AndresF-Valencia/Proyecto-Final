package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Admin;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewUsuario {
    @FXML
    public Button buttonRegistro, buttonIS, ingresar, OlvidarClave, buttonContinuar, buttonFinalizar, enviarCodigo, iniciarSesionPorRecuperacion;
    @FXML
    public TextField CedulaIS, ClaveIS, textNombre, textCedula, textCorreo, textTelefono, textPalabraClave,textRecuperar, textCodigo;
    @FXML
    public AnchorPane rootPane, IniciarSesion, Bienvenida, RegistroUsuario, recuperar;
    @FXML
    public Pane datosUsuario, PonerContrasena, panePalabraClave, paneCodigo;
    @FXML
    public PasswordField clave, verificarClave;

    private ControllerUsuario controllerUsuario;

    @FXML
    public void initialize() {
        controllerUsuario = new ControllerUsuario(this);
        controllerUsuario.iniciarDatos();


        Bienvenida.setVisible(true);
        IniciarSesion.setVisible(false);
        RegistroUsuario.setVisible(false);
        datosUsuario.setVisible(false);
        PonerContrasena.setVisible(false);
        recuperar.setVisible(false);
        panePalabraClave.setVisible(false);
        paneCodigo.setVisible(false);


        buttonRegistro.setOnAction(e -> controllerUsuario.mostrarRegistro());
        buttonIS.setOnAction(e -> controllerUsuario.mostrarInicioSesion());
        buttonFinalizar.setOnAction(e -> controllerUsuario.finalizarRegistro());
        buttonContinuar.setOnAction(e -> controllerUsuario.continuar());
        ingresar.setOnAction(e -> {

            boolean valido = controllerUsuario.iniciarSesion();

            if (valido) {
                Usuario usuario = Sesion.getInstancia().getUsuarioActual();
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                if (usuario instanceof Admin) {
                    controllerUsuario.cambiarEscenaAdmin(stage, "/co/edu/uniquindio/poo/billeteravirtual/interfazAdmin.fxml");
                } else {
                    controllerUsuario.cambiarEscena(stage, "/co/edu/uniquindio/poo/billeteravirtual/interfazFuncionalidades.fxml");
                }
            }

        });
        OlvidarClave.setOnAction(e -> controllerUsuario.mostrarRecuperacion());
        enviarCodigo.setOnAction(e -> controllerUsuario.verificarPalabraClave() );
        iniciarSesionPorRecuperacion.setOnAction(e -> {
            boolean valido = controllerUsuario.verificarCodigo();

            if (valido) {
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                controllerUsuario.cambiarEscena(stage, "/co/edu/uniquindio/poo/billeteravirtual/interfazFuncionalidades.fxml");
            }
        });
    }


    public Button getButtonRegistro() {
        return buttonRegistro;
    }

    public Button getButtonIS() {
        return buttonIS;
    }

    public Button getIngresar() {
        return ingresar;
    }

    public Button getOlvidarClave() {
        return OlvidarClave;
    }

    public Button getButtonContinuar() {
        return buttonContinuar;
    }

    public Button getButtonFinalizar() {
        return buttonFinalizar;
    }

    public TextField getCedulaIS() {
        return CedulaIS;
    }

    public TextField getClaveIS() {
        return ClaveIS;
    }

    public TextField getTextNombre() {
        return textNombre;
    }

    public TextField getTextCedula() {
        return textCedula;
    }

    public TextField getTextCorreo() {
        return textCorreo;
    }

    public TextField getTextTelefono() {
        return textTelefono;
    }

    public AnchorPane getIniciarSesion() {
        return IniciarSesion;
    }

    public AnchorPane getBienvenida() {
        return Bienvenida;
    }

    public AnchorPane getRegistroUsuario() {
        return RegistroUsuario;
    }

    public Pane getDatosUsuario() {
        return datosUsuario;
    }

    public Pane getPonerContrasena() {
        return PonerContrasena;
    }

    public PasswordField getClave() {
        return clave;
    }

    public PasswordField getVerificarClave() {
        return verificarClave;
    }

    public TextField getTextPalabraClave(){
        return textPalabraClave;
    }

    public TextField getTextRecuperar() {
        return textRecuperar;
    }

    public TextField getTextCodigo() {
        return textCodigo;
    }

    public Button getEnviarCodigo() {
        return enviarCodigo;
    }

    public AnchorPane getRecuperar() {
        return recuperar;
    }

    public Pane getPanePalabraClave() {
        return panePalabraClave;
    }

    public Pane getPaneCodigo() {
        return paneCodigo;
    }
}
