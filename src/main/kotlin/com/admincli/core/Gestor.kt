package com.admincli.core

import com.admincli.modelos.Cliente
import com.admincli.modelos.Administrador

class Gestor {
    private val clientes: MutableList<Cliente> = mutableListOf()
    private val administradores: MutableList<Administrador> = mutableListOf()

    // Métodos (+)
    fun iniciarSesion(usuario: String, clave: String): Boolean {
        // .any() devuelve verdadero si al menos un admin cumple cumple la condición
        val valido = administradores.any { admin -> admin.validarCredenciales(usuario, clave) }

        if (valido) println("Sesión iniciada")
        else println("Credenciales invalidas")

        return valido
    }

    fun inscribirCliente(cliente:Cliente) : Unit{
        clientes.add(cliente)
    }

    fun quitarCliente(cliente:Cliente) : Unit{
        cliente.remove(cliente)
    }

    fun mostrarMorosos() : Unit {
        val hayMorosos = false
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

    fun calcularGanancias():Double

}