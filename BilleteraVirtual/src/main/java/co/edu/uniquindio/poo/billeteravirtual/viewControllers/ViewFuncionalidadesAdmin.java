package co.edu.uniquindio.poo.billeteravirtual.viewControllers;

import co.edu.uniquindio.poo.billeteravirtual.controllers.*;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.EstadisticasReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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
    public BarChart<String, Number> graficoBarras;

    @FXML
    public Text labelSaldoPromedio;

    @FXML
    public AnchorPane paneCuentas;

    @FXML
    public AnchorPane AnchorpaneEstadisticas;

    @FXML
    public Pane paneStats;

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
    public Pane paneBienvenida;

    @FXML
    public Pane paneActualizar;

    @FXML
    public Button btnAgregarCuenta;

    @FXML
    public Button btnPdf;

    @FXML
    public Button btnCsv;

    @FXML
    public Button btnRegresarCuenta;

    @FXML
    public Button btnActualizarCuenta1;

    @FXML
    public Button btnRegistrarCuenta;

    @FXML
    public TableView<Transaccion> tablaTransacciones;

    @FXML
    public TableColumn<Transaccion, String> columnaFecha;

    @FXML
    public TableColumn<Transaccion, String> columnaTipo;

    @FXML
    public TableColumn<Transaccion, String> columnaMonto;

    @FXML
    public TableColumn<Transaccion, String> columnaDescripcion;

    @FXML
    public AnchorPane anchorPaneTransacciones;

    @FXML
    public Text labelBienvenida;

    @FXML
    public ComboBox<String> comboTipoCuenta;

    @FXML
    public ComboBox<Cuenta> comboSeleccionCuenta;

    @FXML
    public Pane paneAgregarCuenta, paneGestionCuentas, paneTransacciones;

    @FXML
    public TextField txtBanco, txtCantidad, txtNumeroCuenta,txtNumeroDestino;

    private ControllerGestionUsuarios controllerGestionUsuarios;
    private ControllerCuentaAdmin controllerCuentaAdmin;
    private ControllerEstadisticas controllerEstadisticas;
    private ControllerTransaccionesAdmin controllerTransaccionesAdmin;
    private EstadisticasReporte estadisticasReporte;

    @FXML
    public void initialize(){
        try {
            controllerGestionUsuarios = new ControllerGestionUsuarios(this);
            controllerCuentaAdmin = new ControllerCuentaAdmin(this);
            controllerEstadisticas = new ControllerEstadisticas(this);
            controllerTransaccionesAdmin = new ControllerTransaccionesAdmin(this);


            controllerGestionUsuarios.cargarUsuarios();
            controllerCuentaAdmin.cargarCuentasTabla();
            controllerEstadisticas.mostrarEstadisticas();
            controllerTransaccionesAdmin.cargarTransacciones();


            paneBienvenida.setVisible(true);
            paneCrearUsuario.setVisible(false);
            paneGestionCuentas.setVisible(false);
            paneStats.setVisible(false);
            paneActualizar.setVisible(false);
            paneAgregarCuenta.setVisible(false);
            paneTransacciones.setVisible(false);

            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
            colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

            colIdCuenta.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
            colNumeroCuenta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
            colTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));
            colBanco.setCellValueFactory(new PropertyValueFactory<>("bancoCuenta"));
            colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));

            CategoryAxis ejeX = (CategoryAxis) graficoBarras.getXAxis();
            NumberAxis ejeY = (NumberAxis) graficoBarras.getYAxis();

            ejeX.setLabel("Usuarios");
            ejeY.setLabel("Cantidad de Transacciones");

            graficoBarras.setTitle("Transacciones por Usuario");
            graficoBarras.setLegendVisible(true);

            // Configurar el gráfico de pastel
            graficoGastosComunes.setTitle("Gastos por Categoría");
            graficoGastosComunes.setLegendVisible(true);
            graficoGastosComunes.setLabelsVisible(true);


            btnGestionUsuarios.setOnAction(e-> controllerGestionUsuarios.mostrarGestionUsuarios());
            btnCrearUsuario.setOnAction(e-> controllerGestionUsuarios.mostrarCrearUsuario());
            btnAceptarCrearUsuario1.setOnAction(e-> controllerGestionUsuarios.crearUsuario());
            btnRegresarCrearUsuario1.setOnAction(e-> controllerGestionUsuarios.regresar());
            btnActualizarUsuario.setOnAction(e-> controllerGestionUsuarios.actualizar());
            btnAceptarActualizarUsuario.setOnAction(e-> controllerGestionUsuarios.actualizarUsuario());
            btnRegresarCrearUsuario.setOnAction(e-> controllerGestionUsuarios.regresoTotal());
            btnEliminarUsuario.setOnAction(e-> controllerGestionUsuarios.eliminarUsuario());


            btnGestionCuentas.setOnAction(e-> controllerCuentaAdmin.mostrarCuenta());
            btnCerrarSesion.setOnAction(e-> controllerCuentaAdmin.cerrarSesion());
            btnAgregarCuenta.setOnAction(e-> controllerCuentaAdmin.crearCuenta());
            btnRegresarCuenta.setOnAction(e-> controllerCuentaAdmin.mostrarCuenta());
            btnEliminarCuenta.setOnAction(e-> controllerCuentaAdmin.eliminarCuenta());
            btnRegistrarCuenta.setOnAction(e-> controllerCuentaAdmin.registrarCuenta());

            btnEstadisticas.setOnAction(e-> controllerEstadisticas.mostrarEstadisticas());

            btnGestionTransacciones.setOnAction(e-> controllerTransaccionesAdmin.iniciarVista());
            btnPdf.setOnAction(e-> controllerTransaccionesAdmin.generarReporteAdmin(estadisticasReporte));
            btnCsv.setOnAction(e->controllerTransaccionesAdmin.generarReporteAdminCSV(estadisticasReporte));


    } catch (Exception e) {
        e.printStackTrace();
    }
}
}


