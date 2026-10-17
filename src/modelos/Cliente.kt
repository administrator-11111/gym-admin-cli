package modelos
class Cliente(
    private val suscripcionActual:Suscripcion
){
    fun esMoroso():Boolean
}