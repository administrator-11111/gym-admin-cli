package com.gymadmin.app.cli

import com.gymadmin.app.core.Gestor

/**
 * Punto de entrada principal del sistema de administración de gimnasio.
 * Carga los datos de administradores y clientes desde archivos CSV y ejecuta el REPL.
 *
 * Versión de consola original de GymAdminCLI, conservada como referencia.
 * Se ejecuta desde el IDE (botón ▶ junto a main) con la raíz del proyecto como directorio de trabajo,
 * para que encuentre la carpeta data/. La aplicación gráfica está en com.gymadmin.app.main.kt.
 */
fun main() {
    print("==================================================")
    print("    SISTEMA DE ADMINISTRACIÓN DE GIMNASIO (CLI)   ")
    print("==================================================")

    Gestor.cargarAdministradores("data/administradores.csv")
    Gestor.cargarDatos("data/usuarios.csv")

    val menu = Menu()
    menu.ejecutar()
}