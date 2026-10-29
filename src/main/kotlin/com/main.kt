package com.admincli

import com.admincli.core.Gestor
import com.admincli.modelos.Administrador
import com.admincli.interfaces.Menu

/**
 * Punto de entrada principal del sistema de administración de gimnasio.
 * Carga los datos iniciales desde el archivo CSV, registra al administrador
 * por defecto y ejecuta el sistema.
 */
fun main() {
    println("Sistema de Administración de Gimnasio")
    println("Cargando datos iniciales...")

    val listaAdmins: List<Administrador> = listOf(
        Administrador(1, "Iván Maldonado", "return", "1234"),
        Administrador(2, "Lucas Sierra", "lucas", "1234"),
        Administrador(3, "Alonso Lopez", "topsin", "1234"),
        Administrador(4, "Yeral Mendez", "yerbal", "1234")
    )

    listaAdmins.forEach { admin ->
        Gestor.registrarAdministrador(admin)
    }

    Gestor.cargarDatos("data/usuarios.csv")

    println("\n--- Inicio de Sesión ---")
    var sesionActiva: Boolean = false
    var usuario = ""
    do {
        print("Usuario: ")
        usuario = readln().trim()
        print("Clave: ")
        val clave: String = readln().trim()
        sesionActiva = Gestor.iniciarSesion(usuario, clave)
    } while (!sesionActiva)

    val menu: Menu = Menu(usuario)
    menu.ejecutar()
}