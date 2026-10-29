package com.admincli.core

import com.admincli.modelos.Cliente
import com.admincli.modelos.Administrador
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha
import com.admincli.interfaces.Imprimible
import java.io.File

/**
 * Gestor principal del sistema de administración del gimnasio.
 * Implementado como Singleton (object) para garantizar una única instancia
 * compartida por todo el sistema (REPL, carga de datos, operaciones).
 *
 * @see [Patrón Singleton](https://refactoring.guru/design-patterns/singleton)
 */

object Gestor {
    /**
     * Lista mutable que almacena todos los clientes registrados en el sistema.
     */
    private val clientes: MutableList<Cliente> = mutableListOf()
    /**
     * Lista mutable que almacena los administradores con acceso al sistema.
     */
    private val administradores: MutableList<Administrador> = mutableListOf()

    /**
     * Agrega un cliente a la lista en memoria sin reescribir el CSV.
     * Usado internamente durante la carga inicial de datos.
     */
    private fun agregarClienteEnMemoria(cliente: Cliente): Boolean {
        if (clientes.any { it.id == cliente.id }) return false
        clientes.add(cliente)
        return true
    }
    /**
     * Carga clientes desde un archivo CSV.
     * Formato esperado: Id,"Nombre Completo",Suscripcion,Costo,FechaInicio,FechaFin
     * @param rutaArchivo Ruta al archivo CSV.
     */
    fun cargarDatos(rutaArchivo: String) {
        val archivo = File(rutaArchivo)
        if (!archivo.exists()) {
            println("Error: archivo no encontrado en $rutaArchivo")
            return
        }

        val lineas: List<String> = archivo.readLines()

        for (i in 1 until lineas.size) { // 1 ... Nos saltamos el encabezado
            try {
                // Formato que lee ahora: Id,"Nombre Completo",Suscripcion,Costo,FechaInicio,FechaFin
                val linea: String = lineas[i]
                val campos: MutableList<String> = mutableListOf<String>()
                var actual: String = ""
                var dentroComillas: Boolean = false

                for (c: Char in linea) {
                    when {
                        c == '"' -> dentroComillas = !dentroComillas
                        c == ',' && !dentroComillas -> {
                            campos.add(actual.trim())
                            actual = ""
                        }
                        else -> actual += c
                    }
                }
                campos.add(actual.trim())

                if (campos.size < 6) continue

                val id: Int = campos[0].toInt()
                val nombreCompleto: String = campos[1]
                val tipoPlan: String = campos[2]
                val costo: Double = campos[3].toDouble()

                val fechaInicioParts: List<String> = campos[4].split("/")
                val fechaInicio: Fecha = Fecha(
                    fechaInicioParts[0].toInt(),
                    fechaInicioParts[1].toInt(),
                    fechaInicioParts[2].toInt()
                )

                val fechaFinParts: List<String> = campos[5].split("/")
                val fechaFin: Fecha = Fecha(
                    fechaFinParts[0].toInt(),
                    fechaFinParts[1].toInt(),
                    fechaFinParts[2].toInt()
                )

                val suscripcion: Suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
                val cliente: Cliente = Cliente(id, nombreCompleto, suscripcion)

                agregarClienteEnMemoria(cliente)
            } catch (e: Exception) {
                println("Advertencia: Error al procesar línea ${i + 1}: ${e.message}")
            }
        }
        println("Se cargaron ${clientes.size} clientes desde $rutaArchivo")
    }

    /**
     * Registra un administrador en el sistema.
     * @param admin Instancia de [Administrador] a registrar.
     */
    fun registrarAdministrador(admin: Administrador) {
        administradores.add(admin)
    }

    /**
     * Valida las credenciales ingresadas contra la lista de administradores autorizados.
     *
     * @param usuario El nombre de usuario del administrador.
     * @param clave La contraseña del administrador.
     * @return `true` si las credenciales coinciden con un administrador registrado, `false` en caso contrario.
     */
    // Métodos (+)
    fun iniciarSesion(usuario: String, clave: String): Boolean {
        val valido = administradores.any { admin -> admin.validarCredenciales(usuario, clave) }

        if (valido) println("Sesión iniciada")
        else println("Credenciales invalidas")

        return valido
    }

    /**
     * Inscribe un nuevo cliente agregándolo a la lista del sistema.
     *
     * @param cliente Instancia de [Cliente] que contiene los datos del usuario y su suscripción.
     * @return `true` si es posible inscribir al cliente
     */
    fun inscribirCliente(cliente: Cliente) : Boolean {
        if (clientes.any { it.id == cliente.id }) {
            println("Error: Ya existe un cliente con ID ${cliente.id}")
            return false
        }
        clientes.add(cliente)
        reescribirCsv()
        return true
    }

    /**
     * Busca y elimina un cliente del sistema utilizando su identificador único.
     *
     * @param id El identificador (ID) numérico del cliente a eliminar.
     * @return `true` si el cliente fue encontrado y eliminado con éxito, `false` si el ID no existe.
     */
    fun quitarCliente(id : Int) : Boolean{
        val cliente = clientes.find { it.id == id }
        return if (cliente != null) {
            clientes.remove(cliente)
            println("Cliente eliminado: ${cliente.resumenDatos()}")
            reescribirCsv()
            true
        } else {
            println("No se encontró un cliente con ID $id")
            false
        }
    }
    /**
     * Muestra en la consola un listado con el resumen de datos de todos los clientes registrados.
     * Si la lista está vacía, notifica que no hay clientes.
     */
    fun listarClientes() {
        if (clientes.isEmpty()) {
            println("No hay clientes registrados.")
            return
        }
        for (cliente in clientes) {
            println(cliente.resumenDatos())
        }
    }

    /**
     * Obtiene el siguiente ID disponible leyendo la última línea del archivo CSV.
     * También valida contra los clientes en memoria para evitar colisiones.
     */
    fun obtenerSiguienteId(rutaArchivo: String = "data/usuarios.csv"): Int {
        val archivo = File(rutaArchivo)
        var ultimoIdCsv = 0

        if (archivo.exists()) {
            val lineas = archivo.readLines()
            // Recorremos desde la última línea hacia atrás buscando una línea válida que no sea el encabezado
            for (i in lineas.indices.reversed()) {
                val linea = lineas[i].trim()
                if (linea.isNotEmpty() && i > 0) {
                    val idStr = linea.split(",")[0].trim()
                    val idParseado = idStr.toIntOrNull()
                    if (idParseado != null) {
                        ultimoIdCsv = idParseado
                        break
                    }
                }
            }
        }

        // Si ya hay clientes inscritos en la sesión actual con ID mayor, usamos el más alto
        val maxIdMemoria = clientes.maxOfOrNull { it.id } ?: 0
        val maxIdFinal = maxOf(ultimoIdCsv, maxIdMemoria)

        return maxIdFinal + 1
    }

    /**
     * Busca un cliente por su ID numérico.
     * @return El [Cliente] encontrado o null si no existe.
     */
    fun buscarCliente(id: Int): Cliente? {
        return clientes.find { it.id == id }
    }

    /**
     * Reemplaza un cliente existente por una versión actualizada de sus datos.
     */
    fun actualizarCliente(clienteActualizado: Cliente): Boolean {
        val indice = clientes.indexOfFirst { it.id == clienteActualizado.id }
        return if (indice != -1) {
            clientes[indice] = clienteActualizado
            println("Cliente actualizado exitosamente: ${clienteActualizado.resumenDatos()}")
            reescribirCsv()
            true
        } else {
            println("No se encontró el cliente con ID ${clienteActualizado.id}")
            false
        }
    }

    /**
     * Sincroniza la lista de clientes en memoria con el archivo CSV.
     */
    fun reescribirCsv(rutaArchivo: String = "data/usuarios.csv") {
        val archivo = File(rutaArchivo)
        try {
            archivo.printWriter().use { escritor ->
                escritor.println("Id,Nombre Completo,Suscripcion,Costo,FechaInicio,FechaFin")
                for ((index, c) in clientes.withIndex()) {
                    val sub = c.obtenerSuscripcion()
                    val linea = "${c.id},\"${c.nombreCompleto}\",${sub.obtenerTipoPlan()},${sub.obtenerCosto()},${sub.obtenerFechaInicio()},${sub.obtenerFechaFin()}"
                    
                    // Si es el último cliente, usamos print() para no dejar la línea en blanco al final
                    if (index == clientes.size - 1) {
                        escritor.print(linea)
                    } else {
                        escritor.println(linea)
                    }
                }
            }
        } catch (e: Exception) {
            println("Advertencia: No se pudo actualizar el CSV: ${e.message}")
        }
    }

    /**
     * Filtra y muestra en la consola el listado de clientes cuya suscripción ha expirado.
     *
     * Determina el estado evaluando la función `esMoroso()` de cada [Cliente].
     */
    fun mostrarMorosos() : Unit {
        var hayMorosos = false
        for (cliente in clientes){
            if (cliente.esMoroso()) {
                hayMorosos = true
                println("${cliente.resumenDatos()}")
            }
        }
        if (!hayMorosos) {
            println("No hay clientes morosos")
        }
    }

    /**
     * Calcula la suma total del costo de las suscripciones de todos los clientes actuales.
     *
     * @return El monto total recaudado (`Double`) en función a las suscripciones registradas.
     */
    fun calcularGanancias():Double{
        var ganancia:Double = 0.0
        for (cliente in clientes){
            ganancia += cliente.obtenerCostoSuscripcion()
        }
        return ganancia 
    }
}