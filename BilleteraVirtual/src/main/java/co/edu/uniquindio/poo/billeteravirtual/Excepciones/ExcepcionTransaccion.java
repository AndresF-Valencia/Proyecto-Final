package co.edu.uniquindio.poo.billeteravirtual.Excepciones;

public class ExcepcionTransaccion extends Exception {

    //Constructor
    public ExcepcionTransaccion(String mensaje) {
        super(mensaje);
    }

    //Constructor
    public ExcepcionTransaccion(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
