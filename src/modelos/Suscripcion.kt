package modelos
class Suscripcion(
    private val tipoPlan:String,
    private val costo:Double,
    val fecha1:Fecha,
    val fecha2:Fecha
)
    
    fun renovar(meses:Int):Unit{
    
}