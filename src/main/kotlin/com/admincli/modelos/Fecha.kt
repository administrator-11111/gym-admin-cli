package com.admincli.modelos

import java.time.LocalDate

/**
 * Clase que representa una fecha con día, mes y año.
 * Proporciona operaciones para manipular fechas y convertir
 * a [LocalDate] para comparaciones.
 *
 * @property dia Día del mes (1-31).
 * @property mes Mes del año (1-12).
 * @property anio Año.
 */
class Fecha(
    private var dia: Int,
    private var mes: Int,
    private var anio: Int
) {
    init {
        require(mes in 1..12) { "Mes inválido: $mes (debe ser entre 1 y 12)" }
        require(dia in 1..31) { "Día inválido: $dia (debe ser entre 1 y 31)" }
        require(anio >= 1900) { "Año inválido: $anio" }
        LocalDate.of(anio, mes, dia) // esto verifica años bisiestos :) https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/kotlinx.datetime/-local-date/-local-date.html
    }

    /**
     * Constructor secundario que crea una Fecha con la fecha actual del sistema.
     */
    constructor(): this(
        LocalDate.now().dayOfMonth,
        LocalDate.now().monthValue,
        LocalDate.now().year
    )

    /**
     * Convierte esta Fecha a un objeto [LocalDate] de Java para comparaciones.
     * @return Objeto LocalDate equivalente.
     */
    fun aLocalDate(): LocalDate = LocalDate.of(anio, mes, dia)

    /**
     * Suma una cantidad de meses a esta fecha, actualizando día, mes y año.
     * @param n Número de meses a sumar.
     */
    fun sumarMeses(n: Int) {
        val nuevaFecha: LocalDate = aLocalDate().plusMonths(n.toLong())
        this.dia = nuevaFecha.dayOfMonth
        this.mes = nuevaFecha.monthValue
        this.anio = nuevaFecha.year
    }

    /**
     * Devuelve la fecha en formato "dia/mes/anio".
     * @return String con formato dd/mm/yyyy.
     */
    override fun toString(): String = "$dia/$mes/$anio"

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
    companion object {
        fun desdeString(cadena: String): Fecha {
            val partes = cadena.trim().split("/")
            require(partes.size == 3) { "Formato de fecha inválido: '$cadena'. Se esperaba dia/mes/anio." }
            return Fecha(partes[0].toInt(), partes[1].toInt(), partes[2].toInt())
        }
    }
}