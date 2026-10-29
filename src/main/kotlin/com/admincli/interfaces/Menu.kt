package com.admincli.interfaces

import com.admincli.core.Gestor
import com.admincli.modelos.Cliente
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha
import com.admincli.modelos.Administrador

/**
 * Representa la interfaz de usuario en consola (CLI) para la administración del gimnasio.
 *
 * Esta clase gestiona la interacción con el usuario administrador, incluyendo el proceso
 * de autenticación, la visualización del menú principal y la captura de datos por consola
 * para ejecutar las operaciones del sistema a través de [Gestor].
 */
class Menu {

    /**
     * Limpia la pantalla de la consola utilizando códigos de escape ANSI y vacía el flujo de salida.
     */
    private fun limpiarConsola() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    /**
     * Solicita las credenciales de acceso al usuario mediante la consola con un límite de 3 intentos.
     *
     * Valida el nombre de usuario y la contraseña ingresados contra el sistema mediante [Gestor.iniciarSesion].
     *
     * @return `true` si la autenticación es exitosa dentro de los intentos permitidos;
     * `false` si se agotan los 3 intentos fallidos.
     */
    fun solicitarAutenticacion(): Boolean {
        println("==================================================")
        println("         ACCESO AL SISTEMA - GYM ADMIN CLI        ")
        println("==================================================")

        var intentos = 3
        while (intentos > 0) {
            print("Usuario: ")
            val usuario = readln().trim()
            print("Contraseña: ")
            val clave = readln().trim()

            val adminAutenticado: Administrador? = Gestor.iniciarSesion(usuario, clave)
            if (adminAutenticado != null) {
                println("\n¡Bienvenido, ${adminAutenticado.nombreCompleto}!")
                println("Presione Enter para ingresar al menú principal...")
                readln()
                return true
            } else {
                intentos--
                if (intentos > 0) {
                    println("Credenciales incorrectas. Intentos restantes: $intentos\n")
                } else {
                    println("Ha superado el número máximo de intentos. Acceso bloqueado por seguridad.")
                }
            }
        }
        return false
    }

    /**
     * Imprime en la consola las opciones disponibles del panel de administración.
     */
    private fun mostrarMenu() {
        println("""
        ==================================================
                     PANEL DE ADMINISTRACIÓN
        ==================================================
        [1] Ver lista de todos los usuarios
        [2] Inscribir nuevo cliente
        [3] Quitar cliente
        [4] Revisar clientes morosos
        [5] Buscar cliente (ID/Nombre)
        [6] Ver métricas financieras del gimnasio
        [7] Salir del sistema
        ==================================================
        """.trimIndent())
        print("Opción >> ")
    }

    /**
     * Inicia el flujo principal de la interfaz de consola.
     *
     * Primero verifica la identidad del operador mediante [solicitarAutenticacion]. Si tiene éxito,
     * mantiene un bucle interactivo que muestra el menú, procesa la opción seleccionada y ejecuta
     * la acción correspondiente hasta que el usuario elija salir (opción 7), momento en el cual
     * sincroniza y guarda los datos en disco.
     */
    fun ejecutar() {
        if (!solicitarAutenticacion()) {
            return
        }

        var opcion: Int = 0
        do {
            limpiarConsola()
            mostrarMenu()
            opcion = readln().toIntOrNull() ?: 0
            try {
                when (opcion) {
                    1 -> {
                        println("=== Lista general de usuarios ===")
                        val usuarios = Gestor.obtenerTodosLosUsuarios()
                        if (usuarios.isEmpty()) {
                            println("No hay usuarios registrados.")
                        } else {
                            usuarios.forEach { println(it.resumenDatos()) }
                        }
                    }
                    2 -> inscribirClienteDesdeConsola()
                    3 -> quitarClienteDesdeConsola()
                    4 -> {
                        println("=== Clientes morosos ===")
                        val morosos = Gestor.obtenerMorosos()
                        if (morosos.isEmpty()) {
                            println("No hay clientes morosos en este momento.")
                        } else {
                            morosos.forEach { println(it.resumenDatos()) }
                        }
                    }
                    5 -> buscarClienteDesdeConsola()
                    6 -> mostrarMetricasDesdeConsola()
                    7 -> {
                        println("Sincronizando y guardando cambios en disco...")
                        Gestor.guardarDatos()
                        Gestor.guardarAdministradores()
                        println("Sesión cerrada exitosamente.")
                    }
                    else -> println("Opción inválida. Seleccione un número del 1 al 7.")
                }
            } catch (e: Exception) {
                println("Error inesperado en la operación: ${e.message}")
            }

            if (opcion != 7) {
                println("\nPresione Enter para continuar...")
                readln()
            }
        } while (opcion != 7)
    }

    /**
     * Guía al usuario por consola para registrar un nuevo [Cliente] en el sistema.
     *
     * Genera un ID automático, solicita el nombre completo, el tipo de plan (Mensual, Trimestral o Anual)
     * y el costo de la suscripción. Calcula las fechas de vigencia usando [Fecha], crea las instancias
     * de [Suscripcion] y [Cliente], y las registra mediante [Gestor.inscribirCliente].
     * Captura y muestra errores de formato numérico o validaciones fallidas.
     */
    private fun inscribirClienteDesdeConsola() {
        try {
            val nuevoId = Gestor.generarSiguienteClienteId()
            println("--- Inscripción de Nuevo Cliente ---")
            println("ID asignado automáticamente: $nuevoId")

            print("Nombre completo: ")
            val nombre: String = readln().trim()
            require(nombre.isNotEmpty()) { "El nombre no puede estar vacío." }

            println("\nSeleccione el tipo de plan:")
            println("[1] Mensual (1 mes)")
            println("[2] Trimestral (3 meses)")
            println("[3] Anual (12 meses)")
            print("Opción de plan >> ")
            val opcionPlan = readln().trim()

            val (tipoPlan, mesesDuracion) = when (opcionPlan) {
                "1" -> "Mensual" to 1
                "2" -> "Trimestral" to 3
                "3" -> "Anual" to 12
                else -> {
                    println("Opción de plan inválida. Inscripción cancelada.")
                    return
                }
            }

            print("Costo de la suscripción ($): ")
            val costo: Double = readln().toDouble()
            require(costo >= 0.0) { "El costo no puede ser negativo." }

            val fechaInicio = Fecha() // Fecha actual
            val fechaFin = Fecha()
            fechaFin.sumarMeses(mesesDuracion)

            val suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
            val nuevoCliente = Cliente(nuevoId, nombre, suscripcion)

            val registrado = Gestor.inscribirCliente(nuevoCliente)
            if (registrado) {
                println("\n✓ Cliente inscrito exitosamente: ${nuevoCliente.resumenDatos()}")
            }
        } catch (e: NumberFormatException) {
            println("Error: Formato numérico incorrecto.")
        } catch (e: Exception) {
            println("Error al inscribir cliente: ${e.message}")
        }
    }

    /**
     * Gestiona la eliminación de un cliente existente a través de la consola.
     *
     * Solicita el ID del cliente, verifica su existencia con [Gestor.buscarClientePorId] y pide
     * confirmación explícita al usuario antes de proceder con la baja definitiva mediante [Gestor.quitarCliente].
     */
    private fun quitarClienteDesdeConsola() {
        try {
            print("Ingrese el ID del cliente que desea dar de baja: ")
            val id: Int = readln().toInt()
            val cliente = Gestor.buscarClientePorId(id)
            if (cliente == null) {
                println("No se encontró ningún cliente con el ID $id.")
                return
            }

            println("\nCliente localizado: ${cliente.resumenDatos()}")
            print("¿Está seguro de que desea eliminar a este cliente permanentemente? (s/n): ")
            val confirmacion = readln().trim().lowercase()

            if (confirmacion == "s" || confirmacion == "si" || confirmacion == "sí") {
                val eliminado = Gestor.quitarCliente(id)
                if (eliminado) {
                    println("✓ Cliente con ID $id eliminado exitosamente.")
                } else {
                    println("No se pudo eliminar al cliente.")
                }
            } else {
                println("Operación de baja cancelada por el usuario.")
            }
        } catch (e: NumberFormatException) {
            println("Error: Debe ingresar un ID numérico válido.")
        }
    }

    /**
     * Solicita un criterio de búsqueda por consola (ID o fragmento del nombre) e imprime
     * la lista de clientes coincidentes utilizando [Gestor.buscarCliente].
     */
    private fun buscarClienteDesdeConsola() {
        print("Ingrese ID o parte del nombre a buscar: ")
        val criterio = readln().trim()
        if (criterio.isEmpty()) {
            println("Criterio de búsqueda vacío.")
            return
        }

        val resultados = Gestor.buscarCliente(criterio)
        if (resultados.isEmpty()) {
            println("No se encontraron clientes que coincidan con '$criterio'.")
        } else {
            println("=== Resultados encontrados (${resultados.size}) ===")
            resultados.forEach { println(it.resumenDatos()) }
        }
    }

    /**
     * Obtiene las estadísticas operativas y financieras desde [Gestor.obtenerMetricas] y las
     * presenta formateadas en la consola (total de socios, activos, morosos, tasa de morosidad,
     * recaudación acumulada y ticket promedio).
     */
    private fun mostrarMetricasDesdeConsola() {
        val metricas = Gestor.obtenerMetricas()
        println("""
        ==================================================
               MÉTRICAS FINANCIERAS Y DE SOCIOS
        ==================================================
        - Total de clientes registrados: ${metricas["total"]}
        - Clientes al día (activos):     ${metricas["activos"]}
        - Clientes morosos:              ${metricas["morosos"]}
        - Tasa de morosidad:             ${"%.2f".format(metricas["tasaMorosidad"])}%
        --------------------------------------------------
        - Recaudación total acumulada:   $${"%.2f".format(metricas["ganancias"])}
        - Ticket promedio por socio:     $${"%.2f".format(metricas["ticketPromedio"])}
        ==================================================
        """.trimIndent())
    }
}