package com.admincli.modelos

import com.admincli.interfaces.Imprimible

abstract class Usuario(
    val id:Int ,
    val nombreCompleto:String
) : Imprimible {
    // usamos open para extender el método a Cliente y Administrador
    open override fun resumenDatos() : String {
        return "id: $id, nombre: $nombreCompleto"
    }
}