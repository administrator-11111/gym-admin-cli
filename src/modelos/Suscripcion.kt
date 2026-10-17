package modelos
class Suscripcion(
    private val tipoPlan:String,
    private val costo:Double,
    private val fecha1:Fecha,
    private val fecha2:Fecha
)
 fun getFecha2(): Fecha {
    return fecha2
    }
     fun getFecha1(): Fecha {
    return fecha1
    }
    fun renovar(meses:Int):Unit{
    
}