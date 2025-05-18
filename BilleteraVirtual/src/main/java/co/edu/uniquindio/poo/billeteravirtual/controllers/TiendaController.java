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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiendaController {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;
    private final TransaccionFacade transaccionFacade;
    public ServicioCuenta servicioCuenta;
    public ServicioPresupuesto servicioPresupuesto;
    public ServicioTransaccion servicioTransaccion;


    public TiendaController(ViewFuncionalidades view) {
        this.view = view;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        this.servicioCuenta = ServicioCuenta.getInstancia();
        this.servicioTransaccion = ServicioTransaccion.getInstancia();
        this.servicioPresupuesto = ServicioPresupuesto.getInstancia();
        TransaccionFactory.setServicioCuenta(servicioCuenta);

        this.transaccionFacade = new TransaccionFacade(servicioCuenta, servicioTransaccion,new TransaccionFactory());

    }

    public void iniciarVista() {
        ocultarElementoDePrincipalExceptoTienda();
        view.PaneTienda.setVisible(true);

        ServicioProducto.inicializarProductos();

        cargarCuentas();
        cargarCategorias();
        cargarProductosPorCategoria();

        view.comboCategorias.setOnAction(e -> cargarProductosPorCategoria());
    }

    private void ocultarElementoDePrincipalExceptoTienda(){
        view.anchorPanePrincipal.getChildren().forEach(nodo ->{
            nodo.setVisible(nodo == view.PaneTienda);
        });
    }

    public void restaurarVistaPrincipal() {
        view.PaneTienda.setVisible(false);
        view.panePasarDinero.setVisible(false);
        view.PaneMeterDinero.setVisible(false);
        view.PaneSacarDinero.setVisible(false);

        // Muestra solo el principal
        view.PanePrincipal.setVisible(true);
        view.PanePrincipal.toFront(); // Esto asegura que se muestre correctamente
    }


    private void cargarCuentas() {
        List<Cuenta> cuentas = servicioCuenta.obtenerCuentasDe(usuarioActual);
        view.comboCuentaTienda.getItems().setAll(cuentas);
    }

    // Método para cargar las categorías
    public void cargarCategorias() {
        List<CategoriaProducto> categorias = ServicioProducto.obtenerCategoriasDisponibles();
        view.comboCategorias.getItems().setAll(categorias);
    }

    // Método para cargar los productos correspondientes a la categoría seleccionada
    public void cargarProductosPorCategoria() {
        CategoriaProducto categoriaSeleccionada = view.comboCategorias.getValue();
        if (categoriaSeleccionada != null) {
            List<Producto> productos = ServicioProducto.obtenerProductosPorCategoria(categoriaSeleccionada);
            view.comboProductos.getItems().setAll(productos);
        }
    }

    // Método para realizar la compra
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

        transaccionFacade.procesarTransaccion(codigoGenerado, LocalDate.now(), "COMPRA", monto, "Compra exitosa de " + producto.getNombre(), cuentaOrigen.getNumeroCuenta(), Transaccion.CUENTAEXTERNA);
        SujetoObservable.notificarObservadores();
        SujetoObservable.notificarSaldo();
        Logger.getInstance().mostrarToast(view.rootPane, "Transacción exitosa");

        // Limpiar campos
        view.numeroCuenta.clear();
        view.txtCantidad.clear();
    }

    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }
}
