package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorReporteCSV implements ExportadorReporte {

    private EstadisticasReporte estadisticas;
    private Double totalIngresosCliente;
    private Double totalGastosCliente;

    public void setEstadisticas(EstadisticasReporte estadisticas) {
        this.estadisticas = estadisticas;
    }

    public void setTotalesCliente(Double ingresos, Double gastos) {
        this.totalIngresosCliente = ingresos;
        this.totalGastosCliente = gastos;
    }

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