package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioEstadisticas;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controlador encargado de gestionar las estadísticas del sistema,
 * mostrando gráficos y datos relevantes en la vista administrativa.
 */
public class ControllerEstadisticas {

    private final ViewFuncionalidadesAdmin view;
    private final Usuario usuarioActual;
    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor que inicializa el controlador con la vista administrativa,
     * obtiene el usuario actual y carga las estadísticas iniciales.
     *
     * @param viewFuncionalidadesAdmin Vista administrativa para mostrar estadísticas.
     */
    public ControllerEstadisticas(ViewFuncionalidadesAdmin viewFuncionalidadesAdmin) {
        this.view = viewFuncionalidadesAdmin;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
        cargarEstadisticas();
    }

    /**
     * Cambia la vista activa en la interfaz según el nombre del panel solicitado.
     *
     * @param vistaActiva Nombre del panel a activar.
     */
    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneCuentas.setVisible(vistaActiva.equals("Cuentas"));
        view.paneStats.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.anchorPaneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
    }

    /**
     * Muestra el panel de estadísticas y carga sus datos.
     */
    public void mostrarEstadisticas() {
        view.AnchorpaneEstadisticas.setVisible(true);
        cambiarVista("Estadisticas");
        cargarEstadisticas();
    }

    /**
     * Carga y actualiza todos los gráficos y datos estadísticos.
     */
    private void cargarEstadisticas() {
        cargarGraficoPie();
        cargarGraficoBarra();
        mostrarSaldoPromedio();
    }

    /**
     * Carga los datos para el gráfico de pastel (pie chart) que muestra gastos por categoría.
     */
    public void cargarGraficoPie() {
        ServicioEstadisticas servicioEstadisticas = ServicioEstadisticas.getInstancia();
        Map<String, Double> categorias = servicioEstadisticas.obtenerGastosPorCategoria();

        view.graficoGastosComunes.getData().clear();

        for (Map.Entry<String, Double> entry : categorias.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            view.graficoGastosComunes.getData().add(slice);
        }
    }

    /**
     * Carga los datos para el gráfico de barras que muestra el número de transacciones por usuario.
     */
    private void cargarGraficoBarra() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Transacciones por Usuario");

        for (Usuario usuario : servicioUsuario.getUsuariosRegistrados()) {
            int totalTransacciones = usuario.getCuentas()
                    .stream()
                    .mapToInt(cuenta -> cuenta.getTransacciones().size())
                    .sum();
            System.out.println(usuario.getNombre() + " -> " + totalTransacciones + " transacciones");
            series.getData().add(new XYChart.Data<>(usuario.getNombre(), totalTransacciones));
        }

        view.graficoBarras.getData().clear();
        view.graficoBarras.getData().add(series);
        System.out.println("Series agregada con " + series.getData().size() + " datos.");
    }

    /**
     * Calcula y muestra el saldo promedio de todas las cuentas existentes.
     */
    private void mostrarSaldoPromedio() {
        List<Usuario> usuarios = servicioUsuario.getUsuariosRegistrados();
        List<Cuenta> todasLasCuentas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            todasLasCuentas.addAll(usuario.getCuentas());
        }

        if (todasLasCuentas.isEmpty()) {
            view.labelSaldoPromedio.setText("Saldo promedio: $0.00");
            return;
        }

        double suma = 0;
        for (Cuenta cuenta : todasLasCuentas) {
            suma += cuenta.getSaldo1();
        }

        double promedio = suma / todasLasCuentas.size();
        view.labelSaldoPromedio.setText(String.format("Saldo promedio: $%.2f", promedio));
    }
}
