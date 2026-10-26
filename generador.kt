import java.io.File
import java.time.LocalDate
import com.admincli.modelos.Fecha

abstract class GeneradorBase(){
    open fun generar(): String {
        return ""
    }
}

class GenerarNombre : GeneradorBase(){
    private val nombre = listOf("Juan", "Lucy", "Martin", "Marcos", "Pierre", "Lucas",
        "Maria", "Carmen", "Estefania", "Luisa", "Alonso", "Ivan", "Haley", "Jose", "Josefa",
        "Lucho", "Mario", "Samuel","Amanda", "Consuelo", "Alejandra","Sonia", "Martina", "Emilia",
        "Gerardo", "Diego", "Lamine","Benjamin", "Diddy", "Philip", "Hugo","Bruno", 
        "Jeffrey", "Nayetli", "Anais", "Ignacia", "René", "Miguel", "Mauricio")
    private val apellido = listOf("Díaz", "Gonzales", "Mendez", "Torres", "Molina", "Sierra",
        "Grandon", "Leon", "Maldonado", "Retamal", "Quijano", "Canales", "Rios", "Lagos","Gutierrez", 
        "Solis", "Pérez", "Montecino", "López", "Yamal", "Opazo", "Kast", "Vasquez","Netanyahu","Trujillo",
        "Bachelet", "Araya", "Epstein", "Faúndez", "Valenzuela", "Puente", "Devia", "Paredes")
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

class FechasSuscripcion : GeneradorBase(){
    override fun generar(): String{
        val anio = (2020..2026).random()
        val mes = (1..12).random()

        val maxDias = LocalDate.of(anio, mes, 1).lengthOfMonth()
        val dia = (1..maxDias).random()

        val fecha = Fecha(dia, mes, anio)

        return fecha.toString()
    }
}

fun main(){
    val cantidad = 100
    val nombre = GenerarNombre()
    val suscripcion = GenerarSuscripcion()
    val fecha = FechasSuscripcion()

    val archivo = File("data/usuarios.csv")
    archivo.parentFile.mkdirs()
    println(archivo.exists())
    archivo.printWriter().use { escritor ->
        escritor.println("Nombre,Apellido,Suscripcion,FechaSuscripcion")

        repeat(cantidad){
            val datos = nombre.generar().split(" ")
            val tipo = suscripcion.generar()
            val fechaSuscripcion = fecha.generar()

            escritor.println("${datos[0]},${datos[1]},$tipo,$fechaSuscripcion")
        }
    }

    println("Archivo generado correctamente.")
    println(archivo.absolutePath)
}