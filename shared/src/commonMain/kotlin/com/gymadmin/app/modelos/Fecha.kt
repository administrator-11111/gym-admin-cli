package com.gymadmin.app.modelos

import com.gymadmin.app.plataforma.fechaActualDelSistema

/**
 * Clase que representa una fecha con día, mes y año.
 * Proporciona operaciones para manipular y comparar fechas.
 *
 * Está escrita en Kotlin común (sin `java.time`) para poder vivir en `commonMain`.
 * Solo la obtención de la fecha actual depende de la plataforma.
 *
 * @property dia Día del mes (1-31).
 * @property mes Mes del año (1-12).
 * @property anio Año.
 */
class Fecha(
    private var dia: Int,
    private var mes: Int,
    private var anio: Int
) : Comparable<Fecha> {
    init {
        require(mes in 1..12) { "Mes inválido: $mes (debe ser entre 1 y 12)" }
        require(dia in 1..31) { "Día inválido: $dia (debe ser entre 1 y 31)" }
        require(anio >= 1900) { "Año inválido: $anio" }
        // Reemplaza a LocalDate.of(...): verifica que el día exista en ese mes (incluye años bisiestos).
        require(dia <= diasDelMes(mes, anio)) { "Fecha inexistente: $dia/$mes/$anio" }
    }

    /**
     * Constructor secundario que crea una Fecha con la fecha actual del sistema.
     */
    constructor() : this(fechaActualDelSistema())

    private constructor(partes: Triple<Int, Int, Int>) : this(partes.first, partes.second, partes.third)

    /**
     * Suma una cantidad de meses a esta fecha, actualizando día, mes y año.
     *
     * Se comporta igual que `LocalDate.plusMonths`: si el día no existe en el mes
     * resultante (por ejemplo, 31 de enero + 1 mes), se usa el último día de ese mes.
     *
     * @param n Número de meses a sumar.
     */
    fun sumarMeses(n: Int) {
        val totalMeses = anio * 12 + (mes - 1) + n
        val nuevoAnio = totalMeses.floorDiv(12)
        val nuevoMes = totalMeses.mod(12) + 1
        this.dia = minOf(dia, diasDelMes(nuevoMes, nuevoAnio))
        this.mes = nuevoMes
        this.anio = nuevoAnio
    }

    /**
     * Compara cronológicamente dos fechas (permite usar `<`, `>`, etc.).
     * Reemplaza a la conversión a `LocalDate` que se usaba para comparar.
     */
    override fun compareTo(other: Fecha): Int =
        compareValuesBy(this, other, { it.anio }, { it.mes }, { it.dia })

    /**
     * Devuelve la fecha en formato "dia/mes/anio".
     * @return String con formato dd/mm/yyyy.
     */
    override fun toString(): String = "$dia/$mes/$anio"

    companion object {
        /**
         * Crea una instancia de [Fecha] a partir de una cadena de texto con el formato `dia/mes/anio`.
         *
         * Los espacios en blanco al inicio y al final de la cadena son ignorados automáticamente.
         *
         * @param cadena La representación en texto de la fecha (por ejemplo, `"29/10/2026"`).
         * @return Una nueva instancia de [Fecha] con el día, mes y año extraídos.
         * @throws IllegalArgumentException Si la [cadena] no contiene exactamente tres partes separadas por `/`.
         * @throws NumberFormatException Si alguno de los componentes (día, mes o año) no es un número entero válido.
         */
        fun desdeString(cadena: String): Fecha {
            val partes = cadena.trim().split("/")
            require(partes.size == 3) { "Formato de fecha inválido: '$cadena'. Se esperaba dia/mes/anio." }
            return Fecha(partes[0].toInt(), partes[1].toInt(), partes[2].toInt())
        }

        private fun esBisiesto(anio: Int): Boolean =
            (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0

        private fun diasDelMes(mes: Int, anio: Int): Int = when (mes) {
            2 -> if (esBisiesto(anio)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }
}
