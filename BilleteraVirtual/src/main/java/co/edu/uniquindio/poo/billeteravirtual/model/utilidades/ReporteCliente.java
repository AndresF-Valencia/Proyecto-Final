package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;

import java.util.Comparator;
import java.util.List;

public class ReporteCliente extends ReporteBase {

    private final String idCliente;
    private double totalIngresos = 0;
    private double totalGastos = 0;

    public ReporteCliente(ExportadorReporte exportador, String idCliente) {
        super(exportador);
        this.idCliente = idCliente;
    }

    @Override
    protected List<Transaccion> obtenerTransacciones() {
        return ServicioTransaccion.obtenerTransaccionesPorCliente(idCliente);
    }

    @Override
    protected void procesarDatos(List<Transaccion> transacciones) {
        // Ordenar por fecha descendente
        transacciones.sort(Comparator.comparing(Transaccion::getFecha).reversed());

        // Calcular totales simples
        for (Transaccion t : transacciones) {
            if (t.getMonto() > 0) {
                totalIngresos += t.getMonto();
            } else {
                totalGastos += Math.abs(t.getMonto());
            }
        }

        double saldo = totalIngresos - totalGastos;

        System.out.println("=== Resumen del Cliente ===");
        System.out.printf("💰 Ingresos: $%,.2f\n", totalIngresos);
        System.out.printf("🧾 Gastos:   $%,.2f\n", totalGastos);
        System.out.printf("💼 Saldo:    $%,.2f\n", saldo);
        System.out.println("===========================");
    }
}