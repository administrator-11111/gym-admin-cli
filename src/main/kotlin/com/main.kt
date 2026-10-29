package com.admincli

import com.admincli.core.Gestor
import com.admincli.modelos.Cliente
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha
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

    val menu: Menu = Menu()
    menu.ejecutar()
}


/* Código anterior(No borrar aun :p )

    gestor.inscribirCliente(Cliente(1, "Juan Pérez", Suscripcion("Mensual", 20000.0,  Fecha(1, 10, 2026),  Fecha(30, 10, 2026))))
    gestor.inscribirCliente(Cliente(2, "María González", Suscripcion("Mensual", 20000.0,  Fecha(1, 9, 2026),  Fecha(30, 9, 2026)))) // Moroso
    gestor.inscribirCliente(Cliente(3, "Pedro Soto", Suscripcion("Mensual", 20000.0,  Fecha(15, 10, 2026),  Fecha(15, 11, 2026))))
    gestor.inscribirCliente(Cliente(4, "Ana Rojas", Suscripcion("Mensual", 22000.0, Fecha(1, 8, 2026),  Fecha(31, 8, 2026)))) // Moroso
    gestor.inscribirCliente(Cliente(5, "Luis Muñoz", Suscripcion("Mensual", 20000.0, Fecha(5, 10, 2026), Fecha(5, 11, 2026))))

    // --- CLIENTES CON PLAN TRIMESTRAL ---
    gestor.inscribirCliente(Cliente(6, "Carla Díaz", Suscripcion("Trimestral", 50000.0, Fecha(1, 10, 2026),  Fecha(31, 12, 2026))))
    gestor.inscribirCliente(Cliente(7, "Diego Contreras", Suscripcion("Trimestral", 50000.0,  Fecha(1, 7, 2026),  Fecha(30, 9, 2026)))) // Moroso
    gestor.inscribirCliente(Cliente(8, "Sofía Silva", Suscripcion("Trimestral", 55000.0,  Fecha(10, 9, 2026),  Fecha(10, 12, 2026))))
    gestor.inscribirCliente(Cliente(9, "Andrés Sepúlveda", Suscripcion("Trimestral", 50000.0,  Fecha(1, 10, 2026),  Fecha(31, 12, 2026))))
    gestor.inscribirCliente(Cliente(10, "Valentina Morales", Suscripcion("Trimestral", 50000.0,  Fecha(20, 10, 2026),Fecha(20, 1, 2027))))
    gestor.inscribirCliente(Cliente(11, "Matías Castro", Suscripcion("Trimestral", 50000.0,  Fecha(1, 6, 2026),  Fecha(31, 8, 2026)))) // Moroso
    gestor.inscribirCliente(Cliente(12, "Camila Herrera", Suscripcion("Trimestral", 52000.0,  Fecha(15, 10, 2026),  Fecha(15, 1, 2027))))

    // --- CLIENTES CON PLAN ANUAL ---
    gestor.inscribirCliente(Cliente(13, "Sebastián Fuentes", Suscripcion("Anual", 180000.0,  Fecha(1, 1, 2026), Fecha(31, 12, 2026))))
    gestor.inscribirCliente(Cliente(14, "Javiera Araya", Suscripcion("Anual", 180000.0,  Fecha(1, 10, 2026),  Fecha(30, 9, 2027))))
    gestor.inscribirCliente(Cliente(15, "Felipe Gutiérrez", Suscripcion("Anual", 180000.0,  Fecha(15, 3, 2026),  Fecha(15, 3, 2027))))
    gestor.inscribirCliente(Cliente(16, "Daniela Torres", Suscripcion("Anual", 190000.0,  Fecha(1, 5, 2026),  Fecha(30, 4, 2027))))
    gestor.inscribirCliente(Cliente(17, "Cristóbal Espinoza", Suscripcion("Anual", 180000.0, Fecha(1, 1, 2025),  Fecha(31, 12, 2025)))) // Moroso
    gestor.inscribirCliente(Cliente(18, "Constanza Valdés", Suscripcion("Anual", 180000.0,  Fecha(1, 11, 2026),  Fecha(31, 10, 2027))))
    gestor.inscribirCliente(Cliente(19, "Ignacio Riquelme", Suscripcion("Anual", 180000.0,  Fecha(1, 8, 2026), Fecha(31, 7, 2027))))
    gestor.inscribirCliente(Cliente(20, "Fernanda Pizarro", Suscripcion("Anual", 200000.0,  Fecha(1, 10, 2026),  Fecha(30, 9, 2027))))

    gestor.mostrarMorosos()
 */