package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.*;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.CategoriaProducto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Producto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ViewFuncionalidades {
    @FXML
    public Button btnMeterDinero, btnSacarDinero, btnPasarDinero, btnInicio, btnPasar, btnRegresar12, btnPagarTienda,
            btnVerDatos, btnCambiarContrasena, btnAgregarCuenta, btnAgregar, btnGenerarCodigo,btnRegresar11,
            btnGestionarCuentas, btnCerrarSesion, btnRegistrarCuenta, btnRegresarTienda, btnTienda,
            btnConsultarCuenta, btnEliminarCuenta,btnVolverTransaccion, btnRegresar, btnRegresar1,
            btnGuardarCambios, btnModificarDatos, btnVolverDatosUsuario,btnGuardarCambioClave, btnVolverCambioClave, btnVermas;

    @FXML
    public Text bienvenidaText, textTitular, textNumeroCuenta, textTipoCuenta, textSaldo,txtSaldoPrincipal,
            textNombreUsuario, textCorreoUsuario, textDocumentoUsuario, textTelefonoUsuario;

    @FXML
    public TextField campoTitular,cantidadIngresar2, numeroCuenta, campoNumeroCuenta, cantidadIngresar, cantidadIngresar1,
            txtNombreEditar, txtCorreoEditar, txtTelefonoEditar,txtPalabraClaveActual, txtNuevaPalabraClave, txtCantidad;


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
    public ComboBox<Cuenta> comboCuentaTienda;

    @FXML
    public ComboBox<CategoriaProducto> comboCategorias;

    @FXML
    public ComboBox<Producto> comboProductos;

    @FXML
    public TableView<Transaccion> tablaTransacciones;

    @FXML
    public TableColumn<Transaccion, String> columnaFecha;

    @FXML
    public TableColumn<Transaccion, String> columnaTipo;

    @FXML
    public TableColumn<Transaccion, Double> columnaMonto;

    @FXML
    public TableColumn<Transaccion, String> columnaDescripcion;

    @FXML
    public AnchorPane anchorPanePrincipal,anchorPaneRegistroCuenta, anchorPaneGestionarCuenta, rootPane, anchorPaneVerDatosUsuario, anchorPaneCambiarContrasena;

    @FXML
    public PasswordField pfClaveActual, pfNuevaClave, pfConfirmarClave;

    @FXML
    public Pane camposInformacion, paneEditarInformacion, PaneMeterDinero , PaneSacarDinero, panePasarDinero, PaneTienda, PanePrincipal, PaneVerMas;

    private ControllerCuenta controllerCuenta;
    private ControllerDatos controllerDatos;
    private ControllerTransacciones controllerTransacciones;
    private TiendaController tiendaController;

    @FXML
    public void initialize() {
        try {
            controllerCuenta = new ControllerCuenta(this);;
            controllerDatos = new ControllerDatos(this);
            controllerTransacciones = new ControllerTransacciones(this);
            tiendaController = new TiendaController(this);

            controllerCuenta.inicializarCuentas();
            controllerCuenta.cargarCuentas();
            tiendaController.cargarCategorias();
            tiendaController.cargarProductosPorCategoria();
            controllerTransacciones.cargarTransacciones();

            anchorPanePrincipal.setVisible(true);
            PanePrincipal.setVisible(true);
            anchorPaneRegistroCuenta.setVisible(false);
            anchorPaneGestionarCuenta.setVisible(false);
            anchorPaneVerDatosUsuario.setVisible(false);
            anchorPaneCambiarContrasena.setVisible(false);
            PaneMeterDinero.setVisible(false);
            PaneSacarDinero.setVisible(false);
            panePasarDinero.setVisible(false);
            PaneTienda.setVisible(false);
            PaneVerMas.setVisible(false);



            // Eventos ControllerCuenta
            comboTipoCuenta.getItems().addAll("Cuenta de ahorros", "Cuenta corriente");
            btnInicio.setOnAction(e -> controllerCuenta.Inicio());
            btnAgregarCuenta.setOnAction(e -> controllerCuenta.agregarCuenta());
            btnRegistrarCuenta.setOnAction(e -> controllerCuenta.registrarCuenta());
            btnGestionarCuentas.setOnAction(e -> controllerCuenta.gestionarCuentas());
            btnConsultarCuenta.setOnAction(e -> controllerCuenta.consultarCuenta());
            btnEliminarCuenta.setOnAction(e -> controllerCuenta.eliminarCuenta());
            btnRegresar.setOnAction(e -> controllerCuenta.Inicio());
            btnCerrarSesion.setOnAction(e -> controllerCuenta.cerrarSesion());

            //Eventos controllerDatos
            btnVerDatos.setOnAction(e -> controllerDatos.verDatosUsuario());
            btnModificarDatos.setOnAction(e -> controllerDatos.mostrarPanelEditarDatos());
            btnVolverDatosUsuario.setOnAction(e -> controllerCuenta.Inicio());
            btnGuardarCambios.setOnAction(e -> controllerDatos.editarDatosUsuario());
            btnCambiarContrasena.setOnAction(e -> controllerDatos.mostrarCambiarContrasena());
            btnGuardarCambioClave.setOnAction(e -> controllerDatos.guardarCambioClave());
            btnVolverCambioClave.setOnAction(e -> controllerCuenta.Inicio());

            //Eventos controllerPrincipal

            //Eventos ControllerMeterDinero
            btnMeterDinero.setOnAction(e -> controllerTransacciones.meterDinero());
            btnRegresar1.setOnAction(e -> controllerCuenta.Inicio());
            btnAgregar.setOnAction(event -> controllerTransacciones.agregarDinero());

            //Evento ControllerSacardinero
            btnSacarDinero.setOnAction(e -> controllerTransacciones.retirar());
            btnGenerarCodigo.setOnAction(e -> controllerTransacciones.retirarDinero());
            btnRegresar11.setOnAction(e-> controllerCuenta.Inicio());

            //Evento ControllerPasarDinero
            btnPasarDinero.setOnAction(e -> controllerTransacciones.pasarDinero());
            btnRegresar12.setOnAction(e -> controllerCuenta.Inicio());
            btnPasar.setOnAction(e -> controllerTransacciones.transferirDinero());

            //Evento ControllerTienda
            btnTienda.setOnAction(e -> tiendaController.iniciarVista());
            btnRegresarTienda.setOnAction(e -> tiendaController.restaurarVistaPrincipal());
            btnPagarTienda.setOnAction(e -> tiendaController.realizarCompra());

            //Evento ControllerTransacciones
            btnVermas.setOnAction(e -> controllerTransacciones.iniciarVista());
            btnVolverTransaccion.setOnAction(e-> controllerCuenta.Inicio());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public TextField getCampoTitular() {
        return campoTitular;
    }


    public TextField getCampoNumeroCuenta() {
        return campoNumeroCuenta;
    }


    public ComboBox<String> getComboTipoCuenta() {
        return comboTipoCuenta;
    }


    public ComboBox<Cuenta> getComboCuentas() {
        return comboCuentas;
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

    public Button getBtnMeterDinero() {
        return btnMeterDinero;
    }

    public TextField getTxtCantidad() {
        return txtCantidad;
    }

    public TextField getCantidadIngresar1() {
        return cantidadIngresar1;
    }

    public TextField getCantidadIngresar() {
        return cantidadIngresar;
    }

    public TextField getNumeroCuenta() {
        return numeroCuenta;
    }

    public TextField getCantidadIngresar2() {
        return cantidadIngresar2;
    }
}
