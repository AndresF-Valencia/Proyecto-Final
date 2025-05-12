package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class Producto {
    private final String nombre;
    private final CategoriaProducto categoriaProducto;
    private final double precio;

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