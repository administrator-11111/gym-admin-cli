package modelos

class Cliente(
    private val suscripcionActual:Suscripcion
):Usuario(id,nombreCompleto){
    fun getFecha2(): Fecha {
    return fecha2
    }
    fun esMoroso():Boolean{
        val fechatermino= suscripcionActual.getFecha2()
    }
}