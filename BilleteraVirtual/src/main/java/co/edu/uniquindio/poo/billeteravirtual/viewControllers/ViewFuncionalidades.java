package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.*;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.*;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ViewFuncionalidades {
    @FXML
    public Button btnMeterDinero, btnSacarDinero, btnPasarDinero, btnInicio, btnPasar, btnRegresar12, btnPagarTienda,
            btnVerDatos, btnCambiarContrasena, btnAgregarCuenta, btnAgregar, btnGenerarCodigo,btnRegresar11,
            btnGestionarCuentas, btnCerrarSesion, btnRegistrarCuenta, btnRegresarTienda, btnTienda,
            btnConsultarCuenta, btnEliminarCuenta,btnVolverTransaccion, btnRegresar, btnRegresar1,btnRegresar13,
            btnGuardarCambios,btnRegresar15,btnActualizar,btnRegresar16,btnEliminarPresupuesto1,btnEliminarPresupuesto,btnActualizarPresupuesto,btnVerEstado,btnCrearPresupuesto,btnAgregarPresupuesto, btnModificarDatos,btnConsultarPresupuesto, btnRegresar14, btnVolverDatosUsuario,btnGuardarCambioClave, btnVolverCambioClave, btnVermas;

    @FXML
    public Text bienvenidaText, textTitular, textNumeroCuenta, textTipoCuenta, textSaldo,txtSaldoPrincipal,
            textNombreUsuario, textCorreoUsuario, textDocumentoUsuario,txtMontoAsignado,txtMontoGastado,txtMontoDisponible, textTelefonoUsuario;

    @FXML
    public TextField campoTitular,cantidadIngresar2, numeroCuenta, campoNumeroCuenta, cantidadIngresar, cantidadIngresar1,
            txtNombreEditar,campoNombrePresupuestoActualizar,campoNombrePresupuestoEliminar,campoMontoTotal,campoNombrePresupuestoConsultar,campoNombrePresupuesto,campoMonto, txtCorreoEditar, txtTelefonoEditar,txtPalabraClaveActual, txtNuevaPalabraClave, txtCantidad;


    @FXML
    public ComboBox<String> comboTipoCuenta, comboCategoriaPresupuesto;

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
    public AnchorPane anchorPanePresupuesto,anchorPanePrincipal,anchorPaneRegistroCuenta, anchorPaneGestionarCuenta, rootPane, anchorPaneVerDatosUsuario, anchorPaneCambiarContrasena;

    @FXML
    public PasswordField pfClaveActual, pfNuevaClave, pfConfirmarClave;

    @FXML
    public Pane PaneEliminarPresupuesto,camposInformacion,PaneEstadoPresupuesto,PaneCrearPresupuesto, paneEditarInformacion, PaneMeterDinero , PaneSacarDinero, panePasarDinero, PaneTienda, PanePrincipal, PaneVerMas,PaneActualizarPresupuesto;
    public Circle circle;

    private ControllerCuenta controllerCuenta;
    private ControllerDatos controllerDatos;
    private ControllerTransacciones controllerTransacciones;
    private TiendaController tiendaController;
    private Usuario usuarioActual = Sesion.getInstancia().getUsuarioActual();

    @FXML
    public void initialize() {
        try {
            controllerCuenta = new ControllerCuenta(this);
            ;
            controllerDatos = new ControllerDatos(this);
            controllerTransacciones = new ControllerTransacciones(this);
            tiendaController = new TiendaController(this);

            controllerCuenta.inicializarCuentas();
            controllerCuenta.cargarCuentas();
            tiendaController.cargarCategorias();
            tiendaController.cargarProductosPorCategoria();
            controllerTransacciones.cargarTransacciones();
            
            bienvenidaText.setText(bienvenidaText.getText() + usuarioActual.getNombre() + "!");
            String saldo = controllerTransacciones.actualizarSaldoInicio();
            txtSaldoPrincipal.setText(saldo);




            anchorPanePrincipal.setVisible(true);
            PanePrincipal.setVisible(true);
            anchorPaneRegistroCuenta.setVisible(false);
            anchorPaneGestionarCuenta.setVisible(false);
            anchorPaneVerDatosUsuario.setVisible(false);
            anchorPaneCambiarContrasena.setVisible(false);
            anchorPanePresupuesto.setVisible(false);
            PaneCrearPresupuesto.setVisible(false);
            PaneEstadoPresupuesto.setVisible(false);
            PaneActualizarPresupuesto.setVisible(false);
            PaneEliminarPresupuesto.setVisible(false);
            panePasarDinero.setVisible(false);
            PaneSacarDinero.setVisible(false);
            PaneMeterDinero.setVisible(false);
            PaneTienda.setVisible(false);
            PaneVerMas.setVisible(false);


            // Eventos ControllerCuenta
            comboCategoriaPresupuesto.getItems().addAll("General", "Alimentos","Ropa","Tecnologia");
            comboTipoCuenta.getItems().addAll("Cuenta de ahorros", "Cuenta corriente");
            btnInicio.setOnAction(e -> controllerCuenta.Inicio());
            btnAgregarCuenta.setOnAction(e -> controllerCuenta.agregarCuenta());
            btnRegistrarCuenta.setOnAction(e -> controllerCuenta.registrarCuenta());
            btnGestionarCuentas.setOnAction(e -> controllerCuenta.gestionarCuentas());
            btnConsultarCuenta.setOnAction(e -> controllerCuenta.consultarCuenta());
            btnEliminarCuenta.setOnAction(e -> controllerCuenta.eliminarCuenta());
            btnRegresar.setOnAction(e -> controllerCuenta.Inicio());
            btnCerrarSesion.setOnAction(e -> controllerCuenta.cerrarSesion());
            btnAgregarPresupuesto.setOnAction(e -> controllerCuenta.gestionarPresupuesto());
            btnCrearPresupuesto.setOnAction(e -> controllerCuenta.crearPresupuesto());
            btnVerEstado.setOnAction(e-> controllerCuenta.verEstado() );
            btnConsultarPresupuesto.setOnAction(e-> controllerCuenta.consultarEstadoPresupuesto());
            btnRegresar13.setOnAction(e -> controllerCuenta.Inicio());
            btnRegresar14.setOnAction(e -> controllerCuenta.Inicio());
            btnActualizarPresupuesto.setOnAction(e -> controllerCuenta.actualizar() );
            btnActualizar.setOnAction(e -> controllerCuenta.actualizarPresupuesto());
            btnRegresar15.setOnAction(e -> controllerCuenta.Inicio());
            btnRegresar16.setOnAction(e -> controllerCuenta.Inicio());
            btnEliminarPresupuesto.setOnAction(e -> controllerCuenta.eliminar());
            btnEliminarPresupuesto1.setOnAction(e -> controllerCuenta.eliminarPresupuesto());





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
