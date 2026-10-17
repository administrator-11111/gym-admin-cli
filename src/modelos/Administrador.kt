package modelos
import Gestor.kt
class Administrador(
    id:Int,
    nombreCompleto:String,
    private val nombreUsuario:String,
    private val clave:String
):Usuario(id,nombreCompleto){
    fun validarCredenciales(nombreIngresado:String,contraseñaIngresada:String):Boolean{
        return nombreIngresado == nombreUsuario && contraseñaIngresada == clave
    }
}