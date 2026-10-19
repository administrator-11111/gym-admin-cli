package com.admincli.interfaces
/*
ver lista de usuarios
    seleccionar usuarios
    volver atras
inscribir usuarios
quitar usuarios
revisar usuarios morosos
ver ganancias
salir del sistema
*/

interface Imprimible {
    fun resumenDatos():String

    fun Menu(){
        print("""
            [1] ver lista de usuarios
            [2] inscribir usuarios
            [3] quitar usuarios
            [4] revisar usuarios morosos
            [5] ver ganancias
            [6] salir del sistema
        """)
    }
}