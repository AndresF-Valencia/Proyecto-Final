package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerCuentaAdmin;
import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerGestionUsuarios;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ViewFuncionalidadesAdmin {



    @FXML
    public AnchorPane AnchorpaneUsuarios;

    @FXML
    public Button btnAceptarActualizarUsuario;

    @FXML
    public Button btnActualizarUsuario;

    @FXML
    public Button btnCerrarSesion;

    @FXML
    public Button btnCrearUsuario;

    @FXML
    public Button btnEliminarCuenta;

    @FXML
    public Button btnEliminarTransaccion;

    @FXML
    public Button btnEliminarUsuario;

    @FXML
    public Button btnEstadisticas;

    @FXML
    public Button btnAceptarCrearUsuario1;

    @FXML
    public Button btnGestionCuentas;

    @FXML
    public Button btnGestionTransacciones;

    @FXML
    public Button btnGestionUsuarios;

    @FXML
    public Button btnRegresarCrearUsuario;

    @FXML
    public Button btnRegresarCrearUsuario1;

    @FXML
    public TableColumn<Cuenta, String>  colBanco;

    @FXML
    public TableColumn<Usuario, String> colCedula;

    @FXML
    public TableColumn<Usuario, String> colCorreo;

    @FXML
    public TableColumn<Transaccion, String> colDescripcion;

    @FXML
    public TableColumn<Transaccion, String>colFecha;

    @FXML
    public TableColumn<Cuenta, String> colIdCuenta;

    @FXML
    public TableColumn<Transaccion, String> colIdTransaccion;

    @FXML
    public TableColumn<Transaccion, String>colMonto;

    @FXML
    public TableColumn<Usuario, String>  colNombre;

    @FXML
    public TableColumn<Cuenta, String> colNumeroCuenta;

    @FXML
    public TableColumn<Cuenta, String>  colSaldo;

    @FXML
    public TableColumn<Usuario, String>  colTelefono;

    @FXML
    public TableColumn<Transaccion, String> colTipo;

    @FXML
    public TableColumn<Cuenta, String> colTipoCuenta;

    @FXML
    public PieChart graficoGastosComunes;

    @FXML
    public BarChart<Transaccion, String> graficoUsuariosTransacciones;

    @FXML
    public Text labelSaldoPromedio;

    @FXML
    public AnchorPane paneCuentas;

    @FXML
    public AnchorPane paneEstadisticas;

    @FXML
    public  Pane paneStats;

    @FXML
    public AnchorPane paneTransacciones;

    @FXML
    public Pane paneUsuarios;

    @FXML
    public AnchorPane rootPane;

    @FXML
    public TableView<Cuenta> tablaCuentas;

    @FXML
    public TableView<Usuario> tablaUsuarios;

    @FXML
    public TextField txtCedulaNuevo1;

    @FXML
    public TextField txtClaveNuevo1;

    @FXML
    public TextField txtCorreoNuevo;

    @FXML
    public TextField txtCorreoNuevo1;

    @FXML
    public TextField txtNombreNuevo;

    @FXML
    public TextField txtNombreNuevo1;

    @FXML
    public TextField txtPalabraClaveNuevo1;

    @FXML
    public TextField txtTelefonoNuevo;

    @FXML
    public TextField txtTelefonoNuevo1;

    @FXML
    public Pane paneCrearUsuario;

    @FXML
    public Pane paneActualizarUsuario;

    @FXML
    public Pane paneBienvenida;

    @FXML
    public Pane paneActualizar;

    @FXML
    public AnchorPane AnchorpaneEstadisticas;

    @FXML
    public Button btnActualizarCuenta;

    @FXML
    public Button btnAgregarCuenta;

    @FXML
    public Button btnActualizarCuenta1;


    @FXML
    public Button btnRegistrarCuenta;

    @FXML
    public ComboBox<Cuenta> comboActualizarTipoCuenta;


    @FXML
    public ComboBox<Cuenta> comboTipoCuenta;

    @FXML
    public ComboBox<Cuenta> comboSeleccionCuenta;

    @FXML
    public Pane paneActualizarCuenta;

    @FXML
    public AnchorPane transacciones;

    @FXML
    public Pane paneAgregarCuenta;

    @FXML
    public Pane paneCrearTransaccion;

    @FXML
    public Pane paneGestionCuentas;

    @FXML
    public TextField txtBanco;

    @FXML
    public TextField txtCantidad;

    @FXML
    public TextField txtNumeroCuenta;

    @FXML
    public TextField txtNumeroDestino;

    private ControllerGestionUsuarios controllerGestionUsuarios;
    private ControllerCuentaAdmin controllerCuentaAdmin;

    @FXML
    public void initialize(){
        try {
            controllerGestionUsuarios = new ControllerGestionUsuarios(this);
            controllerCuentaAdmin = new ControllerCuentaAdmin(this);

            controllerGestionUsuarios.cargarUsuarios();
            controllerCuentaAdmin.cargarCuentasTabla();
            controllerCuentaAdmin.cargarComboCuentas();

            paneBienvenida.setVisible(true);
            paneUsuarios.setVisible(false);
            paneCrearUsuario.setVisible(false);
            paneCuentas.setVisible(false);
            paneStats.setVisible(false);
            paneActualizar.setVisible(false);
            paneAgregarCuenta.setVisible(false);
            paneActualizarCuenta.setVisible(false);

            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
            colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

            colIdCuenta.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
            colNumeroCuenta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
            colTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));
            colBanco.setCellValueFactory(new PropertyValueFactory<>("banco"));
            colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));




            btnGestionUsuarios.setOnAction(e-> controllerGestionUsuarios.mostrarGestionUsuarios());
            btnCrearUsuario.setOnAction(e-> controllerGestionUsuarios.mostrarCrearUsuario());
            btnAceptarCrearUsuario1.setOnAction(e-> controllerGestionUsuarios.crearUsuario());
            btnRegresarCrearUsuario1.setOnAction(e-> controllerGestionUsuarios.regresar());
            btnActualizarUsuario.setOnAction(e-> controllerGestionUsuarios.actualizar());
            btnAceptarActualizarUsuario.setOnAction(e-> controllerGestionUsuarios.actualizarUsuario());
            btnRegresarCrearUsuario.setOnAction(e-> controllerGestionUsuarios.regresar());
            btnEliminarUsuario.setOnAction(e-> controllerGestionUsuarios.eliminarUsuario());


            btnGestionCuentas.setOnAction(e-> controllerCuentaAdmin.mostrarCuenta());
            btnAgregarCuenta.setOnAction(e-> controllerCuentaAdmin.crearCuenta());
            btnActualizarCuenta.setOnAction(e-> controllerCuentaAdmin.actualizarCuenta());
            btnEliminarCuenta.setOnAction(e-> controllerCuentaAdmin.eliminarCuenta());
            btnActualizarCuenta1.setOnAction(e-> controllerCuentaAdmin.actualizarTipoCuenta());
            btnRegistrarCuenta.setOnAction(e-> controllerCuentaAdmin.registrarCuenta());


    } catch (Exception e) {
        e.printStackTrace();
    }
}
}


