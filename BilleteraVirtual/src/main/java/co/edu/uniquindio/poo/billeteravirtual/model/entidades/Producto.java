package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

/**
 * Representa un producto que puede ser comprado en la billetera virtual.
 */
public class Producto {
    private final String nombre;
    private final CategoriaProducto categoriaProducto;
    private final double precio;

    /**
     * Constructor de la clase Producto.
     *
     * @param nombre Nombre del producto.
     * @param categoriaProducto Categoría del producto.
     * @param precio Precio del producto.
     */
    public Producto(String nombre, CategoriaProducto categoriaProducto, double precio) {
        this.nombre = nombre;
        this.categoriaProducto = categoriaProducto;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }
}
