package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import java.util.List;

public interface ExportadorReporte {
    void exportarReporte(List<Transaccion> transacciones, String rutaArchivo);
}
