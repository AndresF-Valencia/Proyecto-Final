package co.edu.uniquindio.poo.billeteravirtual.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;

import java.util.List;

public class GeneradorReportes {
    private ExportadorReporte exportador;

    public void setExportador(ExportadorReporte exportador) {
        this.exportador = exportador;
    }

    public void generarReporte(List<Transaccion> transacciones, String rutaArchivo) {
        if (exportador != null) {
            exportador.exportarReporte(transacciones, rutaArchivo);
        } else {
            System.err.println("No se ha configurado un exportador.");
        }
    }
}
