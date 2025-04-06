package co.edu.uniquindio.poo.billeteravirtual.Excepciones;

public class ExcepcionUsuario extends Exception {

    // Constructor
    public ExcepcionUsuario(String mensaje) {
        super(mensaje);
    }

    // Constructor
    public ExcepcionUsuario(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
