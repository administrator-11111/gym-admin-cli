package com.admincli.modelos

class Fecha (
    private val dia:Int,
    private val mes:Int,
    private val anio:Int
) {
    fun sumarMeses(n: Int) {
        // nose xd
    }

    // Muestra la fecha en formato Dia/Mes/Año
    override fun toString() : String {
        return "$dia/$mes/$anio"
    }
}