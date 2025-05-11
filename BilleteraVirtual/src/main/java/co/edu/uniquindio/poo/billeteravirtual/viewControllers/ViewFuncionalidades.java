package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.*;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
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
    public Button btnMeterDinero, btnSacarDinero, btnPasarDinero, btnInicio, btnPasar, btnRegresar12,
            btnVerDatos, btnCambiarContrasena, btnAgregarCuenta, btnAgregar, btnGenerarCodigo,btnRegresar11,
            btnGestionarCuentas, btnCerrarSesion, btnRegistrarCuenta,
            btnConsultarCuenta, btnEliminarCuenta, btnRegresar, btnRegresar1,
            btnGuardarCambios, btnModificarDatos, btnVolverDatosUsuario,btnGuardarCambioClave, btnVolverCambioClave, btnVermas;

    @FXML
    public Text bienvenidaText, textTitular, textNumeroCuenta, textTipoCuenta, textSaldo,txtSaldoPrincipal,
            textNombreUsuario, textCorreoUsuario, textDocumentoUsuario, textTelefonoUsuario;

    @FXML
    public TextField campoTitular, cantidadIngresar2, numeroCuenta, campoNumeroCuenta, cantidadIngresar, cantidadIngresar1,
            txtNombreEditar, txtCorreoEditar, txtTelefonoEditar,txtPalabraClaveActual, txtNuevaPalabraClave;


    @FXML
    public ComboBox<String> comboTipoCuenta;

    @FXML
    public ComboBox<Cuenta> comboCuentas;

    @FXML
    public ComboBox<Cuenta> comboSelecionCuenta;

    @FXML
    public ComboBox<Cuenta> comboSelecionCuenta1;

    @FXML
    public ComboBox<Cuenta> comboSelecionCuenta2;

    @FXML
    public AnchorPane anchorPanePrincipal,anchorPaneRegistroCuenta, anchorPaneGestionarCuenta, rootPane, anchorPaneVerDatosUsuario, anchorPaneCambiarContrasena;

    @FXML
    public PasswordField pfClaveActual, pfNuevaClave, pfConfirmarClave;

    @FXML
    public Pane camposInformacion, paneEditarInformacion, PaneMeterDinero , PaneSacarDinero, panePasarDinero;

    private ControllerCuenta controllerCuenta;
    private ControllerDatos controllerDatos;
    private ControllerPrincipal controllerPrincipal;
    private ControllerMeterDinero controllerMeterDinero;
    private ControllerSacarDinero controllerSacarDinero;
    private ControllerPasarDinero controllerPasarDinero;

    @FXML
    public void initialize() {
        try {
            controllerCuenta = new ControllerCuenta(this);;
            controllerDatos = new ControllerDatos(this);
            controllerPrincipal = new ControllerPrincipal(this);
            controllerMeterDinero = new ControllerMeterDinero(this);
            controllerSacarDinero = new ControllerSacarDinero(this);
            controllerPasarDinero = new ControllerPasarDinero(this);

            controllerPrincipal.saldoPrincipal();
            controllerCuenta.cargarCuentas();

            anchorPanePrincipal.setVisible(true);
            anchorPaneRegistroCuenta.setVisible(false);
            anchorPaneGestionarCuenta.setVisible(false);
            anchorPaneVerDatosUsuario.setVisible(false);
            anchorPaneCambiarContrasena.setVisible(false);
            PaneMeterDinero.setVisible(false);
            PaneSacarDinero.setVisible(false);
            panePasarDinero.setVisible(false);



            // Eventos ControllerCuenta
            comboTipoCuenta.getItems().addAll("Cuenta de ahorros", "Cuenta corriente");
            btnInicio.setOnAction(e -> controllerCuenta.Inicio());
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
            btnVolverDatosUsuario.setOnAction(e -> controllerCuenta.regresar());
            btnGuardarCambios.setOnAction(e -> controllerDatos.editarDatosUsuario());
            btnCambiarContrasena.setOnAction(e -> controllerDatos.mostrarCambiarContrasena());
            btnGuardarCambioClave.setOnAction(e -> controllerDatos.guardarCambioClave());
            btnVolverCambioClave.setOnAction(e -> controllerCuenta.regresar());

            //Eventos controllerPrincipal

            //Eventos ControllerMeterDinero
            btnMeterDinero.setOnAction(e -> controllerMeterDinero.iniciarVista());
            btnRegresar1.setOnAction(e -> controllerMeterDinero.restaurarVistaPrincipal());
            btnAgregar.setOnAction(event -> controllerMeterDinero.agregarDinero());

            //Evento ControllerSacardinero
            btnSacarDinero.setOnAction(e -> controllerSacarDinero.iniciarVista());
            btnGenerarCodigo.setOnAction(e -> controllerSacarDinero.retirarDinero());
            btnRegresar11.setOnAction(e-> controllerSacarDinero.restaurarVistaPrincipal());

            //Evento ControllerPasarDinero
            btnPasarDinero.setOnAction(e -> controllerPasarDinero.iniciarVista());
            btnRegresar12.setOnAction(e -> controllerPasarDinero.restaurarVistaPrincipal());
            btnPasar.setOnAction(e -> controllerPasarDinero.pasarDinero());



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

    public TextField getTxtNombreEditar() {
        return txtNombreEditar;
    }

    public Button getBtnPasarDinero() {
        return btnPasarDinero;
    }

    public TextField getTxtPalabraClaveActual() {
        return txtPalabraClaveActual;
    }

    public TextField getTxtNuevaPalabraClave() {
        return txtNuevaPalabraClave;
    }

    public PasswordField getPfClaveActual() {
        return pfClaveActual;
    }

    public PasswordField getPfNuevaClave() {
        return pfNuevaClave;
    }

    public PasswordField getPfConfirmarClave() {
        return pfConfirmarClave;
    }

    public Text getTxtSaldoPrincipal() {
        return txtSaldoPrincipal;
    }
}
