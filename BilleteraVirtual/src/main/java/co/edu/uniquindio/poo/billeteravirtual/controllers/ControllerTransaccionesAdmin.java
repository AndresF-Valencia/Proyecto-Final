package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.adapter.EstadisticasReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporteCSV;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReportePDF;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.ReporteAdmin;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * Controlador para gestionar las transacciones en la vista de administrador.
 * <p>
 * Esta clase se encarga de manejar la interacción entre la vista administrativa
 * relacionada con transacciones y el servicio de transacciones. Permite cambiar
 * la vista activa, cargar todas las transacciones y generar reportes en formatos PDF y CSV.
 * </p>
 */
public class ControllerTransaccionesAdmin {

    private final ViewFuncionalidadesAdmin view;
    private final ServicioTransaccion servicioTransaccion;

    /**
     * Constructor que inicializa el controlador con la vista administrativa y
     * el servicio de transacciones.
     *
     * @param view instancia de la vista administrativa con funcionalidades para el administrador
     */
    public ControllerTransaccionesAdmin(ViewFuncionalidadesAdmin view) {
        this.view = view;
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
    }

    /**
     * Cambia la vista activa en la interfaz de usuario según el nombre dado.
     *
     * @param vistaActiva nombre de la vista que se debe mostrar (por ejemplo, "Bienvenida", "Transacciones", etc.)
     */
    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneCuentas.setVisible(vistaActiva.equals("Cuentas"));
        view.paneStats.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
    }

    /**
     * Inicializa la vista de transacciones para el administrador,
     * mostrando el panel correspondiente y cargando las transacciones.
     */
    public void iniciarVista() {
        view.anchorPaneTransacciones.setVisible(true);
        cambiarVista("Transacciones");
        cargarTransacciones();
    }

    /**
     * Carga todas las transacciones del sistema y las muestra en la tabla
     * de transacciones de la vista administrativa.
     */
    public void cargarTransacciones() {
        List<Transaccion> transacciones = servicioTransaccion.obtenerTodasLasTransacciones();

        view.columnaFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        view.columnaTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        view.columnaMonto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMonto()).asObject().asString());
        view.columnaDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));

        view.tablaTransacciones.getItems().setAll(transacciones);
    }

    /**
     * Genera un reporte en formato PDF con las estadísticas proporcionadas para el administrador.
     *
     * @param estadisticasReporte objeto que contiene las estadísticas que se desean incluir en el reporte
     */
    public void generarReporteAdmin(EstadisticasReporte estadisticasReporte){
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/admin.pdf";
        ExportadorReportePDF exportador = new ExportadorReportePDF();

        exportador.setEstadisticas(estadisticasReporte);

        ReporteAdmin reporte = new ReporteAdmin(exportador);
        reporte.generar(rutaArchivo);
    }

    /**
     * Genera un reporte en formato CSV con las estadísticas proporcionadas para el administrador.
     *
     * @param estadisticasReporte objeto que contiene las estadísticas que se desean incluir en el reporte
     */
    public void generarReporteAdminCSV(EstadisticasReporte estadisticasReporte) {
        String rutaArchivo = "C:/Users/andre/OneDrive/Documents/admin.csv";
        ExportadorReporteCSV exportador = new ExportadorReporteCSV();

        exportador.setEstadisticas(estadisticasReporte);

        ReporteAdmin reporte = new ReporteAdmin(exportador);
        reporte.generar(rutaArchivo);
    }
}
