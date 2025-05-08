package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerCuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ViewFuncionalidades {
    @FXML
    public Button btnMeterDinero, btnSacarDinero, btnPasarDinero, btnVerMovimientos,
            btnVerDatos, btnCambiarContrasena, btnActualizarDatos, btnAgregarCuenta,
            btnGestionarCuentas, btnCerrarSesion, btnRegistrarCuenta,
            btnConsultarCuenta, btnEliminarCuenta, btnRegresar;

    @FXML
    public Text bienvenidaText, textTitular, textNumeroCuenta, textTipoCuenta, textSaldo;

    @FXML
    public TextField campoTitular, campoNumeroCuenta;

    @FXML
    public ComboBox<String> comboTipoCuenta;
    @FXML
    public ComboBox<Cuenta> comboCuentas;

    @FXML
    public AnchorPane anchorPaneRegistroCuenta, anchorPaneGestionarCuenta, rootPane;

    private ControllerCuenta controllerCuenta;

    @FXML
    public void initialize() {
        try {
            controllerCuenta = new ControllerCuenta(this);;
            controllerCuenta.cargarCuentas();

            anchorPaneRegistroCuenta.setVisible(false);
            anchorPaneGestionarCuenta.setVisible(false);

            comboTipoCuenta.getItems().addAll("Cuenta de ahorros", "Cuenta corriente");
            btnAgregarCuenta.setOnAction(e -> controllerCuenta.agregarCuenta());
            btnRegistrarCuenta.setOnAction(e -> controllerCuenta.registrarCuenta());
            btnGestionarCuentas.setOnAction(e -> controllerCuenta.gestionarCuentas());
            btnConsultarCuenta.setOnAction(e -> controllerCuenta.consultarCuenta());
            btnEliminarCuenta.setOnAction(e -> controllerCuenta.eliminarCuenta());
            btnRegresar.setOnAction(e -> controllerCuenta.regresar());
            btnCerrarSesion.setOnAction(e -> controllerCuenta.cerrarSesion());
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
}
