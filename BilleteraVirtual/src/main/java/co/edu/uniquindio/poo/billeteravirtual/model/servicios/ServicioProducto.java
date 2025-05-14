package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.CategoriaProducto;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioProducto {
    private static final List<Producto> listaProductos = new ArrayList<>();
    private static final List<CategoriaProducto> listaCategorias = new ArrayList<>();

    public static void inicializarProductos() {
        if (listaCategorias.isEmpty()) {
            CategoriaProducto alimentos = new CategoriaProducto("Alimentos", "Productos alimenticios", "321");
            CategoriaProducto ropa = new CategoriaProducto("Ropa" , "Productos textiles", "123");
            CategoriaProducto tecnologia = new CategoriaProducto("Tecnología", "Productos Tecnologicos", "345");

            listaCategorias.add(alimentos);
            listaCategorias.add(ropa);
            listaCategorias.add(tecnologia);

            // Agregar productos a cada categoría
            listaProductos.add(new Producto("Arroz", alimentos, 15000));
            listaProductos.add(new Producto("Leche", alimentos, 20000));

            listaProductos.add(new Producto("Camiseta", ropa, 30000));
            listaProductos.add(new Producto("Pantalón", ropa, 45000));

            listaProductos.add(new Producto("Celular", tecnologia, 800000));
            listaProductos.add(new Producto("Audífonos", tecnologia, 120000));
        }
    }

    public static List<Producto> getListaProductos() {
        return listaProductos;
    }

    public static List<CategoriaProducto> obtenerCategoriasDisponibles() {
        return listaCategorias;
    }

    public static List<Producto> obtenerProductosPorCategoria(CategoriaProducto categoria) {
        // Usamos el método getListaProductos para obtener los productos
        return getListaProductos().stream()
                .filter(p -> p.getCategoriaProducto().equals(categoria))
                .collect(Collectors.toList());
    }
}


