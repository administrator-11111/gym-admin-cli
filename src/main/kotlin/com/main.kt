package com.admincli

import com.admincli.core.Gestor
import com.admincli.interfaces.Menu

/**
 * Punto de entrada principal del sistema de administración de gimnasio.
 * Carga los datos de administradores y clientes desde archivos CSV y ejecuta el REPL.
 */
fun main() {
    println("==================================================")
    println("    SISTEMA DE ADMINISTRACIÓN DE GIMNASIO (CLI)   ")
    println("==================================================")

    Gestor.cargarAdministradores("data/administradores.csv")
    Gestor.cargarDatos("data/usuarios.csv")

    val menu = Menu()
    menu.ejecutar()
}