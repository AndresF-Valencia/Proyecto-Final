package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class Cuenta {
    private int IdCuenta;
    private String numeroCuenta;
    private String tipoCuenta;

    //Contructor De Cuenta
    public Cuenta(int IdCuenta, String numeroCuenta, String tipoCuenta) {
        this.IdCuenta = IdCuenta;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
    }
    //Getters y Setter de la cuenta
    public int getIdCuenta() {
        return IdCuenta;
    }
    public void setIdCuenta(int IdCuenta) {
        this.IdCuenta = IdCuenta;
    }
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "IdCuenta=" + IdCuenta +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                '}';
    }
}
