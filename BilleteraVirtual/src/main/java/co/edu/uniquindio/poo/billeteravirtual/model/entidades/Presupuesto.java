package co.edu.uniquindio.poo.billeteravirtual.model.entidades;

/**
 * Representa un presupuesto dentro del sistema.
 * Puede ser un presupuesto general o específico por categoría.
 */
public class Presupuesto implements Cloneable {
    private String idPresupuesto;
    private String nombre;
    private Double montoTotal;
    private double montoGastado;
    public boolean esGeneral;
    private String categoria;
    public static final String PRESUPUESTO_GENERAL = "General";

    /**
     * Constructor de la clase Presupuesto.
     *
     * @param idPresupuesto ID único del presupuesto.
     * @param nombre Nombre del presupuesto.
     * @param montoTotal Monto total asignado al presupuesto.
     * @param esGeneral Indica si el presupuesto es general.
     */
    public Presupuesto(String idPresupuesto, String nombre, Double montoTotal, boolean esGeneral) {
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.montoGastado = 0.0;
        this.esGeneral = esGeneral;
        this.categoria = "";
    }

    // Getters y setters documentados de forma similar

    /**
     * Clona el presupuesto actual.
     *
     * @return Una nueva instancia del presupuesto con los mismos valores.
     */
    @Override
    public Presupuesto clone() {
        try {
            return (Presupuesto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clonación fallida", e);
        }
    }

    @Override
    public String toString() {
        return "Presupuesto{" +
                "idPresupuesto=" + idPresupuesto +
                ", montoTotal=" + montoTotal +
                ", montoGastado=" + montoGastado +
                '}';
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public Presupuesto setIdPresupuesto(String idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Presupuesto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Presupuesto setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public Presupuesto setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
        return this;
    }

    public boolean isEsGeneral() {
        return esGeneral;
    }

    public Presupuesto setEsGeneral(boolean esGeneral) {
        this.esGeneral = esGeneral;
        return this;
    }

    public String getCategoria() {
        return categoria;
    }

    public Presupuesto setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }
}


