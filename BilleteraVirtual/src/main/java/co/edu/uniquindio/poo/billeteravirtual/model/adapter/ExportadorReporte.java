package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import java.util.List;

/**
 * Interfaz que define el comportamiento para exportar reportes
 * de transacciones a diferentes formatos (ej. CSV, PDF).
 */
public interface ExportadorReporte {

    /**
     * Exporta un reporte con la lista de transacciones a la ruta especificada.
     *
     * @param transacciones Lista de transacciones a exportar.
     * @param rutaArchivo   Ruta del archivo donde se generará el reporte.
     */
    void exportarReporte(List<Transaccion> transacciones, String rutaArchivo);
}
