package co.edu.uniquindio.poo.billeteravirtual.Excepciones;

public class ExcepcionCategoria extends Exception {

    //Constructor
    public ExcepcionCategoria(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }

    //Constructor
    public ExcepcionCategoria(String mensaje) {
        super(mensaje);
    }
}
