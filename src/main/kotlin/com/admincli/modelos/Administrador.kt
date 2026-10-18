package com.admincli.modelos

class Administrador(
    // Atributos heredados
    id: Int,
    nombreCompleto: String,

    // Atributos privados
    private val nombreUsuario: String,
    private val clave: String
) : Usuario(id, nombreCompleto) { // Hereda usuario

    fun validarCredenciales(usuario: String, clave: String): Boolean {
        return (this.nombreUsuario == usuario && this.clave == clave)
    }
}