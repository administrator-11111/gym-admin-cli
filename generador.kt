import java.io.File
import java.time.LocalDate
import com.admincli.modelos.Fecha

/**
 * Clase base para los generadores de datos aleatorios.
 *
 * Define el método generar(), que puede ser sobrescrito
 * por las clases que heredan de ella.
 */
abstract class GeneradorBase(){
    /**
     * Genera un dato en formato String.
     *
     * @return Cadena de texto generada.
     */
    open fun generar(): String {
        return ""
    }
}

/**
 * Generador de nombres y apellidos aleatorios.
 *
 * Selecciona un nombre y un apellido de dos listas
 * predefinidas para construir un nombre completo.
 */
class GenerarNombre : GeneradorBase(){
    private val nombre = listOf("Juan", "Lucy", "Martin", "Marcos", "Pierre", "Lucas",
        "Maria", "Carmen", "Estefania", "Luisa", "Alonso", "Ivan", "Haley", "Jose", "Josefa",
        "Lucho", "Mario", "Samuel","Amanda", "Consuelo", "Alejandra","Sonia", "Martina", "Emilia",
        "Gerardo", "Diego", "Lamine","Benjamin", "Diddy", "Philip", "Hugo","Bruno", 
        "Jeffrey", "Nayetli", "Anais", "Ignacia", "René", "Miguel", "Mauricio", "Sophia", "Mayte",
        "Antonio", "Marcos", "Paola", "Fernando", "Claudio", "Eduardo", "Neftalí", "Maximiliano",
        "Maximo", "Catalina","Javier", "Carlos", "Tomas", "Cesar", "Brayan", "Katerine", "Enrique",
        "Victor", "Cristiano", "Nicanor", "Aurora", "Abigail", "Sebastian", "Sergio", "Arturo", "Walter",
        "Vicente", "Gustavo", "Gabriel", "Mercedes", "Pamela", "Renata", "Silvia", "Mariana", "Antonia",
        "Marcela", "Isidora", "Julieta", "Trinidad", "Victoria", "Valentina")
    private val apellido = listOf("Díaz", "Gonzales", "Mendez", "Torres", "Molina", "Sierra",
        "Grandon", "Leon", "Maldonado", "Retamal", "Quijano", "Canales", "Rios", "Lagos","Gutierrez", 
        "Solis", "Pérez", "Montecino", "López", "Yamal", "Opazo", "Kast", "Vasquez","Netanyahu","Trujillo",
        "Bachelet", "Araya", "Faúndez", "Valenzuela", "Puente", "Devia", "Paredes", "Achú", "Jiménez",
        "Rojas", "Castro", "Barrios", "Fuentes", "Avila", "Moran", "Ronaldo", "Parra", "Leiva", "Piñera",
        "Quiroz", "Vidal", "Kirk", "Quijote", "Parada", "Manriquez", "Imas", "Morales", "Flores", "Alvarado",
        "Espinoza", "Durán", "Aguirre", "Ortega", "Dahmer", "Ramirez", "Cárdenas")
    /**
     * Genera un nombre completo aleatorio.
     *
     * @return Nombre y apellido separados por un espacio.
     */
    override fun generar(): String{
        return nombre.random() + " " + apellido.random()
    }
}

/**
 * Generador de suscripciones aleatorias para el gimnasio.
 *
 * Selecciona una duración de suscripción y un costo
 * asociado al tipo seleccionado.
 *
 * Tipos disponibles: Mensual, Trimestral y Anual.
 */
class GenerarSuscripcion : GeneradorBase(){
    /**
     * Genera una suscripción con su respectivo costo.
     *
     * @return Tipo de suscripción y precio separados
     *         por un espacio. Ejemplo: "Mensual 25000".
     */
    override fun generar(): String{
        val tipos = listOf("Mensual", "Trimestral", "Anual")
        var coste: List<Int> = listOf()
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

/**
 * Generador de fechas aleatorias para las suscripciones.
 *
 * Genera fechas válidas entre los años 2020 y 2026,
 * considerando la cantidad de días de cada mes
 * y los años bisiestos.
 */
class FechasSuscripcion : GeneradorBase(){
    override fun generar(): String{
        val anio = (2020..2026).random()
        val mes = (1..12).random()

        // Obtiene los días del mes, considerando años bisiestos.
        val maxDias = LocalDate.of(anio, mes, 1).lengthOfMonth()
        val dia = (1..maxDias).random()

        val fecha = Fecha(dia, mes, anio)

        return fecha.toString()
    }
}

/**
 * Genera usuarios aleatorios y los almacena en un archivo CSV.
 *
 * Cada registro contiene ID, nombre, apellido, tipo y costo
 * de suscripción, fecha de inicio y fecha de finalización.
 *
 * El archivo se guarda en data/usuarios.csv.
 * Si ya existe, su contenido se sobrescribe.
 */
fun main(){
    val cantidad = 200 

    val nombre = GenerarNombre()
    val suscripcion = GenerarSuscripcion()
    val fecha = FechasSuscripcion()

    // Prepara el archivo y crea su directorio si no existe.
    val archivo = File("data/usuarios.csv")
    archivo.parentFile.mkdirs()
    println(archivo.exists())
    archivo.printWriter().use { escritor ->
        escritor.println("Id,Nombre Completo,Suscripcion,Costo,FechaInicio,FechaFin")

        // Genera el nombre completo
        repeat(cantidad){ i ->
            val nombreCompleto = nombre.generar()

            // Obtiene el tipo de suscripción y su costo.
            val datosSuscripcion = suscripcion.generar().split(" ")
            val tipo = datosSuscripcion[0]
            val costo = datosSuscripcion[1].toDouble()

            // Determina la duración según el tipo de suscripción.
            val meses = when(tipo){
                "Mensual" -> 1
                "Trimestral" -> 3
                "Anual" -> 12
                else -> 0
            }

            // Convierte la fecha generada a un objeto Fecha.
            val datosFecha = fecha.generar().split("/")
            val inicio = Fecha(
                datosFecha[0].toInt(),
                datosFecha[1].toInt(),
                datosFecha[2].toInt()
            )

            // Calcula el vencimiento como el día anterior
            // a completar los meses de suscripción.
            val fechaFinal = inicio.aLocalDate()
                .plusMonths(meses.toLong())
                .minusDays(1)

            val fin = Fecha(
                fechaFinal.dayOfMonth,
                fechaFinal.monthValue,
                fechaFinal.year
            )

            // Escribe el usuario en una nueva fila del CSV.
            escritor.println("${i+1},\"$nombreCompleto\",$tipo,$costo,$inicio,$fin")
        }
    }
    println("Archivo generado correctamente.")
    println(archivo.absolutePath)
}