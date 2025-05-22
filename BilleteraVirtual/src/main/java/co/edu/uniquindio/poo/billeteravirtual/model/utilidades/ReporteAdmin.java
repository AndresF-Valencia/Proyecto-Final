package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import co.edu.uniquindio.poo.billeteravirtual.model.adapter.EstadisticasReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.adapter.ExportadorReporte;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioEstadisticas;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Clase que genera un reporte administrativo basado en las transacciones y usuarios.
 * Extiende ReporteBase para exportar reportes con diferentes formatos.
 */
public class ReporteAdmin extends ReporteBase {

    private ServicioUsuario servicioUsuario;
    private ServicioEstadisticas servicioEstadisticas;
    private int maxTransacciones;
    private double maxGasto = 0.0;

    /**
     * Constructor que inicializa el exportador y los servicios necesarios.
     *
     * @param exportador Exportador para el formato de reporte deseado.
     */
    public ReporteAdmin(ExportadorReporte exportador) {
        super(exportador);
        this.servicioUsuario = ServicioUsuario.getInstancia();
        this.servicioEstadisticas = ServicioEstadisticas.getInstancia();
    }

    /**
     * Obtiene todas las transacciones para procesar en el reporte.
     *
     * @return Lista con todas las transacciones registradas.
     */
    @Override
    protected List<Transaccion> obtenerTransacciones() {
        return ServicioTransaccion.obtenerTodasLasTransacciones();
    }

    /**
     * Procesa los datos de las transacciones para calcular estadísticas relevantes
     * como el usuario más activo y la categoría con mayor gasto.
     *
     * @param transacciones Lista de transacciones a procesar.
     */
    @Override
    protected void procesarDatos(List<Transaccion> transacciones) {
        EstadisticasReporte estadisticas = new EstadisticasReporte();

        Map<String, Double> categorias = servicioEstadisticas.obtenerGastosPorCategoria();

        transacciones.sort(Comparator.comparing(Transaccion::getFecha).reversed());

        for (Usuario usuario : servicioUsuario.getUsuariosRegistrados()) {
            int total = 0;
            for (Cuenta cuenta : usuario.getCuentas()) {
                total += cuenta.getTransacciones().size();
            }
            if (total > maxTransacciones) {
                maxTransacciones = total;
                estadisticas.usuarioMasActivo = usuario;
            }
        }

        for (Map.Entry<String, Double> entrada : categorias.entrySet()) {
            if (entrada.getValue() > maxGasto) {
                maxGasto = entrada.getValue();
                estadisticas.categoriaMasUsada = entrada.getKey();
            }
        }
    }
}
