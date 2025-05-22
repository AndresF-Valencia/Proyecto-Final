package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import java.util.List;

/**
 * Interfaz que define el comportamiento para exportar reportes
 * de transacciones a diferentes formatos (por ejemplo, CSV o PDF).
 *
 * Permite implementar distintos exportadores que procesan datos
 * como totales de ingresos/gastos y estadísticas generales.
 */
public interface ExportadorReporte {

    /**
     * Exporta un reporte con la lista de transacciones a la ruta especificada.
     *
     * @param transacciones Lista de transacciones a exportar.
     * @param rutaArchivo   Ruta del archivo donde se generará el reporte.
     */
    void exportarReporte(List<Transaccion> transacciones, String rutaArchivo);

    /**
     * Establece los totales de ingresos y gastos de un cliente
     * para ser incluidos en el reporte exportado.
     *
     * @param totalIngresos Monto total recibido por el cliente.
     * @param gastos        Monto total gastado por el cliente.
     */
    void setTotalesCliente(Double totalIngresos, Double gastos);

    /**
     * Establece las estadísticas generales del sistema, como el usuario más activo
     * y la categoría de transacción más usada, para su inclusión en el reporte exportado.
     *
     * @param estadisticas Objeto que contiene las estadísticas del sistema.
     */
    void setEstadisticasReporte(EstadisticasReporte estadisticas);
}
