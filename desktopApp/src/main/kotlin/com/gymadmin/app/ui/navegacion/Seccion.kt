package com.gymadmin.app.ui.navegacion

/**
 * Secciones de la aplicación. Agregar una sección nueva = agregar un valor aquí
 * y su pantalla en GymAdminApp.
 *
 * @property titulo Texto que se muestra en la barra lateral y en el encabezado de la pantalla.
 * @property descripcion Breve explicación de la sección.
 * @property simbolo Letra que se usa como ícono (la plantilla no incluye la librería de íconos).
 */
enum class Seccion(
    val titulo: String,
    val descripcion: String,
    val simbolo: String,
) {
    DASHBOARD("Dashboard", "Resumen genral", "D"),
    CLIENTES("Clientes", "Clientes inscritos en el gimnasio", "C"),
    SUSCRIPCIONES("Suscripciones", "Planes contratados", "S"),
}
