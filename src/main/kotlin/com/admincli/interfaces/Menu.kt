package com.admincli.interfaces

import com.admincli.core.Gestor
import com.admincli.modelos.Cliente
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha
import java.io.File
import java.math.BigDecimal //Para mostrar las ganacias con el numero completo

/**
 * Clase que gestiona la interfaz interactiva (REPL) del sistema.
 */
class Menu {

    private fun limpiarConsola(){
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    private fun mostrarMenu(){
        println("""
        ==================================
        SISTEMA DE ADMINISTRACIÓN GIMNASIO
        ==================================
        [1] Ver lista de usuarios
        [2] Inscribir usuario
        [3] Revisar usuarios morosos
        [4] Ver ganancias
        [5] Salir del sistema
        """.trimIndent())
        print("Opción>> ")
    }

    fun ejecutar(){
        var opcion: Int
        do {
            limpiarConsola()
            mostrarMenu()
            opcion = readln().toIntOrNull() ?: 0
            try {
                when(opcion){
                    1 -> gestionarListaClientes()
                    2 -> inscribirClienteDesdeConsola()
                    3 -> {
                        println("=== Clientes morosos ===")
                        Gestor.mostrarMorosos()
                    }
                    4 -> {
                        val ganancias = BigDecimal(Gestor.calcularGanancias()).toPlainString()
                        println("=== Ganancias totales: $$ganancias ===")
                    }
                    5 -> println("Saliendo del sistema...")
                    else -> println("Opción inválida, elige una opción del 1 al 5.")
                }
            }catch(e: Exception){
                println("Error inesperado: ${e.message}")
            }
            if(opcion != 5){
                println("\nPresione Enter para continuar...")
                readln()
            }
        }while(opcion != 5)
    }

    /**
     * Muestra los clientes y ofrece el submenú de Seleccionar o Volver atrás.
     */
    private fun gestionarListaClientes(){
        limpiarConsola()
        println("=== Lista de clientes registrados ===")
        Gestor.listarClientes()
        println("-------------------------------------")
        println("[1] Seleccionar usuario")
        println("[2] Volver atrás")
        print("Opción>> ")

        val subOpcion = readln().toIntOrNull() ?: 2
        if (subOpcion == 1){
            print("\nIngrese ID del usuario a seleccionar: ")
            val id = readln().toIntOrNull()
            if(id == null) {
                println("ID no válido.")
                return
            }

            val cliente = Gestor.buscarCliente(id)
            if (cliente == null) {
                println("No se encontró ningún cliente con ID $id.")
                return
            }

            println("\nUsuario seleccionado:")
            println(cliente.resumenDatos())
            println("-------------------------------------")
            println("[1] Editar usuario")
            println("[2] Quitar usuario")
            println("[3] Volver atrás")
            print("Opción>> ")

            when (readln().toIntOrNull() ?: 3) {
                1 -> editarClientePasoAPaso(cliente)
                2 -> {
                    print("¿Está seguro de eliminar a este cliente? (s/n): ")
                    if (readln().trim().lowercase() == "s") {
                        Gestor.quitarCliente(cliente.id)
                    } else {
                        println("Operación cancelada.")
                    }
                }
                else -> println("Operación cancelada.")
            }
        }
    }

    /**
     * Edita los campos paso a paso. Si el usuario presiona Enter vacío, se conserva el valor actual.
     */
    private fun editarClientePasoAPaso(cliente: Cliente) {
        println("\n=== Editando Usuario ID #${cliente.id} ===")
        println("(Presione ENTER sin escribir nada para conservar el valor actual)\n")

        // 1. Nombre
        print("Nombre actual [${cliente.nombreCompleto}]: ")
        val entradaNombre = readln().trim()
        val nuevoNombre = if (entradaNombre.isNotEmpty()) entradaNombre else cliente.nombreCompleto

        // 2. Tipo de plan
        val subActual = cliente.obtenerSuscripcion()
        print("Plan actual [${subActual.obtenerTipoPlan()}] (Mensual/Trimestral/Anual): ")
        val entradaPlan = readln().trim()
        val nuevoPlan = if (entradaPlan.isNotEmpty()) entradaPlan else subActual.obtenerTipoPlan()

        // 3. Costo
        print("Costo actual [${subActual.obtenerCosto()}]: ")
        val entradaCosto = readln().trim()
        val nuevoCosto = if (entradaCosto.isNotEmpty()) entradaCosto.toDoubleOrNull() ?: subActual.obtenerCosto() else subActual.obtenerCosto()

        // 4. Fechas (Día, Mes, Año de inicio)
        val fInicioActual = subActual.obtenerFechaInicio()
        println("\n-- Fecha de Inicio actual: $fInicioActual --")
        
        print("Día de inicio: ")
        val entradaDia = readln().trim()
        
        print("Mes de inicio: ")
        val entradaMes = readln().trim()
        
        print("Año de inicio: ")
        val entradaAnio = readln().trim()

        val diaFinal = entradaDia.toIntOrNull() ?: fInicioActual.toString().split("/")[0].toInt()
        val mesFinal = entradaMes.toIntOrNull() ?: fInicioActual.toString().split("/")[1].toInt()
        val anioFinal = entradaAnio.toIntOrNull() ?: fInicioActual.toString().split("/")[2].toInt()

        val nuevaFechaInicio = Fecha(diaFinal, mesFinal, anioFinal)
        val nuevaFechaFin = Fecha(diaFinal, mesFinal, anioFinal)

        // Recalcular la fecha de fin según el plan seleccionado
        when(nuevoPlan){
            "Mensual" -> nuevaFechaFin.sumarMeses(1)
            "Trimestral" -> nuevaFechaFin.sumarMeses(3)
            "Anual" -> nuevaFechaFin.sumarMeses(12)
            else -> {
                println("Plan desconocido, se mantendrá la fecha de fin anterior.")
            }
        }

        val nuevaSuscripcion = Suscripcion(nuevoPlan, nuevoCosto, nuevaFechaInicio, nuevaFechaFin)
        val clienteModificado = Cliente(cliente.id, nuevoNombre, nuevaSuscripcion)

        Gestor.actualizarCliente(clienteModificado)
    }

    private fun inscribirClienteDesdeConsola() {
        try {
            val id: Int = Gestor.obtenerSiguienteId()
            println("ID asignado automáticamente: $id")
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

            val fechaInicio = Fecha(dia, mes, anio)
            val fechaFin = Fecha(dia, mes, anio)
            when(tipoPlan){
                "Mensual" -> fechaFin.sumarMeses(1)
                "Trimestral" -> fechaFin.sumarMeses(3)
                "Anual" -> fechaFin.sumarMeses(12)
                else -> {
                    println("Error: tipo de plan no válido. Use Mensual, Trimestral o Anual.")
                    return
                }
            }

            val suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
            val cliente = Cliente(id, nombre, suscripcion)

            val registrado = Gestor.inscribirCliente(cliente)
            if(registrado){
                //val ultimoId = File("data/usuarios.csv").appendText("\n$id,\"$nombre\",$tipoPlan,$costo,$fechaInicio,$fechaFin")
                println("Cliente inscrito exitosamente: ${cliente.resumenDatos()}")
            }
        } catch (e: NumberFormatException) {
            println("Error: Ingrese un valor numérico válido.")
        } catch (e: Exception) {
            println("Error al inscribir cliente: ${e.message}")
        }
    }
}