abstract class GeneradorBase(){
    open fun generar(): String {
        return ""
    }
}

class GenerarNombre : GeneradorBase(){
    private val nombre = listOf("Juan", "Lucy", "Martin", "Marcos", "Pierre", "Lucas",
        "Maria", "Carmen", "Estefania", "Luisa", "Alonso", "Ivan", "Haley", "Jose", "Josefa",
        "Lucho")
    private val apellido = listOf("Díaz", "Gonzales", "Mendez", "Torres", "Molina", "Sierra",
        "Grandon", "Leon", "Maldonado", "Retamal", "Quijano", "Canales", "Rios", "Lagos")
    override fun generar(): String{
        return nombre.random() + " " + apellido.random()
    }
}

class GenerarSuscripcion : GeneradorBase(){
    override fun generar(): String{
        val tipos = listOf("Mensual", "Trimestral", "Anual")
        var coste: List<Int> = listOf(2, 1)
        val suscripcion = tipos.random()
        if(suscripcion == "Mensual"){
            coste = listOf(20000, 25000, 30000)
        }else if(suscripcion == "Trimestral"){
                coste = listOf(70000, 90000, 110000)
            }else if(suscripcion == "Anual"){
                coste = listOf(180000, 190000, 200000)
            }
        return suscripcion + " " + coste.random()
    }
}

class FechasSuscripcion: GeneradorBase(){
    private val dia = (1..31).random()
    private val mes = (1..12).random()
    private val año = (2020.. 2026).random()

    override fun generar(): String{
        return "$dia, $mes, $año"
    }
}