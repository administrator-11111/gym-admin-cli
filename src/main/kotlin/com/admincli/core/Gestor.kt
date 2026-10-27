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
        val archivo = File("../../../../../../data/usuarios.csv")
        if (!archivo.exists()) {
            println("Error: archivo no encontrado en $rutaArchivo")
            return
        }

        val lineas = archivo.readLines()
        var idContador = 1
        for (i in 1 until lineas.size) { // 1 ... Nos saltamos el encabezado
            try {
                val campos = lineas[i].split(",")
                if (campos.size < 4) continue

                val nombreCompleto = "${campos[0].trim()} ${campos[1].trim()}"
                val suscripcionParts = campos[2].trim().split(" ")
                val tipoPlan = suscripcionParts[0]
                val costo = suscripcionParts[1].toDouble()
                val fechaParts = campos[3].trim().split("/")
                val dia = fechaParts[0].toInt()
                val mes = fechaParts[1].toInt()
                val anio = fechaParts[2].toInt()

                val fechaInicio = Fecha(dia, mes, anio)
                val fechaFin = Fecha(dia, mes, anio)
                // Calcular fecha fin según tipo de plan
                when (tipoPlan) {
                    "Mensual" -> fechaFin.sumarMeses(1)
                    "Trimestral" -> fechaFin.sumarMeses(3)
                    "Anual" -> fechaFin.sumarMeses(12)
                }

                val suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
                val cliente = Cliente(idContador, nombreCompleto, suscripcion)
                inscribirCliente(cliente)
                idContador++
            } catch (e: Exception) {
                println("Advertencia: Error al procesar línea ${i + 1}: ${e.message}")
            }
        }
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
    fun inscribirCliente(cliente:Cliente) : Unit{
        clientes.add(cliente)
    }

    /**
     * Busca y elimina un cliente del sistema utilizando su identificador único.
     *
     * @param id El identificador (ID) numérico del cliente a eliminar.
     * @return `true` si el cliente fue encontrado y eliminado con éxito, `false` si el ID no existe.
     */
    fun quitarCliente(cliente:Cliente) : Unit{
        val cliente = clientes.find { it.id == id }
        clientes.remove(cliente)
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
            ganancia += cliente.suscripcionActual.obtenerCosto
        }
        return ganancia 
    }
}