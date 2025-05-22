package co.edu.uniquindio.poo.billeteravirtual.model.observer;

/**
 * Interfaz que notifica cambios al observador.
 */
public interface Observador {

    /**
     * Método llamado cuando ocurre una actualización general en el sujeto observado.
     * Puede usarse para refrescar gráficos, tablas, o cualquier otro componente visual.
     */
    void actualizar();

    /**
     * Método llamado específicamente cuando hay un cambio en el saldo.
     * Útil para mantener sincronizadas etiquetas, campos de saldo u otros elementos relacionados.
     */
    void actualizarSaldo();
}

