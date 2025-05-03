package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class Categoria {
    private int IdCategoria;
    private String nombre;
    private String descripcion;

    //Constructor de Categoria
    public Categoria(int IdCategoria, String nombre, String descripcion) {
        this.IdCategoria = IdCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    //Getters y Setters de Categoria
    public int getIdCategoria() {
        return IdCategoria;
    }
    public void setIdCategoria(int IdCategoria) {
        this.IdCategoria = IdCategoria;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    //To String de Categoria
    @Override
    public String toString() {
        return "Categoria{" +
                "IdCategoria=" + IdCategoria +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

