package com.admincli.core

import com.admincli.modelos.Cliente
import com.admincli.modelos.Administrador
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha
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
     * Carga clientes desde un archivo CSV.
     * Formato esperado: Nombre,Apellido,Suscripcion TipoCosto,Dia/Mes/Anio
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

                val suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
                val cliente = Cliente(idContador, nombreCompleto, suscripcion)
                val suscripcion: Suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
                val cliente: Cliente = Cliente(id, nombreCompleto, suscripcion)

                inscribirCliente(cliente)
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
     */
    fun inscribirCliente(cliente: Cliente) : Unit {
        if (clientes.any { it.id == cliente.id }) {
            println("Error: Ya existe un cliente con ID ${cliente.id}")
            return
        }
        clientes.add(cliente)
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
     * Lista todos los usuarios del sistema (clientes y administradores)
     * usando polimorfismo a través de la interfaz Imprimible.
     */
    fun listarTodosLosUsuarios() {
        val usuarios: List<Imprimible> = clientes + administradores
        if (usuarios.isEmpty()) {
            println("No hay usuarios registrados.")
            return
        }
        for (usuario: Imprimible in usuarios) {
            println(usuario.resumenDatos())
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