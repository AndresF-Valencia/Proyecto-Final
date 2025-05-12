package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class CategoriaProducto {
    private final String nombre;

    public CategoriaProducto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}