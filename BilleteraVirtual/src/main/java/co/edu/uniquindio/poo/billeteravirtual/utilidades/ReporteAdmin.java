package co.edu.uniquindio.poo.billeteravirtual.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioTransaccion;

import java.util.Comparator;
import java.util.List;

public class ReporteAdmin extends ReporteBase {

    private double totalIngresos = 0;
    private double totalGastos = 0;

    public ReporteAdmin(ExportadorReporte exportador) {
        super(exportador);
    }

    @Override
    protected List<Transaccion> obtenerTransacciones() {
        return ServicioTransaccion.obtenerTodasLasTransacciones();
    }

    @Override
    protected void procesarDatos(List<Transaccion> transacciones) {
        // Ordenar por fecha descendente
        transacciones.sort(Comparator.comparing(Transaccion::getFecha).reversed());

        // Calcular métricas
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getMonto() > 0) {
                totalIngresos += transaccion.getMonto();
            } else {
                totalGastos += Math.abs(transaccion.getMonto());
            }
        }

        double saldo = totalIngresos - totalGastos;

        System.out.println("==== MÉTRICAS DEL REPORTE ADMIN ====");
        System.out.printf("🟢 Total Ingresos: $%,.2f\n", totalIngresos);
        System.out.printf("🔴 Total Gastos:   $%,.2f\n", totalGastos);
        System.out.printf("⚖️  Saldo Neto:     $%,.2f\n", saldo);
        System.out.println("====================================");
    }
}