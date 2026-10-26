package com.admincli.interfaces
import java.io.File
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

/* 
interface Imprimible {
    fun resumenDatos():String
}
*/

fun limpiarConsola() {
    print("\u001b[H\u001b[2J")
    System.out.flush()
}

fun mostrarMenu(){
    print("""
        [1] ver lista de usuarios
        [2] inscribir usuarios
        [3] quitar usuarios
        [4] revisar usuarios morosos
        [5] ver ganancias
        [6] salir del sistema
        Opcion>>
    """.trimIndent())
}

fun menu(){
    var opcion : Int
    do{
        limpiarConsola()
        mostrarMenu()
        opcion = readln().toIntOrNull() ?: 0
        when(opcion){
            1 -> println("1") // reemplazar por las funciones de visualizar y otros
            2 -> println("2")
            3 -> println("3")
            4 -> println("4")
            5 -> println("5")
            6 -> println("6")
            else -> println("Opcion invalida, elige una opcion valida")
        }
    }while(opcion != 6)
}

fun main(){
    //Estructura que tiene que leer
    //Nombre,Apellido,Suscripcion,FechaSuscripcion
    val archivo = File("../../../../../../data/usuarios.csv") //Vuelve al directorio raiz /data
    readline()


}