package co.edu.uniquindio.poo.billeteravirtual.utilidades;

import java.util.Random;

public class GeneradorCodigo {
    public String generarCodigo() {
        Random random = new Random();
        int numero = 1000 + random.nextInt(9000); // Genera número entre 1000 y 9999
        return Integer.toString(numero);
    }

}
