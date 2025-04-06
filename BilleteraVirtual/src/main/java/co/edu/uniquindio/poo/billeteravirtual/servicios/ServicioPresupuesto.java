package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.Excepciones.ExcepcionPresupuesto;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioPresupuesto {

    private Map<Integer, Presupuesto> presupuestos = new HashMap<>();

    // Registrar un nuevo presupuesto
    public void registrarPresupuesto(int id, String descripcion, double montoTotal, double montoGastado) throws ExcepcionPresupuesto {
        if (presupuestos.containsKey(id)) {
            throw new ExcepcionPresupuesto("Ya existe un presupuesto con el id " + id);
        }
        if (montoGastado > montoTotal) {
            throw new ExcepcionPresupuesto("No tenes tantas lukas mi rey");
        }
        presupuestos.put(id, new Presupuesto(id, descripcion, montoTotal, montoGastado));
        System.out.println("Presupuesto " + id + " registrado con éxito");
    }


    // Obtener un presupuesto por ID
    public Presupuesto obtenerPresupuesto(int id) throws ExcepcionPresupuesto {
        Presupuesto presupuesto1 = presupuestos.get(id);
        if (presupuesto1 == null) {
            throw new ExcepcionPresupuesto("No se encontro ningún presupuesto con dicha ID" +id) ;
        }
        return presupuesto1;
    }

    // Eliminar un presupuesto
    public void eliminarPresupuesto(int id) throws ExcepcionPresupuesto {
        if(!presupuestos.containsKey(id)) {
            throw new ExcepcionPresupuesto("No existe un presupuesto con ese id");
        }
        presupuestos.remove(id);
        System.out.println("Presupuesto " + id + " eliminado");
    }

    // Listar todos los presupuestos
    public List<Presupuesto> listarPresupuestos(){
        return new ArrayList<>(presupuestos.values());
    }
}
