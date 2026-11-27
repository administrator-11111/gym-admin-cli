package com.gymadmin.app.ui.demo

import com.gymadmin.app.modelos.Cliente
import com.gymadmin.app.modelos.Fecha
import com.gymadmin.app.modelos.Suscripcion

/**
 * DATOS DEMOSTRATIVOS — no son información real del gimnasio.
 *
 * Sirven solo para que las pantallas tengan algo que mostrar en esta primera etapa.
 * No se leen ni se escriben en los CSV de la carpeta data/.
 * Se reutilizan las clases del negocio (Cliente, Suscripcion, Fecha) del módulo shared,
 * así no se duplican modelos. En una etapa posterior se reemplazarán por los datos del Gestor.
 */
object DatosDemostrativos {

    /** Valores fijos para las tarjetas del Dashboard (no se calculan). */
    val indicadores: List<IndicadorDemo> = listOf(
        IndicadorDemo("Clientes registrados", "5", "Texto ejemplo"),
        IndicadorDemo("Suscripciones activas", "3", "Texto ejemplo"),
        IndicadorDemo("Suscripciones vencidas", "5", "Texto ejemplo"),
    )

    val clientes: List<Cliente> = listOf(
        Cliente(1, "Bruno Faundez", Suscripcion("Mensual", 20000.0, Fecha(1, 11, 2026), Fecha(1, 12, 2026))),
        Cliente(2, "Jose Miguel", Suscripcion("Trimestral", 55000.0, Fecha(15, 9, 2026), Fecha(15, 12, 2026))),
        Cliente(3, "Ivan Cardenas", Suscripcion("Anual", 180000.0, Fecha(10, 2, 2026), Fecha(10, 2, 2027))),
        Cliente(4, "Hugo Araya", Suscripcion("Mensual", 20000.0, Fecha(5, 8, 2026), Fecha(5, 9, 2026))),
        Cliente(5, "Francisco Vasquez", Suscripcion("Trimestral", 55000.0, Fecha(1, 6, 2026), Fecha(1, 9, 2026))),
    )
}

/** Dato de ejemplo para una tarjeta del Dashboard. */
data class IndicadorDemo(val titulo: String, val valor: String, val detalle: String)
