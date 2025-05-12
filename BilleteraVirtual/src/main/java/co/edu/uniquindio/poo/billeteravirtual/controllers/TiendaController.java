package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.entidades.CategoriaProducto;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Cuenta;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Producto;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioCuenta;
import co.edu.uniquindio.poo.billeteravirtual.servicios.ServicioProducto;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Logger;
import co.edu.uniquindio.poo.billeteravirtual.utilidades.Sesion;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewFuncionalidades;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

public class TiendaController {
    private final ViewFuncionalidades view;
    private final Usuario usuarioActual;

    public TiendaController(ViewFuncionalidades view) {
        this.view = view;
        this.usuarioActual = Sesion.getInstancia().getUsuarioActual();
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

        // Depuración
        System.out.println("Categorías cargadas: " + categorias.size());
    }

    // Método para cargar los productos correspondientes a la categoría seleccionada
    public void cargarProductosPorCategoria() {
        CategoriaProducto categoriaSeleccionada = view.comboCategorias.getValue();
        if (categoriaSeleccionada != null) {
            // Obtener productos por categoría
            List<Producto> productos = ServicioProducto.obtenerProductosPorCategoria(categoriaSeleccionada);

            // Depuración
            System.out.println("Productos encontrados para la categoría " + categoriaSeleccionada.getNombre() + ": " + productos.size());

            view.comboProductos.getItems().setAll(productos);
        }
    }

    // Método para realizar la compra
    public void realizarCompra() {
        Cuenta cuentaOrigen = view.comboCuentaTienda.getValue();
        CategoriaProducto categoriaProducto = view.comboCategorias.getValue();
        Producto producto = view.comboProductos.getValue();
        String textoCantidad = view.txtCantidad.getText();

        if (cuentaOrigen == null || categoriaProducto == null || producto == null || textoCantidad.isBlank()) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ Complete todos los campos.");
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(textoCantidad);
        } catch (NumberFormatException e) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ Cantidad inválida.");
            return;
        }

        if (cantidad <= 0) {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ La cantidad debe ser mayor que cero.");
            return;
        }


        double saldoOrigen = cuentaOrigen.getSaldo1();

        if (saldoOrigen < cantidad* producto.getPrecio()) {
            Logger.getInstance().mostrarToast(view.rootPane, "⚠️ Saldo insuficiente.");
            return;
        }

        // Realizar la transferencia
        cuentaOrigen.agregarSaldo(-cantidad * producto.getPrecio());

        Logger.getInstance().mostrarToast(view.rootPane, "✅ Compra Exitosa ");

        // Limpiar campos
        view.numeroCuenta.clear();
        view.txtCantidad.clear();
    }
}
