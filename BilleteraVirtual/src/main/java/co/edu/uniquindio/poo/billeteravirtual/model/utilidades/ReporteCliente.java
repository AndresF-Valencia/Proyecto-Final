package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReportePDF;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;

import java.util.Comparator;
import java.util.List;

/**
 * Reporte específico para un cliente, que genera estadísticas
 * y datos sobre las transacciones asociadas a ese cliente.
 */
public class ReporteCliente extends ReporteBase {

    private final String idCliente;
    private double totalIngresos;
    private double totalGastos;
    private final ServicioTransaccion servicioTransaccion;

    /**
     * Constructor que recibe el exportador y el ID del cliente.
     *
     * @param exportador Implementación para exportar el reporte.
     * @param idCliente  Identificador del cliente para filtrar transacciones.
     */
    public ReporteCliente(ExportadorReporte exportador, String idCliente) {
        super(exportador);
        this.idCliente = idCliente;
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
    }

    /**
     * Obtiene las transacciones asociadas al cliente.
     *
     * @return Lista de transacciones del cliente.
     */
    @Override
    protected List<Transaccion> obtenerTransacciones() {
        return servicioTransaccion.obtenerTransaccionesPorCliente(idCliente);
    }

    /**
     * Procesa las transacciones para calcular totales de ingresos y gastos.
     *
     * @param transacciones Lista de transacciones a procesar.
     */
    @Override
    protected void procesarDatos(List<Transaccion> transacciones) {
        transacciones.sort(Comparator.comparing(Transaccion::getFecha).reversed());
        double totalIngresos = 0.0;
        double totalGastos = 0.0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equalsIgnoreCase("Deposito")) {
                totalIngresos+= transaccion.getMonto();
            } else if (transaccion.getTipo().equalsIgnoreCase("Transferencia")) {
                Cuenta cuentaDestino = ServicioCuenta.obtenerCuentaPorNumero(transaccion.getCuentaDestino());
                if (cuentaDestino != null && cuentaDestino.getUsuario().getCedula().equals(idCliente)){
                    totalIngresos+= transaccion.getMonto();
                }else{
                    totalGastos+= transaccion.getMonto();
                }
            } else if (transaccion.getTipo().equalsIgnoreCase("Retiro") ||
                    transaccion.getTipo().equalsIgnoreCase("Compra")) {
                totalGastos+= transaccion.getMonto();
            }
        }

        super.exportador.setTotalesCliente(totalIngresos, totalGastos);
    }

    /**
     * Obtiene el total de gastos calculados.
     *
     * @return Total de gastos.
     */
    public double getTotalGastos() {
        return totalGastos;
    }

    /**
     * Obtiene el total de ingresos calculados.
     *
     * @return Total de ingresos.
     */
    public double getTotalIngresos() {
        return totalIngresos;
    }
}
