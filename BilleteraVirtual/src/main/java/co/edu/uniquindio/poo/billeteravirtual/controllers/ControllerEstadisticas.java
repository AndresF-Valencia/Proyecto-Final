package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioEstadisticas;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidadesAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerEstadisticas {

    private final ViewFuncionalidadesAdmin view;
    private final Usuario usuarioActual;
    private final ServicioUsuario servicioUsuario;

    public ControllerEstadisticas(ViewFuncionalidadesAdmin viewFuncionalidadesAdmin) {
        this.view = viewFuncionalidadesAdmin;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioUsuario = ServicioUsuario.getInstancia();
        cargarEstadisticas();
    }

    public void cambiarVista(String vistaActiva) {
        view.paneBienvenida.setVisible(vistaActiva.equals("Bienvenida"));
        view.paneUsuarios.setVisible(vistaActiva.equals("Gestion Usuarios"));
        view.paneCrearUsuario.setVisible(vistaActiva.equals("Crear Usuario"));
        view.paneCuentas.setVisible(vistaActiva.equals("Cuentas"));
        view.paneStats.setVisible(vistaActiva.equals("Estadisticas"));
        view.paneActualizar.setVisible(vistaActiva.equals("Actualizar Usuario"));;
        view.paneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.anchorPaneTransacciones.setVisible(vistaActiva.equals("Transacciones"));
        view.paneGestionCuentas.setVisible(vistaActiva.equals("Gestion Cuentas"));
    }

    public void mostrarEstadisticas() {
        view.AnchorpaneEstadisticas.setVisible(true);
        cambiarVista("Estadisticas");
        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        cargarGraficoPie();
        cargarGraficoBarra();
        mostrarSaldoPromedio();
    }


    public void cargarGraficoPie() {
        ServicioEstadisticas servicioEstadisticas = ServicioEstadisticas.getInstancia();
        Map<String, Double> categorias = servicioEstadisticas.obtenerGastosPorCategoria();

        // Limpia datos previos
        view.graficoGastosComunes.getData().clear();

        for (Map.Entry<String, Double> entry : categorias.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            view.graficoGastosComunes.getData().add(slice);
        }
    }



    private void cargarGraficoBarra() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Transacciones por Usuario");

        for (Usuario usuario : servicioUsuario.getUsuariosRegistrados()) {
            int total = 0;
            for (Cuenta cuenta : usuario.getCuentas()) {
                total += cuenta.getTransacciones().size();
            }
            System.out.println(usuario.getNombre() + " -> " + total + " transacciones");
            series.getData().add(new XYChart.Data<>(usuario.getNombre(), total));
        }

        view.graficoBarras.getData().clear();
        view.graficoBarras.getData().add(series);
        System.out.println("Series agregada con " + series.getData().size() + " datos.");
    }


    private void mostrarSaldoPromedio() {
        List<Usuario> usuarios = servicioUsuario.getUsuariosRegistrados();
        List<Cuenta> todasLasCuentas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            todasLasCuentas.addAll(usuario.getCuentas());
        }

        if (todasLasCuentas.isEmpty()) {
            view.labelSaldoPromedio.setText("Saldo promedio: $0.0");
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


