package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerCuenta;
import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerDatos;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ViewFuncionalidades {
    @FXML
    public Button btnMeterDinero, btnSacarDinero, btnPasarDinero, btnVerMovimientos,
            btnVerDatos, btnCambiarContrasena, btnActualizarDatos, btnAgregarCuenta,
            btnGestionarCuentas, btnCerrarSesion, btnRegistrarCuenta,
            btnConsultarCuenta, btnEliminarCuenta, btnRegresar,
            btnGuardarCambio, btnModificarDatos, btnVolverDatosUsuario,btnGuardarCambioClave, btnVolverCambioClave;

    @FXML
    public Text bienvenidaText, textTitular, textNumeroCuenta, textTipoCuenta, textSaldo,
            textNombreUsuario, textCorreoUsuario, textDocumentoUsuario, textTelefonoUsuario;

    @FXML
    public TextField campoTitular, campoNumeroCuenta,
            txtNombreEditar, txtCorreoEditar, txtTelefonoEditar,txtPalabraClaveActual, txtNuevaPalabraClave;


    @FXML
    public ComboBox<String> comboTipoCuenta;

    @FXML
    public ComboBox<Cuenta> comboCuentas;

    @FXML
    public AnchorPane anchorPaneRegistroCuenta, anchorPaneGestionarCuenta, rootPane, anchorPaneVerDatosUsuario, anchorPaneCambiarContrasena;

    @FXML
    public PasswordField pfClaveActual, pfNuevaClave, pfConfirmarClave;

    @FXML
    public Pane camposInformacion, paneEditarInformacion;
    private ControllerCuenta controllerCuenta;

    @FXML
    public void initialize() {
        try {
            System.out.println("Inicializando ViewFuncionalidades");
            controllerCuenta = new ControllerCuenta(this);;
            controllerCuenta.cargarCuentas();

            //Controller datos
            ControllerDatos controllerDatos = new ControllerDatos(this);

            anchorPaneRegistroCuenta.setVisible(false);
            anchorPaneGestionarCuenta.setVisible(false);
            anchorPaneVerDatosUsuario.setVisible(false);
            anchorPaneCambiarContrasena.setVisible(false);

            // Eventos ControllerCuenta
            comboTipoCuenta.getItems().addAll("Cuenta de ahorros", "Cuenta corriente");
                btnAgregarCuenta.setOnAction(e -> controllerCuenta.agregarCuenta());
            btnRegistrarCuenta.setOnAction(e -> controllerCuenta.registrarCuenta());
            btnGestionarCuentas.setOnAction(e -> controllerCuenta.gestionarCuentas());
            btnConsultarCuenta.setOnAction(e -> controllerCuenta.consultarCuenta());
            btnEliminarCuenta.setOnAction(e -> controllerCuenta.eliminarCuenta());
            btnRegresar.setOnAction(e -> controllerCuenta.regresar());
            btnCerrarSesion.setOnAction(e -> controllerCuenta.cerrarSesion());

            //Eventos controllerDatos
            btnVerDatos.setOnAction(e -> controllerDatos.verDatosUsuario());
            btnModificarDatos.setOnAction(e -> controllerDatos.mostrarPanelEditarDatos());
            btnVolverDatosUsuario.setOnAction(e -> controllerDatos.volver());
            btnGuardarCambio.setOnAction(e -> controllerDatos.editarDatosUsuario());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Button getBtnMeterDinero() {
        return btnMeterDinero;
    }

    public ViewFuncionalidades setBtnMeterDinero(Button btnMeterDinero) {
        this.btnMeterDinero = btnMeterDinero;
        return this;
    }

    public TextField getCampoTitular() {
        return campoTitular;
    }

    public ViewFuncionalidades setCampoTitular(TextField campoTitular) {
        this.campoTitular = campoTitular;
        return this;
    }

    public TextField getCampoNumeroCuenta() {
        return campoNumeroCuenta;
    }

    public ViewFuncionalidades setCampoNumeroCuenta(TextField campoNumeroCuenta) {
        this.campoNumeroCuenta = campoNumeroCuenta;
        return this;
    }

    public ComboBox<String> getComboTipoCuenta() {
        return comboTipoCuenta;
    }

    public ViewFuncionalidades setComboTipoCuenta(ComboBox<String> comboTipoCuenta) {
        this.comboTipoCuenta = comboTipoCuenta;
        return this;
    }

    public ComboBox<Cuenta> getComboCuentas() {
        return comboCuentas;
    }

    public ViewFuncionalidades setComboCuentas(ComboBox<Cuenta> comboCuentas) {
        this.comboCuentas = comboCuentas;
        return this;
    }

    public Button getBtnSacarDinero() {
        return btnSacarDinero;
    }

    public TextField getTxtCorreoEditar() {
        return txtCorreoEditar;
    }

    public TextField getTxtTelefonoEditar() {
        return txtTelefonoEditar;
    }
}
