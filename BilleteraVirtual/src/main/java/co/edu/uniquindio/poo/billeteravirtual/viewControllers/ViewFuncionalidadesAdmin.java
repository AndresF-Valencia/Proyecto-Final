package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.ControllerGestionUsuarios;
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
    private AnchorPane AnchorpaneUsuarios;

    @FXML
    private Button btnAceptarActualizarUsuario;

    @FXML
    private Button btnActualizarUsuario;

    @FXML
    public Button btnCerrarSesion;

    @FXML
    private Button btnCrearUsuario;

    @FXML
    private Button btnEliminarCuenta;

    @FXML
    private Button btnEliminarTransaccion;

    @FXML
    private Button btnEliminarUsuario;

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
    private Button btnRegresarCrearUsuario;

    @FXML
    private Button btnRegresarCrearUsuario1;

    @FXML
    private TableColumn<?, ?> colBanco;

    @FXML
    public TableColumn<Usuario, String> colCedula;

    @FXML
    public TableColumn<Usuario, String> colCorreo;

    @FXML
    public TableColumn<?, ?> colDescripcion;

    @FXML
    public TableColumn<?, ?> colFecha;

    @FXML
    public TableColumn<?, ?> colIdCuenta;

    @FXML
    public TableColumn<?, ?> colIdTransaccion;

    @FXML
    public TableColumn<?, ?> colMonto;

    @FXML
    public TableColumn<Usuario, String>  colNombre;

    @FXML
    public TableColumn<?, ?> colNumeroCuenta;

    @FXML
    public TableColumn<?, ?> colSaldo;

    @FXML
    public TableColumn<Usuario, String>  colTelefono;

    @FXML
    public TableColumn<?, ?> colTipo;

    @FXML
    public TableColumn<?, ?> colTipoCuenta;

    @FXML
    public PieChart graficoGastosComunes;

    @FXML
    public BarChart<?, ?> graficoUsuariosTransacciones;

    @FXML
    public Text labelSaldoPromedio;

    @FXML
    public AnchorPane paneCuentas;

    @FXML
    public AnchorPane paneEstadisticas;

    @FXML
    public AnchorPane paneTransacciones;

    @FXML
    public Pane paneUsuarios;

    @FXML
    public AnchorPane rootPane;

    @FXML
    public TableView<?> tablaCuentas;

    @FXML
    public TableView<?> tablaTransacciones;

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
    public Pane PaneTransacciones;

    @FXML
    public AnchorPane anchorpaneTransacciones;

    @FXML
    public Button btnActualizarCuenta;

    @FXML
    public Button btnAgregarCuenta;

    @FXML
    public Button btnCrearTransaccion;

    @FXML
    public Button btnPasarTransaccion;

    @FXML
    public Button btnRegistrarCuenta;

    @FXML
    public Button btnRegresarTransaccion;

    @FXML
    public ComboBox<?> comboActualizarTipoCuenta;

    @FXML
    public ComboBox<?> comboCuentaTransaccion;

    @FXML
    public ComboBox<?> comboTipoCuenta;

    @FXML
    public Pane paneActualizarCuenta;

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

    @FXML
    public void initialize(){
        try {
            controllerGestionUsuarios = new ControllerGestionUsuarios(this);
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
            colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

            paneBienvenida.setVisible(true);
            paneUsuarios.setVisible(false);
            paneCrearUsuario.setVisible(false);
            paneCuentas.setVisible(false);
            paneTransacciones.setVisible(false);
            paneEstadisticas.setVisible(false);
            paneActualizar.setVisible(false);

            controllerGestionUsuarios.cargarUsuarios();

            btnGestionUsuarios.setOnAction(e-> controllerGestionUsuarios.mostrarGestionUsuarios());
            btnCrearUsuario.setOnAction(e-> controllerGestionUsuarios.mostrarCrearUsuario());
            btnAceptarCrearUsuario1.setOnAction(e-> controllerGestionUsuarios.crearUsuario());
            btnRegresarCrearUsuario1.setOnAction(e-> controllerGestionUsuarios.regresar());
            btnActualizarUsuario.setOnAction(e-> controllerGestionUsuarios.actualizar());
            btnAceptarActualizarUsuario.setOnAction(e-> controllerGestionUsuarios.actualizarUsuario());
            btnRegresarCrearUsuario.setOnAction(e-> controllerGestionUsuarios.regresar());
            btnEliminarUsuario.setOnAction(e-> controllerGestionUsuarios.eliminarUsuario());

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    }


