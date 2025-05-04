package co.edu.uniquindio.poo.billeteravirtual.adapter;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import java.util.List;

public interface ExportadorReporte {
    void exportarReporte(List<Transaccion> transacciones, String rutaArchivo);
}
