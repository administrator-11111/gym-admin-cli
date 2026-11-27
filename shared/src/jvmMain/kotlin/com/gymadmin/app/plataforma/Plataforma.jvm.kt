package com.gymadmin.app.plataforma

import java.io.File
import java.time.LocalDate

// Implementación para escritorio (JVM) de las funciones declaradas en commonMain.
// Mantiene exactamente el comportamiento original de GymAdminCLI (java.time y java.io.File).

internal actual fun fechaActualDelSistema(): Triple<Int, Int, Int> {
    val hoy = LocalDate.now()
    return Triple(hoy.dayOfMonth, hoy.monthValue, hoy.year)
}

internal actual fun existeArchivo(ruta: String): Boolean = File(ruta).exists()

internal actual fun leerLineas(ruta: String): List<String> = File(ruta).readLines()

internal actual fun escribirLineas(ruta: String, lineas: List<String>) {
    val archivo = File(ruta)
    archivo.parentFile?.mkdirs()
    archivo.printWriter().use { writer ->
        lineas.forEach { writer.println(it) }
    }
}
