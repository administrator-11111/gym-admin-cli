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
    /**
     * Constructor secundario que crea una Fecha con la fecha actual del sistema.
     */
    constructor():this(
        LocalDate.now().dayOfMonth,
        LocalDate.now().monthValue,
        LocalDate.now().year
    )

    /**
     * Convierte esta Fecha a un objeto [LocalDate] de Java para comparaciones.
     * @return Objeto LocalDate equivalente.
     */
    fun aLocalDate(): LocalDate {
        return LocalDate.of(anio, mes, dia)
    }

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
    override fun toString(): String {
        return "$dia/$mes/$anio"
    }
}