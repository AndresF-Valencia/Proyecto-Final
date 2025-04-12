package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionCategoria;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Categoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiciosCategoria {

    private Map<Integer, Categoria> categorias = new HashMap<>();

    //Registrar una Categoria
    public void registrarCategoria(int idCategoria, String nombre, String descripcion ) throws ExcepcionCategoria {
        if (categorias.containsKey(idCategoria)) {
            throw new ExcepcionCategoria("Ya existe un categoria con el id " + idCategoria);
        }
        categorias.put(idCategoria, new Categoria(idCategoria, nombre, descripcion));
        System.out.println("Categoria " + idCategoria + " registrada con exito");
    }

    //Obtener una categoria por ID
    public Categoria obtenerCategoria(int idCategoria) throws ExcepcionCategoria {
        Categoria categoria = categorias.get(idCategoria);
        if (categoria == null) {
            throw new ExcepcionCategoria("No existe una categoria con el id " + idCategoria);
        }
        return categoria;
    }

    //Eliminar Categoria
    public void eliminarCategoria(int idCategoria) throws ExcepcionCategoria {
        if(!categorias.containsKey(idCategoria)) {
            throw new ExcepcionCategoria("No existe una categoria con el id " + idCategoria);
        }
        categorias.remove(idCategoria);
        System.out.println("Categoria " + idCategoria + " eliminada con exito");
    }

    // Listar todas las categorias
    public List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias.values());
    }
}
