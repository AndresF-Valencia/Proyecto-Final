package co.edu.uniquindio.poo.billeteravirtual.adapter;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorReporteCSV implements ExportadorReporte {
    @Override
    public void exportarReporte(List<Transaccion> transacciones, String rutaArchivo) {
        try (FileWriter escritor = new FileWriter(rutaArchivo)) {
            escritor.append("ID Transacción, Fecha, Tipo, Monto, Descripción\n");

            for (Transaccion transaccion : transacciones) {
                escritor.append(transaccion.getIdTransaccion())
                        .append(", ")
                        .append(transaccion.getFecha().toString())
                        .append(", ")
                        .append(transaccion.getTipo())
                        .append(", ")
                        .append(String.valueOf(transaccion.getMonto()))
                        .append(", ")
                        .append(transaccion.getDescripcion())
                        .append("\n");
            }

            System.out.println("Reporte CSV generado correctamente: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al generar el reporte CSV: " + e.getMessage());
        }
    }
}
