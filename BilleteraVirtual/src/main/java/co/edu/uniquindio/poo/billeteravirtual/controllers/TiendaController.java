package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.decoradores.UsuarioConPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.*;
import co.edu.uniquindio.poo.billeteravirtual.model.facade.TransaccionFacade;
import co.edu.uniquindio.poo.billeteravirtual.model.observer.SujetoObservable;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioProducto;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioTransaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.GeneradorCodigo;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador encargado de gestionar la lógica y eventos relacionados con la tienda virtual.
 * <p>
 * Administra la carga de productos, categorías, cuentas del usuario actual y la realización
 * de compras, además de manejar la interfaz correspondiente a la tienda.
 * </p>
 */
public class TiendaController {

    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;
    private final TransaccionFacade transaccionFacade;
    public ServicioCuenta servicioCuenta;
    public ServicioPresupuesto servicioPresupuesto;
    public ServicioTransaccion servicioTransaccion;

    /**
     * Constructor que inicializa el controlador con la vista y los servicios necesarios.
     * Obtiene el usuario actual de la sesión y prepara la fachada de transacciones.
     *
     * @param view instancia de la vista que contiene los elementos visuales de funcionalidades
     */
    public TiendaController(ViewFuncionalidades view) {
        this.view = view;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
        this.servicioPresupuesto = ServicioPresupuesto.getInstancia();
        TransaccionFactory.setServicioCuenta(servicioCuenta);

        this.transaccionFacade = new TransaccionFacade(servicioCuenta, servicioTransaccion, new TransaccionFactory());
    }

    /**
     * Inicializa la vista de la tienda, mostrando solo el panel correspondiente,
     * cargando cuentas, categorías y productos y asignando el evento de cambio de categoría.
     */
    public void iniciarVista() {
        ocultarElementoDePrincipalExceptoTienda();
        view.PaneTienda.setVisible(true);

        ServicioProducto.inicializarProductos();

        cargarCuentas();
        cargarCategorias();
        cargarProductosPorCategoria();

        view.comboCategorias.setOnAction(e -> cargarProductosPorCategoria());
    }

    /**
     * Oculta todos los elementos del panel principal excepto el panel de tienda.
     */
    private void ocultarElementoDePrincipalExceptoTienda() {
        view.anchorPanePrincipal.getChildren().forEach(nodo -> {
            nodo.setVisible(nodo == view.PaneTienda);
        });
    }

    /**
     * Restaura la vista principal, ocultando todos los paneles secundarios y mostrando solo el principal.
     */
    public void restaurarVistaPrincipal() {
        view.PaneTienda.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);

        view.PanePrincipal.setVisible(true);
        view.PanePrincipal.toFront();
    }

    /**
     * Carga y muestra en la vista las cuentas asociadas al usuario actual.
     */
    private void cargarCuentas() {
        List<Cuenta> cuentas = servicioCuenta.obtenerCuentasDe(usuarioActual);
        view.comboCuentaTienda.getItems().setAll(cuentas);
    }

    /**
     * Carga y muestra las categorías disponibles para productos en la tienda.
     */
    public void cargarCategorias() {
        List<CategoriaProducto> categorias = ServicioProducto.obtenerCategoriasDisponibles();
        view.comboCategorias.getItems().setAll(categorias);
    }

    /**
     * Carga y muestra los productos correspondientes a la categoría seleccionada en el combo.
     */
    public void cargarProductosPorCategoria() {
        CategoriaProducto categoriaSeleccionada = view.comboCategorias.getValue();
        if (categoriaSeleccionada != null) {
            List<Producto> productos = ServicioProducto.obtenerProductosPorCategoria(categoriaSeleccionada);
            view.comboProductos.getItems().setAll(productos);
        }
    }

    /**
     * Realiza una compra validando los campos, saldo y presupuesto disponible,
     * registra la transacción y actualiza las vistas y observadores.
     */
    public void realizarCompra() {
        Cuenta cuentaOrigen = view.comboCuentaTienda.getValue();
        CategoriaProducto categoriaProducto = view.comboCategorias.getValue();
        Producto producto = view.comboProductos.getValue();
        String textoCantidad = view.txtCantidad.getText();
        String codigoGenerado = new GeneradorCodigo().generarCodigo();

        if (mostrarErrorSi(cuentaOrigen == null || categoriaProducto == null || producto == null || textoCantidad.isEmpty(), "❌ Complete todos los campos.")) {
            return;
        }

        double cantidad = Double.parseDouble(textoCantidad);
        if (mostrarErrorSi(cantidad <= 0, "❌ La cantidad debe ser mayor que cero.")) {
            return;
        }

        double monto = cantidad * producto.getPrecio();
        double saldoOrigen = cuentaOrigen.getSaldo1();

        if (mostrarErrorSi(saldoOrigen < monto, "⚠️ Saldo insuficiente.")) {
            return;
        }

        UsuarioConPresupuesto decorado = null;
        if (!servicioPresupuesto.obtenerPresupuestos(usuarioActual).isEmpty()) {
            decorado = servicioPresupuesto.crearUsuarioConPresupuesto(usuarioActual);
        }

        if (decorado != null) {
            if (mostrarErrorSi(!decorado.sePuedeRealizarTransaccion(monto, categoriaProducto.getNombre()), "❌ Excede el presupuesto de compra.")) {
                return;
            }
            decorado.registrarGasto(monto, categoriaProducto.getNombre());
        }

        transaccionFacade.procesarTransaccion(codigoGenerado, LocalDate.now(), "COMPRA", monto,
                "Compra exitosa de " + producto.getNombre(),
                cuentaOrigen.getNumeroCuenta(), Transaccion.CUENTAEXTERNA);

        SujetoObservable.notificarObservadores();
        SujetoObservable.notificarSaldo();
        Logger.getInstance().mostrarToast(view.rootPane, "Transacción exitosa");

        // Limpiar campos
        view.numeroCuenta.clear();
        view.txtCantidad.clear();
    }

    /**
     * Muestra un mensaje de error en la vista si la condición es verdadera.
     *
     * @param condicion condición que determina si se muestra el mensaje de error
     * @param mensaje   texto del mensaje a mostrar
     * @return true si se mostró el error, false en caso contrario
     */
    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }
}
