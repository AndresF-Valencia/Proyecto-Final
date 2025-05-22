package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

import java.util.List;

/**
 * Clase base abstracta para generación de reportes.
 * Utiliza el patrón Template Method para definir el flujo
 * de generación de reportes y delegar pasos específicos
 * a las subclases.
 */
public abstract class ReporteBase {

    private final ExportadorReporte exportador;

    /**
     * Constructor que recibe el exportador de reportes.
     *
     * @param exportador Implementación para exportar el reporte generado.
     */
    public ReporteBase(ExportadorReporte exportador) {
        this.exportador = exportador;
    }

    /**
     * Método plantilla que controla el flujo general para generar un reporte.
     * Obtiene las transacciones, procesa los datos y exporta el reporte.
     *
     * @param rutaArchivo Ruta donde se guardará el archivo generado.
     */
    public final void generar(String rutaArchivo) {
        List<Transaccion> transacciones = obtenerTransacciones();
        procesarDatos(transacciones);
        exportador.exportarReporte(transacciones, rutaArchivo);
    }

    /**
     * Obtiene la lista de transacciones que serán procesadas en el reporte.
     *
     * @return Lista de transacciones.
     */
    protected abstract List<Transaccion> obtenerTransacciones();

    /**
     * Procesa los datos obtenidos para preparar el reporte.
     *
     * @param transacciones Lista de transacciones para procesar.
     */
    protected abstract void procesarDatos(List<Transaccion> transacciones);
}
