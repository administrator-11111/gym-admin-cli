package com.admincli.interfaces

/**
 * Interfaz que define la capacidad de generar un resumen textual de datos.
 * Implemntada por todas las clases que representatn entidades del sitema.
 */
interface Imprimible {
    /**
     * Devuelve una representación textual resumida del objeto
     * @return String con los datos principales del objeto
     */
    fun resumenDatos():String
}

