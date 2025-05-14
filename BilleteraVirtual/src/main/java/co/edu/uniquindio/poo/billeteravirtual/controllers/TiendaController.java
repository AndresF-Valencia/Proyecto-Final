package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.*;
import co.edu.uniquindio.poo.billeteravirtual.model.facade.TransaccionFacade;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioCuenta;
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

public class TiendaController {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;
    private final TransaccionFacade transaccionFacade;

    public TiendaController(ViewFuncionalidades view) {
        this.view = view;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
        ServicioCuenta servicioCuenta = ServicioCuenta.getInstancia();
        ServicioTransaccion servicioTransaccion = ServicioTransaccion.getInstancia();
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
        List<Cuenta> cuentas = ServicioCuenta.obtenerCuentasDe(usuarioActual);
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

        if (mostrarErrorSi(cuentaOrigen == null || categoriaProducto == null || producto == null || textoCantidad.isEmpty(),"❌ Complete todos los campos.")) {
            return;
        }

        double cantidad = Double.parseDouble(textoCantidad);

        if (mostrarErrorSi(cantidad <= 0,"❌ La cantidad debe ser mayor que cero.")) {
            return;
        }


        double saldoOrigen = cuentaOrigen.getSaldo1();

        if (mostrarErrorSi(saldoOrigen < cantidad * producto.getPrecio(),"⚠️ Saldo insuficiente.")) {
            return;
        }

        // Realizar la transferencia
        cuentaOrigen.agregarSaldo(-cantidad * producto.getPrecio());
        transaccionFacade.procesarTransaccion(codigoGenerado, LocalDate.now(),"COMPRA", cantidad* producto.getPrecio(),"Compra exitosa de"+ producto, cuentaOrigen.getIdCuenta(), Transaccion.CUENTAEXTERNA);

        Logger.getInstance().mostrarToast(view.rootPane, "Transaccion exitosa");

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
