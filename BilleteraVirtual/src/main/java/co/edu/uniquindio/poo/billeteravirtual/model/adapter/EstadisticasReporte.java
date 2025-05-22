package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

/**
 * Clase que encapsula estadísticas generales del sistema.
 * Incluye el usuario más activo (con más transacciones) y
 * la categoría de transacción más usada.
 */
public class EstadisticasReporte {
    /**
     * Usuario con más transacciones registradas en el sistema.
     */
    public Usuario usuarioMasActivo;

    /**
     * Categoría de transacción más utilizada.
     */
    public String categoriaMasUsada;

    public Usuario getUsuarioMasActivo() {
        return usuarioMasActivo;
    }

    public EstadisticasReporte setUsuarioMasActivo(Usuario usuarioMasActivo) {
        this.usuarioMasActivo = usuarioMasActivo;
        return this;
    }

    public String getCategoriaMasUsada() {
        return categoriaMasUsada;
    }

    public EstadisticasReporte setCategoriaMasUsada(String categoriaMasUsada) {
        this.categoriaMasUsada = categoriaMasUsada;
        return this;
    }
}
