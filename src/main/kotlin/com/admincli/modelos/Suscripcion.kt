package com.admincli.modelos

import com.admincli.modelos.Fecha
/**
 * Representa el plan contratado por un cliente del gimnasio.
 *
 * Almacena el tipo de plan, su costo y las fechas de inicio y finalización.
 *
 * @property tipoPlan Nombre del plan, por ejemplo, mensual, trimestral o anual.
 * @property costo Costo del plan contratado.
 * @property fechaInicio Fecha de inicio de la suscripción.
 * @property fechaFin Fecha de finalización de la suscripción.
 */
class Suscripcion(
    private val tipoPlan: String,
    private val costo: Double,
    private val fechaInicio: Fecha,
    private val fechaFin: Fecha
) {
    constructor(tipoPlan: String, costo: Double) : this(
        tipoPlan,
        costo,
        Fecha(),
        calcularFechaFin(tipoPlan)
    )

     /**
     * Devuelve el nombre del plan contratado.
     *
     * @return Tipo de plan de la suscripción.
     */
    fun obtenerTipoPlan() : String = this.tipoPlan

    /**
     * Devuelve el costo del plan contratado.
     *
     * @return Costo almacenado en la suscripción.
     */
    fun obtenerCosto() : Double = this.costo

  /**
     * Devuelve la fecha de inicio de la suscripción.
     *
     * @return Referencia al objeto [Fecha] almacenado, sin crear una copia.
     */
    fun obtenerFechaInicio() : Fecha = this.fechaInicio

  /**
     * Devuelve la fecha de finalización de la suscripción.
     *
     * @return Referencia al objeto [Fecha] almacenado, sin crear una copia.
     */
    fun obtenerFechaFin() : Fecha = this.fechaFin


    companion object {

        fun duracionMeses(tipoPlan: String): Int {
            return when (tipoPlan.trim().lowercase()) {
                "mensual" -> 1
                "trimestral" -> 3
                "anual" -> 12
                else -> throw IllegalArgumentException("Plan no válido: '$tipoPlan'. Válidos: Mensual, Trimestral, Anual.")
            }
        }


        private fun calcularFechaFin(tipoPlan: String): Fecha {
            val hoy = Fecha()
            val meses = duracionMeses(tipoPlan)
            val termino = hoy.aLocalDate().plusMonths(meses.toLong())
            return Fecha(termino.dayOfMonth, termino.monthValue, termino.year)
        }
    }
}