package com.admincli.modelos

class Cliente(
    // Atributos heraddos de clase Usuario
    id: Int,
    nombreCompleto: String,

    // Atributo privado
    private val suscripcionActual: Suscripcion
) : Usuario(id, nombreCompleto) {

    fun esMoroso() : Boolean {
        // Consultar con el profe si se puede usar java.time :3
        val fechaActual = java.time.LocalDate.now()
        return fechaActual.isAfter(suscripcionActual.obtenerFechaFin().aLocalDate())
    }

    override fun resumenDatos() : String {
        return "${super.resumenDatos()}(Plan: ${suscripcionActual.obtenerTipoPlan()} | Finaliza: ${suscripcionActual.obtenerFechaFin()})"
    }
}