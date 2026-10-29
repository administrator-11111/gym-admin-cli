package com.admincli.modelos

/**
 * Representa a un cliente del gimnasio.
 * Hereda de [Usuario] y tiene asociada una [Suscripcion].
 *
 * @property suscripcionActual La suscripción vigente del cliente.
 * @constructor Crea un cliente con ID, nombre y suscripción.
 */
class Cliente(
    // Atributos heraddos de clase Usuario
    id: Int,
    nombreCompleto: String,

    // Atributo privado
    private val suscripcionActual: Suscripcion,
    private var activo: Boolean = true
) : Usuario(id, nombreCompleto) {

    /**
     * Constructor secundario que crea un cliente con una suscripción mensual por defecto.
     * @param id Identificador único del cliente.
     * @param nombreCompleto Nombre completo del cliente.
     */
    constructor(id: Int, nombreCompleto: String) : this (
        id,
        nombreCompleto,
        suscripcionActual = Suscripcion(tipoPlan = "Mensual", costo = 0.0)
    )

    fun estaActivo(): Boolean = activo
    fun activar() { activo = true }
    fun desactivar() { activo = false }

    /**
     * Verifica si la suscripción del cliente ha caducado.
     * @return true si la fecha actual es posterior a la fecha de fin de la suscripción.
     */
    fun esMoroso() : Boolean {
        val fechaActual = java.time.LocalDate.now()
        return fechaActual.isAfter(suscripcionActual.obtenerFechaFin().aLocalDate())
    }

    /**
     * Obtiene el costo de la suscripción actual.
     * @return Costo como Double.
     */
    fun obtenerCostoSuscripcion(): Double {
        return suscripcionActual.obtenerCosto()
    }
    fun obtenerTipoPlan(): String = suscripcionActual.obtenerTipoPlan()
    fun obtenerFechaInicio(): Fecha = suscripcionActual.obtenerFechaInicio()
    fun obtenerFechaFin(): Fecha = suscripcionActual.obtenerFechaFin()

    override fun resumenDatos() : String {
        val estado = if (activo) "Activo" else "Inactivo"
        return "${super.resumenDatos()}(Plan: ${suscripcionActual.obtenerTipoPlan()} | Desde: ${suscripcionActual.obtenerFechaInicio()} | Hasta: ${suscripcionActual.obtenerFechaFin()} | Estado: $estado)"
    }

    fun toCsvRow(): String {
        return "$id,\"$nombreCompleto\",${suscripcionActual.obtenerTipoPlan()},${suscripcionActual.obtenerCosto()},${suscripcionActual.obtenerFechaInicio()},${suscripcionActual.obtenerFechaFin()}"
    }
}


