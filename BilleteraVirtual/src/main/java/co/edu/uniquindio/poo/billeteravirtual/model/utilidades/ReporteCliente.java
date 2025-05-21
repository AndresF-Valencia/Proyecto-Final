package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;

import java.util.Comparator;
import java.util.List;

public class ReporteCliente extends ReporteBase {

    private final String idCliente;
    private double totalIngresos = 0.0;
    private double totalGastos = 0.0;
    private final ServicioTransaccion servicioTransaccion;

    public ReporteCliente(ExportadorReporte exportador, String idCliente) {
        super(exportador);
        this.idCliente = idCliente;
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
    }

    @Override
    protected List<Transaccion> obtenerTransacciones() {
        return servicioTransaccion.obtenerTransaccionesPorCliente(idCliente);
    }

    @Override
    protected void procesarDatos(List<Transaccion> transacciones) {
        transacciones.sort(Comparator.comparing(Transaccion::getFecha).reversed());

        for (Transaccion transaccion : transacciones) {
            if(transaccion.getTipo().equalsIgnoreCase("Deposito")){
                totalIngresos++;
            } else if(transaccion.getTipo().equalsIgnoreCase("Retiro") || transaccion.getTipo().equalsIgnoreCase("Transferencia")|| transaccion.getTipo().equalsIgnoreCase("Compra")){
                totalGastos++;
            }

        }

    }

    public double getTotalGastos() {
        return totalGastos;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }
}