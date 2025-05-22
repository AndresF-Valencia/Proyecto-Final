package co.edu.uniquindio.poo.billeteravirtual.model.decoradores;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Presupuesto;

import java.util.List;

/**
 * Interfaz que define las operaciones relacionadas con la gestión de presupuestos
 * para un usuario, incluyendo verificación y registro de gastos.
 */
public interface IPresupuesto {

    /**
     * Agrega un nuevo presupuesto al usuario.
     *
     * @param presupuesto El presupuesto que se desea agregar.
     */
    public void agregarPresupuesto(Presupuesto presupuesto);

    /**
     * Obtiene la lista de presupuestos asociados al usuario.
     *
     * @return Lista de presupuestos.
     */
    public List<Presupuesto> getPresupuestos();

    /**
     * Verifica si una transacción puede realizarse según el presupuesto disponible
     * en la categoría especificada.
     *
     * @param monto     Monto de la transacción.
     * @param categoria Categoría de la transacción.
     * @return true si la transacción puede realizarse, false en caso contrario.
     */
    public boolean sePuedeRealizarTransaccion(double monto, String categoria);

    /**
     * Registra un gasto en la categoría correspondiente, actualizando el monto gastado.
     *
     * @param monto     Monto del gasto.
     * @param categoria Categoría del gasto.
     */
    public void registrarGasto(double monto, String categoria);
}
