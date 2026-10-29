package com.admincli.core

import com.admincli.modelos.Cliente
import com.admincli.modelos.Administrador
import com.admincli.modelos.Suscripcion
import com.admincli.modelos.Fecha
import com.admincli.interfaces.Imprimible
import java.io.File

/**
 * Objeto singleton encargado de centralizar la lógica de negocio, la gestión en memoria
 * y la persistencia en archivos CSV tanto de [Cliente] como de [Administrador].
 *
 * Provee operaciones CRUD, autenticación de administradores, generación automática de
 * identificadores y cálculo de métricas financieras y operativas del sistema.
 */
object Gestor {

    /** Lista interna mutable que almacena los clientes registrados en memoria. */
    private val clientes: MutableList<Cliente> = mutableListOf()

    /** Lista interna mutable que almacena los administradores registrados en memoria. */
    private val administradores: MutableList<Administrador> = mutableListOf()

    /** Contador incremental para asignar el próximo ID único disponible a un nuevo cliente. */
    private var siguienteClienteId: Int = 1

    /** Contador incremental para asignar el próximo ID único disponible a un nuevo administrador. */
    private var siguienteAdminId: Int = 1

    /** Ruta relativa por defecto del archivo CSV donde se persisten los datos de los clientes. */
    private var rutaArchivoClientes: String = "data/usuarios.csv"

    /** Ruta relativa por defecto del archivo CSV donde se persisten los datos de los administradores. */
    private var rutaArchivoAdmins: String = "data/administradores.csv"

    /**
     * Obtiene el próximo identificador único disponible para registrar un nuevo cliente.
     *
     * @return El ID numérico ([Int]) sugerido para el siguiente [Cliente].
     */
    fun generarSiguienteClienteId(): Int = siguienteClienteId

    /**
     * Obtiene el próximo identificador único disponible para registrar un nuevo administrador.
     *
     * @return El ID numérico ([Int]) sugerido para el siguiente [Administrador].
     */
    fun generarSiguienteAdminId(): Int = siguienteAdminId

    // --- CARGA Y PERSISTENCIA DE ADMINISTRADORES ---

    /**
     * Carga los registros de administradores desde un archivo CSV hacia la lista en memoria.
     *
     * Si el archivo especificado no existe, crea automáticamente un administrador por defecto
     * (`usuario: "admin"`, `clave: "admin"`), ajusta el contador de IDs y genera el archivo físico.
     * Ignora líneas vacías, malformadas o con IDs duplicados, actualizando [siguienteAdminId]
     * al finalizar en función del ID más alto encontrado.
     *
     * @param rutaArchivo Ruta del archivo CSV de origen. Por defecto utiliza [rutaArchivoAdmins].
     */
    fun cargarAdministradores(rutaArchivo: String = rutaArchivoAdmins) {
        this.rutaArchivoAdmins = rutaArchivo
        val archivo = File(rutaArchivo)
        if (!archivo.exists()) {
            println("Advertencia: Archivo de administradores no encontrado en $rutaArchivo. Creando administrador por defecto.")
            val adminDefecto = Administrador(1, "Administrador Sistema", "admin", "admin")
            administradores.add(adminDefecto)
            siguienteAdminId = 2
            guardarAdministradores(rutaArchivo)
            return
        }

        val lineas = archivo.readLines()
        for (i in 1 until lineas.size) {
            val linea = lineas[i].trim()
            if (linea.isEmpty()) continue
            try {
                val campos = parsearCamposCsv(linea)
                if (campos.size < 4) continue
                val id = campos[0].toInt()
                val nombreCompleto = campos[1]
                val usuario = campos[2]
                val clave = campos[3]

                if (administradores.none { it.id == id }) {
                    administradores.add(Administrador(id, nombreCompleto, usuario, clave))
                }
            } catch (e: Exception) {
                println("Advertencia: Error al procesar administrador en línea ${i + 1}: ${e.message}")
            }
        }

        val maxId = administradores.maxOfOrNull { it.id } ?: 0
        siguienteAdminId = maxId + 1
        println("Se cargaron ${administradores.size} administradores desde $rutaArchivo")
    }

    /**
     * Persiste la lista actual de administradores en memoria dentro de un archivo con formato CSV.
     *
     * Si los directorios padres de la ruta no existen, los crea automáticamente antes de escribir
     * la cabecera y las filas correspondientes a cada [Administrador].
     *
     * @param rutaArchivo Ruta del archivo CSV de destino. Por defecto utiliza [rutaArchivoAdmins].
     */
    fun guardarAdministradores(rutaArchivo: String = rutaArchivoAdmins) {
        try {
            val archivo = File(rutaArchivo)
            archivo.parentFile?.mkdirs()
            archivo.printWriter().use { writer ->
                writer.println("Id,Nombre Completo,Usuario,Clave")
                for (admin in administradores) {
                    writer.println(admin.toCsvRow())
                }
            }
        } catch (e: Exception) {
            println("Error al guardar administradores en $rutaArchivo: ${e.message}")
        }
    }

    // --- CARGA Y PERSISTENCIA DE CLIENTES ---

    /**
     * Carga los registros de clientes y sus respectivas suscripciones desde un archivo CSV.
     *
     * Omite la cabecera, líneas vacías, registros con menos de 6 columnas o clientes con IDs
     * ya existentes en memoria. Al finalizar la lectura, recalcula [siguienteClienteId] sumando 1
     * al identificador más alto registrado.
     *
     * @param rutaArchivo Ruta del archivo CSV de origen. Por defecto utiliza [rutaArchivoClientes].
     */
    fun cargarDatos(rutaArchivo: String = rutaArchivoClientes) {
        this.rutaArchivoClientes = rutaArchivo
        val archivo = File(rutaArchivo)
        if (!archivo.exists()) {
            println("Error: Archivo de clientes no encontrado en $rutaArchivo")
            return
        }

        val lineas: List<String> = archivo.readLines()
        for (i in 1 until lineas.size) {
            val linea: String = lineas[i].trim()
            if (linea.isEmpty()) continue
            try {
                val campos = parsearCamposCsv(linea)
                if (campos.size < 6) continue

                val id: Int = campos[0].toInt()
                val nombreCompleto: String = campos[1]
                val tipoPlan: String = campos[2]
                val costo: Double = campos[3].toDouble()
                val fechaInicio: Fecha = Fecha.desdeString(campos[4])
                val fechaFin: Fecha = Fecha.desdeString(campos[5])

                val suscripcion = Suscripcion(tipoPlan, costo, fechaInicio, fechaFin)
                val cliente = Cliente(id, nombreCompleto, suscripcion)

                if (clientes.none { it.id == id }) {
                    clientes.add(cliente)
                }
            } catch (e: Exception) {
                println("Advertencia: Error al procesar cliente en línea ${i + 1}: ${e.message}")
            }
        }

        val maxId = clientes.maxOfOrNull { it.id } ?: 0
        siguienteClienteId = maxId + 1
        println("Se cargaron ${clientes.size} clientes desde $rutaArchivo (Siguiente ID automático: $siguienteClienteId)")
    }

    /**
     * Guarda la totalidad de los clientes almacenados en memoria dentro de un archivo CSV.
     *
     * Crea la estructura de carpetas necesaria si no existe, escribe la fila de encabezados
     * y serializa cada objeto [Cliente] mediante su método `toCsvRow()`.
     *
     * @param rutaArchivo Ruta del archivo CSV de destino. Por defecto utiliza [rutaArchivoClientes].
     */
    fun guardarDatos(rutaArchivo: String = rutaArchivoClientes) {
        try {
            val archivo = File(rutaArchivo)
            archivo.parentFile?.mkdirs()
            archivo.printWriter().use { writer ->
                writer.println("Id,Nombre Completo,Suscripcion,Costo,FechaInicio,FechaFin")
                for (cliente in clientes) {
                    writer.println(cliente.toCsvRow())
                }
            }
        } catch (e: Exception) {
            println("Error al guardar clientes en $rutaArchivo: ${e.message}")
        }
    }

    // --- PARSEO CSV AUXILIAR ---

    /**
     * Descompone una línea de texto en formato CSV en una lista de campos individuales.
     *
     * Soporta valores encerrados entre comillas dobles (`"`), evitando que las comas internas
     * dentro de un texto entrecomillado dividan erróneamente el campo. Elimina los espacios
     * en blanco iniciales y finales de cada valor resultante.
     *
     * @param linea Cadena de texto correspondiente a una fila del archivo CSV.
     * @return Lista de cadenas ([List]<[String]>) con los valores limpios de cada columna.
     */
    private fun parsearCamposCsv(linea: String): List<String> {
        val campos = mutableListOf<String>()
        var actual = ""
        var dentroComillas = false
        for (c in linea) {
            when {
                c == '"' -> dentroComillas = !dentroComillas
                c == ',' && !dentroComillas -> {
                    campos.add(actual.trim())
                    actual = ""
                }
                else -> actual += c
            }
        }
        campos.add(actual.trim())
        return campos
    }

    // --- GESTIÓN DE CLIENTES ---

    /**
     * Registra un nuevo cliente en el sistema y persiste los cambios en el archivo CSV.
     *
     * Verifica previamente que no exista otro cliente con el mismo identificador. Si el ID
     * del nuevo cliente es mayor o igual a [siguienteClienteId], actualiza el contador.
     *
     * @param cliente Instancia de [Cliente] que se desea inscribir.
     * @return `true` si el cliente fue inscrito y guardado con éxito; `false` si el ID ya estaba en uso.
     */
    fun inscribirCliente(cliente: Cliente): Boolean {
        if (clientes.any { it.id == cliente.id }) {
            println("Error: Ya existe un cliente con ID ${cliente.id}")
            return false
        }
        clientes.add(cliente)
        if (cliente.id >= siguienteClienteId) {
            siguienteClienteId = cliente.id + 1
        }
        guardarDatos()
        return true
    }

    /**
     * Elimina un cliente del sistema según su identificador único y actualiza el archivo CSV.
     *
     * @param id Identificador numérico del cliente a eliminar.
     * @return `true` si el cliente fue encontrado y eliminado; `false` si no existe un cliente con ese ID.
     */
    fun quitarCliente(id: Int): Boolean {
        val cliente = clientes.find { it.id == id }
        return if (cliente != null) {
            clientes.remove(cliente)
            guardarDatos()
            true
        } else {
            false
        }
    }

    /**
     * Busca clientes que coincidan con un criterio de búsqueda general.
     *
     * Si el criterio puede convertirse a un número entero, verifica la coincidencia exacta con el `id`
     * del cliente; adicionalmente, evalúa si el texto del criterio está contenido (sin distinguir
     * mayúsculas de minúsculas) dentro del resumen de datos del cliente.
     *
     * @param criterio Texto o número en formato [String] por el cual filtrar los clientes.
     * @return Lista de objetos [Cliente] que cumplen con el criterio de búsqueda.
     */
    fun buscarCliente(criterio: String): List<Cliente> {
        val idNumerico = criterio.trim().toIntOrNull()
        return clientes.filter { cliente ->
            (idNumerico != null && cliente.id == idNumerico) ||
                    cliente.resumenDatos().contains(criterio.trim(), ignoreCase = true)
        }
    }

    /**
     * Busca un cliente específico mediante su identificador único.
     *
     * @param id Identificador numérico del cliente buscado.
     * @return La instancia de [Cliente] correspondiente, o `null` si no se encuentra registrado.
     */
    fun buscarClientePorId(id: Int): Cliente? = clientes.find { it.id == id }

    /**
     * Obtiene todos los clientes cuya suscripción se encuentra vencida o en estado de morosidad.
     *
     * @return Lista de [Cliente] que cumplen la condición `esMoroso() == true`.
     */
    fun obtenerMorosos(): List<Cliente> = clientes.filter { it.esMoroso() }

    /**
     * Recupera una colección unificada con todos los usuarios del sistema (clientes y administradores)
     * bajo su interfaz común [Imprimible].
     *
     * @return Lista polimórfica de elementos [Imprimible].
     */
    fun obtenerTodosLosUsuarios(): List<Imprimible> = clientes + administradores

    /**
     * Obtiene una copia inmutable de la lista completa de clientes registrados.
     *
     * @return Lista de solo lectura con todas las instancias de [Cliente].
     */
    fun obtenerListaClientes(): List<Cliente> = clientes.toList()

    /**
     * Obtiene una copia inmutable de la lista completa de administradores registrados.
     *
     * @return Lista de solo lectura con todas las instancias de [Administrador].
     */
    fun obtenerAdministradores(): List<Administrador> = administradores.toList()

    /**
     * Registra un nuevo administrador en el sistema y guarda los cambios en el archivo CSV,
     * siempre y cuando su identificador no esté duplicado.
     *
     * @param admin Instancia de [Administrador] a registrar.
     */
    fun registrarAdministrador(admin: Administrador) {
        if (administradores.none { it.id == admin.id }) {
            administradores.add(admin)
            if (admin.id >= siguienteAdminId) {
                siguienteAdminId = admin.id + 1
            }
            guardarAdministradores()
        }
    }

    /**
     * Autentica a un administrador verificando sus credenciales de acceso.
     *
     * @param usuario Nombre de usuario ingresado.
     * @param clave Contraseña ingresada en texto plano.
     * @return La instancia del [Administrador] autenticado si las credenciales coinciden, o `null` en caso contrario.
     */
    fun iniciarSesion(usuario: String, clave: String): Administrador? {
        return administradores.find { it.validarCredenciales(usuario, clave) }
    }

    /**
     * Calcula el ingreso total bruto sumando el costo de suscripción de todos los clientes registrados.
     *
     * @return Monto total acumulado en formato [Double].
     */
    fun calcularGanancias(): Double = clientes.sumOf { it.obtenerCostoSuscripcion() }

    /**
     * Genera un resumen estadístico y financiero del estado actual de la cartera de clientes.
     *
     * Las claves incluidas en el mapa resultante son:
     * - `"total"`: Cantidad total de clientes ([Int]).
     * - `"activos"`: Cantidad de clientes con suscripción al día ([Int]).
     * - `"morosos"`: Cantidad de clientes morosos ([Int]).
     * - `"tasaMorosidad"`: Porcentaje de clientes morosos respecto al total ([Double]).
     * - `"ganancias"`: Suma total de los costos de suscripción ([Double]).
     * - `"ticketPromedio"`: Ingreso promedio por cliente registrado ([Double]).
     *
     * @return Mapa inmutable ([Map]<[String], [Any]>) con los indicadores clave del sistema.
     */
    fun obtenerMetricas(): Map<String, Any> {
        val total = clientes.size
        val morosos = obtenerMorosos().size
        val activos = total - morosos
        val tasaMorosidad = if (total > 0) (morosos.toDouble() / total) * 100.0 else 0.0
        val ganancias = calcularGanancias()
        val ticketPromedio = if (total > 0) ganancias / total else 0.0

        return mapOf(
            "total" to total,
            "activos" to activos,
            "morosos" to morosos,
            "tasaMorosidad" to tasaMorosidad,
            "ganancias" to ganancias,
            "ticketPromedio" to ticketPromedio
        )
    }
}