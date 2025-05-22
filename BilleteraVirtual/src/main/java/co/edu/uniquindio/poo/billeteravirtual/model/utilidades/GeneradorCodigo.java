package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;

import java.util.Random;

public class GeneradorCodigo {

    /**
     * Genera un código numérico aleatorio de 4 dígitos.
     * El código estará entre 1000 y 9999 inclusive.
     *
     * @return Código generado como cadena de texto.
     */
    public String generarCodigo() {
        Random random = new Random();
        int numero = 1000 + random.nextInt(9000); // Número entre 1000 y 9999
        return Integer.toString(numero);
    }
}
