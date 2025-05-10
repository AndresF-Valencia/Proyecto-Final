package co.edu.uniquindio.poo.billeteravirtual.entidades;

public class Cuenta {
    private Usuario usuario;
    private String IdCuenta;
    private String numeroCuenta;
    private String tipoCuenta;
    private String bancoCuenta;
    public double saldo;

    //Contructor De Cuenta
    public Cuenta(String IdCuenta, String numeroCuenta, String tipoCuenta, String bancoCuenta, Usuario usuario) {
        this.usuario = usuario;
        this.IdCuenta = IdCuenta;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.bancoCuenta = bancoCuenta;
        this.saldo = 1000000.0;
    }
    //Getters y Setter de la cuenta
    public String getIdCuenta() {
        return IdCuenta;
    }
    public void setIdCuenta(String IdCuenta) {
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
    public String getBancoCuenta() {
        return bancoCuenta;
    }
    public Cuenta setBancoCuenta(String bancoCuenta) {
        this.bancoCuenta = bancoCuenta;
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Cuenta setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public String getSaldo() {
        return String.valueOf(saldo);
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo1() {
        return saldo;
    }

    @Override
    public String toString() {
        return bancoCuenta + "-" + numeroCuenta;
    }

    public void agregarSaldo(double cantidad) {
        this.saldo += cantidad;
    }

}
