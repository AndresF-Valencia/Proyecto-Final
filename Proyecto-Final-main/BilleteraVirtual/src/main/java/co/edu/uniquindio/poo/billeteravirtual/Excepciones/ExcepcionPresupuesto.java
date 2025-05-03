package co.edu.uniquindio.poo.billeteravirtual.Excepciones;

public class ExcepcionPresupuesto extends Exception {

    //Constructor
    public ExcepcionPresupuesto(String mensaje) {
        super(mensaje);
    }

    //Constructor
    public ExcepcionPresupuesto(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
