package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

/**
 * Clase que representa una categoría de productos.
 */
public class CategoriaProducto {
    private final String nombre;
    private final String descripcion;
    private final String idCategoriaProducto;

    /**
     * Constructor de CategoriaProducto.
     * @param nombre Nombre de la categoría.
     * @param descripcion Descripción de la categoría.
     * @param idCategoriaProducto Identificador único de la categoría.
     */
    public CategoriaProducto(String nombre, String descripcion, String idCategoriaProducto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoriaProducto = idCategoriaProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
