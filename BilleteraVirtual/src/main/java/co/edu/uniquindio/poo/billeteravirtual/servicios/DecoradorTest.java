package co.edu.uniquindio.poo.billeteravirtual.servicios;

import co.edu.uniquindio.poo.billeteravirtual.decoradores.PresupuestoConAhorro;
import co.edu.uniquindio.poo.billeteravirtual.decoradores.PresupuestoConLimite;
import co.edu.uniquindio.poo.billeteravirtual.decoradores.PresupuestoDecorator;
import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class DecoradorTest {
    public static void main(String[] args) {
        // Crear presupuesto base
        Presupuesto presupuestoBase = new Presupuesto(1, "Comida", 1000.0, 0);

        // Crear decorador con ahorro
        PresupuestoDecorator conAhorro = new PresupuestoConAhorro(presupuestoBase);

        // Encadenar decoradores: Limite sobre Ahorro
        PresupuestoDecorator conLimiteYAhorro = new PresupuestoConLimite(conAhorro, 500);

        // Agregar gasto a la versión con límite y ahorro
        conLimiteYAhorro.agregarGasto(100);

        // Imprimir resultados
        System.out.println("Gastado: " + conLimiteYAhorro.getMontoGastado());
        System.out.println("Descripción: " + conLimiteYAhorro.getDescripcion());
    }
}
