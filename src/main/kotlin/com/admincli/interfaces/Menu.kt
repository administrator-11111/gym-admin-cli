package com.admincli.interfaces

import com.admincli.core.Gestor
import com.admincli.modelos.Cliente
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha

/**
 * Clase que gestiona la interfaz interactiva (REPL) del sistema.
 * Muestra un menú con las operaciones disponibles y ejecuta las acciones
 * del usuario invocando los métodos del [Gestor] (Singleton).
 */
class Menu {

    /**
     * Limpia la consola usando códigos ANSI.
     */
    private fun limpiarConsola() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    /**
     * Muestra el menú principal del sistema.
     */
    private fun mostrarMenu(){
        println("""
        [1] ver lista de usuarios
        [2] inscribir usuarios
        [3] quitar usuarios
        [4] revisar usuarios morosos
        [5] ver ganancias
        [6] salir del sistema
    """.trimIndent())
        print("Opcion>> ")
    }

    /**
     * Bucle principal del REPL. Muestra el menú y ejecuta las operaciones
     * hasta que el usuario elija salir (opción 6).
     */
    fun ejecutar(){
        var opcion: Int = 0
        do{
            limpiarConsola()
            mostrarMenu()
            opcion = readln().toIntOrNull() ?: 0
            try {
                when(opcion){
                    1 -> {
                        println("=== Lista de clientes ===")
                        Gestor.listarClientes()
                    }
                    2 -> inscribirClienteDesdeConsola()
                    3 -> quitarClienteDesdeConsola()
                    4 -> {
                        println("=== Clientes morosos ===")
                        Gestor.mostrarMorosos()
                    }
                    5 -> {
                        val ganancias: Double = Gestor.calcularGanancias()
                        println("=== Ganancias totales: $$ganancias ===")
                    }
                    6 -> println("Saliendo del sistema...")
                    else -> println("Opcion invalida, elige una opcion del 1 al 6")
                }
            } catch (e: Exception) {
                println("Error inesperado: ${e.message}")
            }
            if (opcion != 6) {
                println("\nPresione Enter para continuar...")
                readln()
            }
        }while(opcion != 6)
    }

    /**
     * Solicita datos al usuario para inscribir un nuevo cliente.
     * Captura excepciones de formato numérico para evitar crashes.
     */
    private fun inscribirClienteDesdeConsola() {
        try {
            print("ID del cliente: ")
            val id: Int = readln().toInt()
            print("Nombre completo: ")
            val nombre: String = readln()
            print("Tipo de plan (Mensual/Trimestral/Anual): ")
            val tipoPlan: String = readln()
            print("Costo del plan: ")
            val costo: Double = readln().toDouble()
            print("Día de inicio (ej: 15): ")
            val dia: Int = readln().toInt()
            print("Mes de inicio (1-12): ")
            val mes: Int = readln().toInt()
            print("Año de inicio (ej: 2026): ")
            val anio: Int = readln().toInt()

            val fechaInicio: Fecha = Fecha(dia, mes, anio)
            val fechaFin: Fecha = Fecha(dia, mes, anio)
            when (tipoPlan) {
                "Mensual" -> fechaFin.sumarMeses(1)
                "Trimestral" -> fechaFin.sumarMeses(3)
                "Anual" -> fechaFin.sumarMeses(12)
                else -> {
                    println("Error: tipo de plan no válido. Use Mensual, Trimestral o Anual.")
                    return
                }
            }

            val suscripcion: Suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
            val cliente: Cliente = Cliente(id, nombre, suscripcion)
            Gestor.inscribirCliente(cliente)
            println("Cliente inscrito exitosamente: ${cliente.resumenDatos()}")
        } catch (e: NumberFormatException) {
            println("Error: Ingrese un valor numérico válido.")
        } catch (e: Exception) {
            println("Error al inscribir cliente: ${e.message}")
        }
    }

    /**
     * Solicita el ID de un cliente para eliminarlo del sistema.
     */
    private fun quitarClienteDesdeConsola() {
        try {
            print("ID del cliente a eliminar: ")
            val id: Int = readln().toInt()
            Gestor.quitarCliente(id)
        } catch (e: NumberFormatException) {
            println("Error: Ingrese un ID numérico válido.")
        }
    }
}