package co.edu.uniquindio.poo.billeteravirtual.Excepciones;

public class ExcepcionCuenta extends  Exception {

    //Constructor
    public ExcepcionCuenta(String mensaje) {
        super(mensaje);
    }

    //Constructor
    public ExcepcionCuenta(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }

}
