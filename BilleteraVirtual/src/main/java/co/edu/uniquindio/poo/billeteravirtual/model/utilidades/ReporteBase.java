package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;

import java.util.List;

public abstract class ReporteBase {

    private final ExportadorReporte exportador;

    public ReporteBase(ExportadorReporte exportador) {
        this.exportador = exportador;
    }

    // Template Method
    public final void generar(String rutaArchivo) {
        List<Transaccion> transacciones = obtenerTransacciones();
        procesarDatos(transacciones);
        exportador.exportarReporte(transacciones, rutaArchivo);
    }

    protected abstract List<Transaccion> obtenerTransacciones();

    protected abstract void procesarDatos(List<Transaccion> transacciones);
}
