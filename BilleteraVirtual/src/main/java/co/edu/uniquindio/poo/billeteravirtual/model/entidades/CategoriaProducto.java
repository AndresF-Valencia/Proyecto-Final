package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

public class CategoriaProducto {
    private final String nombre;
    private final String descripcion;
    private final String idCategoriaProducto;

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