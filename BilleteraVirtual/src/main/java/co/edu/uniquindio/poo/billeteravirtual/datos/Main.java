package co.edu.uniquindio.poo.billeteravirtual.datos;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class Main {
    public static void main(String[] args) {
        // Cargar el presupuesto desde el archivo de propiedades
        Presupuesto presupuestoCargado = PresupuestoManager.cargarPresupuesto();

        if (presupuestoCargado == null) {
            // Si no se cargó, crear uno nuevo
            presupuestoCargado = new Presupuesto(1, "Comida", 1000.0, 200.0);
            PresupuestoManager.guardarPresupuesto(presupuestoCargado);
        }

        // Mostrar los datos cargados
        System.out.println("ID Presupuesto: " + presupuestoCargado.getIdPresupuesto());
        System.out.println("Descripción: " + presupuestoCargado.getDescripcion());
        System.out.println("Monto Total: " + presupuestoCargado.getMontoTotal());
        System.out.println("Monto Gastado: " + presupuestoCargado.getMontoGastado());

        // Ejemplo de actualización de presupuesto
        PresupuestoManager.actualizarPresupuesto(presupuestoCargado, 50.0);
        System.out.println("\nNuevo monto gastado tras actualización: " + presupuestoCargado.getMontoGastado());
    }
}

