package com.admincli.modelos

import com.admincli.interfaces.Imprimible

/**
 * Clase abstracta base que representa a un usuario del sistema.
 * Define las propiedades comunes: identificador y nombre completo.
 * Implementa [Imprimible] para proporcionar un resumen de datos.
 *
 * @property id Identificador único del usuario.
 * @property nombreCompleto Nombre completo del usuario.
 */

abstract class Usuario(
    val id: Int,
    val nombreCompleto: String
) : Imprimible {
    /**
     * Constructor secundario que asigna un nombre por defecto.
     */
    constructor(id: Int) : this(id, "Sin Nombre")
    open override fun resumenDatos() : String {
        return "id: $id, nombre: $nombreCompleto "
    }
}