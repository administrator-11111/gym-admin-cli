package com.gymadmin.app.plataforma

/*
 * Funciones que dependen de la plataforma (expect).
 *
 * El código común (commonMain) no puede usar APIs de Java como java.io.File o java.time.
 * Por eso aquí solo se DECLARA lo que se necesita, y cada plataforma lo IMPLEMENTA (actual).
 * La implementación para escritorio está en jvmMain/.../plataforma/Plataforma.jvm.kt.
 */

/** Fecha actual del sistema como (día, mes, año). */
internal expect fun fechaActualDelSistema(): Triple<Int, Int, Int>

/** Indica si existe un archivo en la ruta indicada. */
internal expect fun existeArchivo(ruta: String): Boolean

/** Lee todas las líneas de un archivo de texto (UTF-8). */
internal expect fun leerLineas(ruta: String): List<String>

/** Escribe las líneas en un archivo de texto (UTF-8), creando las carpetas necesarias. */
internal expect fun escribirLineas(ruta: String, lineas: List<String>)
