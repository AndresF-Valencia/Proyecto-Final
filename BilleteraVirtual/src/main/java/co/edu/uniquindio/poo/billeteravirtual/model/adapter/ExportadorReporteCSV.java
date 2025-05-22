package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Implementación de {@link ExportadorReporte} que permite exportar
 * las transacciones en formato CSV.
 */
public class ExportadorReporteCSV implements ExportadorReporte {

    /**
     * Estadísticas generales del sistema.
     */
    private EstadisticasReporte estadisticas;

    /**
     * Total de ingresos registrados por el cliente.
     */
    private Double totalIngresosCliente;

    /**
     * Total de gastos registrados por el cliente.
     */
    private Double totalGastosCliente;

    /**
     * Establece las estadísticas generales del sistema.
     *
     * @param estadisticas Instancia de {@link EstadisticasReporte}.
     */
    public void setEstadisticas(EstadisticasReporte estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * Establece los totales de ingresos y gastos del cliente.
     *
     * @param ingresos Total de ingresos.
     * @param gastos Total de gastos.
     */
    public void setTotalesCliente(Double ingresos, Double gastos) {
        this.totalIngresosCliente = ingresos;
        this.totalGastosCliente = gastos;
    }

    /**
     * Exporta un reporte en formato CSV con la información de las transacciones,
     * las estadísticas generales y los totales del cliente.
     *
     * @param transacciones Lista de transacciones.
     * @param rutaArchivo Ruta donde se guardará el archivo CSV.
     */
    @Override
    public void exportarReporte(List<Transaccion> transacciones, String rutaArchivo) {
        try (FileWriter escritor = new FileWriter(rutaArchivo)) {

            if (estadisticas != null) {
                escritor.append("Estadísticas del Sistema:\n");
                escritor.append("Usuario con más transacciones,").append((CharSequence) estadisticas.usuarioMasActivo).append("\n");
                escritor.append("Categoría más usada,").append(estadisticas.categoriaMasUsada).append("\n");
                escritor.append("\n");
            }

            if (totalIngresosCliente != null && totalGastosCliente != null) {
                escritor.append("Resumen del Cliente:\n");
                escritor.append("Total Ingresos,").append(String.format("%.2f", totalIngresosCliente)).append("\n");
                escritor.append("Total Gastos,").append(String.format("%.2f", totalGastosCliente)).append("\n");
                escritor.append("\n");
            }

            escritor.append("ID Transacción,Fecha,Tipo,Monto,Descripción\n");

            for (Transaccion transaccion : transacciones) {
                escritor.append(transaccion.getIdTransaccion())
                        .append(",")
                        .append(transaccion.getFecha().toString())
                        .append(",")
                        .append(transaccion.getTipo())
                        .append(",")
                        .append(String.valueOf(transaccion.getMonto()))
                        .append(",")
                        .append(transaccion.getDescripcion())
                        .append("\n");
            }

            System.out.println("Reporte CSV generado correctamente: " + rutaArchivo);

        } catch (IOException e) {
            System.err.println("Error al generar el reporte CSV: " + e.getMessage());
        }
    }
}
