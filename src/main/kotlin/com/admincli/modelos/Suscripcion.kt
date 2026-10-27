package com.admincli.modelos
import java.time.LocalDate

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
    private var fechaFin: Fecha
) {
    
     /**
     * Devuelve el nombre del plan contratado.
     *
     * @return Tipo de plan de la suscripción.
     */
    fun obtenerTipoPlan() : String {
        return this.tipoPlan
    }

    /**
     * Devuelve el costo del plan contratado.
     *
     * @return Costo almacenado en la suscripción.
     */
    fun obtenerCosto() : Double {
        return this.costo
    }

  /**
     * Devuelve la fecha de inicio de la suscripción.
     *
     * @return Referencia al objeto [Fecha] almacenado, sin crear una copia.
     */
    fun obtenerFechaInicio() : Fecha {
        return this.fechaInicio
    }

  /**
     * Devuelve la fecha de finalización de la suscripción.
     *
     * @return Referencia al objeto [Fecha] almacenado, sin crear una copia.
     */
    fun obtenerFechaFin() : Fecha {
        return this.fechaFin
    }
    constructor(tipoPlan: String, costo: Double) : this(
        tipoPlan,
        costo,
        Fecha(
            java.time.LocalDate.now().dayOfMonth,
            java.time.LocalDate.now().monthValue,
            java.time.LocalDate.now().year
        ),
        calcularFechaFin(tipoPlan)
    )

    companion object {
        private fun calcularFechaFin(tipoPlan: String): Fecha {
            val hoy = java.time.LocalDate.now()

            val meses = when (tipoPlan) {
                "Mensual" -> 1
                "Trimestral" -> 3
                "Anual" -> 12
                else -> throw IllegalArgumentException("Plan no válido")
            }

            val termino = hoy.plusMonths(meses.toLong())

            return Fecha(
                termino.dayOfMonth,
                termino.monthValue,
                termino.year
            )
        }
    }
}