package co.edu.uniquindio.poo.billeteravirtual.datos;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

public class PresupuestoManager {
    private static final String ARCHIVO_PRESUPUESTO = "presupuesto.properties";

    // Método para obtener el presupuesto cargado desde el archivo de propiedades
    public static Presupuesto cargarPresupuesto() {
        return DataPersistence.loadPresupuesto();
    }

    // Método para guardar el presupuesto en el archivo de propiedades
    public static void guardarPresupuesto(Presupuesto presupuesto) {
        DataPersistence.savePresupuesto(presupuesto);
    }

    // Método que puede realizar acciones adicionales, como actualizar o modificar el presupuesto
    public static void actualizarPresupuesto(Presupuesto presupuesto, double gasto) {
        presupuesto.setMontoGastado(presupuesto.getMontoGastado() + gasto);
        guardarPresupuesto(presupuesto);
    }
}
