package com.admincli.modelos
import java.time.LocalDate

class Suscripcion(
    private val tipoPlan: String,
    private val costo: Double,
    private val fechaInicio: Fecha,
    private var fechaFin: Fecha
) {
    
    fun obtenerTipoPlan() : String {
        return this.tipoPlan
    }

    fun obtenerCosto() : Double {
        return this.costo
    }

    fun obtenerFechaInicio() : Fecha {
        return this.fechaInicio
    }

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