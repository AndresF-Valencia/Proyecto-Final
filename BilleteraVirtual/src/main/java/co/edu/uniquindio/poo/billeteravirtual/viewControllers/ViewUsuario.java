package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ViewUsuario {
    @FXML
    public Button buttonRegistro, buttonIS, ingresar, OlvidarClave, buttonContinuar, buttonFinalizar;
    @FXML
    public TextField CedulaIS, ClaveIS, textNombre, textCedula, textCorreo, textTelefono;
    @FXML
    public AnchorPane IniciarSesion, Bienvenida, RegistroUsuario;
    @FXML
    public Pane datosUsuario, PonerContrasena;
    @FXML
    public PasswordField clave, verificarClave;

    private ControllerUsuario controllerUsuario;

    @FXML
    public void initialize() {
        controllerUsuario = new ControllerUsuario(this);


        Bienvenida.setVisible(true);
        IniciarSesion.setVisible(false);
        RegistroUsuario.setVisible(false);
        datosUsuario.setVisible(false);
        PonerContrasena.setVisible(false);


        buttonRegistro.setOnAction(e -> controllerUsuario.mostrarRegistro());
        buttonIS.setOnAction(e -> controllerUsuario.mostrarInicioSesion());
        buttonFinalizar.setOnAction(e -> controllerUsuario.finalizarRegistro());
        buttonContinuar.setOnAction(e -> controllerUsuario.continuar());
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
}
