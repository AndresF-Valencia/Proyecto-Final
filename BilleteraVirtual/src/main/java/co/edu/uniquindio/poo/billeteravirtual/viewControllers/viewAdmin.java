package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class viewAdmin {

    @FXML
    private Pane adminGestionCuentas;

    @FXML
    private Pane adminGestionTransaciones;

    @FXML
    private Pane adminGestionUsuarios;

    @FXML
    private Text bienvenidaText;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregarCuenta;

    @FXML
    private Button btnAgregarTransaccion;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEditarCuenta;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEliminarCuenta;

    @FXML
    private Button btnEliminarTransaccion;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnGestionarCuentas;

    @FXML
    private Button btnGestionarTransacciones;

    @FXML
    private Button btnGestionarUsuarios;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnVolverCuenta;

    @FXML
    private Button btnVolverTransaccion;

    @FXML
    private TableColumn<?, ?> columnaBanco;

    @FXML
    private TableColumn<?, ?> columnaCedula;

    @FXML
    private TableColumn<?, ?> columnaClave;

    @FXML
    private TableColumn<?, ?> columnaCorreo;

    @FXML
    private TableColumn<?, ?> columnaDescripcion;

    @FXML
    private TableColumn<?, ?> columnaFecha;

    @FXML
    private TableColumn<?, ?> columnaIdCuenta;

    @FXML
    private TableColumn<?, ?> columnaIdTransaccion;

    @FXML
    private TableColumn<?, ?> columnaMonto;

    @FXML
    private TableColumn<?, ?> columnaNombre;

    @FXML
    private TableColumn<?, ?> columnaNumero;

    @FXML
    private TableColumn<?, ?> columnaPalabra;

    @FXML
    private TableColumn<?, ?> columnaSaldo;

    @FXML
    private TableColumn<?, ?> columnaTelefono;

    @FXML
    private TableColumn<?, ?> columnaTipo;

    @FXML
    private TableColumn<?, ?> columnaTipoTrans;

    @FXML
    private TableColumn<?, ?> columnaUsuario;

    @FXML
    private Label labelSaldoPromedio;

    @FXML
    private TableView<?> tablaUsuarios;

    @FXML
    private TableView<?> tablaUsuarios1;

    @FXML
    private TableView<?> tablaUsuarios2;

}
