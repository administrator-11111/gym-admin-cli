package com.admincli.modelos

/**
 * Representa al administrador que accede al sistema de gestión del gimnasio.
 *
 * Hereda de [Usuario] el identificador y el nombre completo, y almacena
 * el nombre de usuario y la clave utilizados para validar el acceso.
 *
 * @param id: Identificador del administrador, enviado a la clase [Usuario].
 * @param nombreCompleto: Nombre completo del administrador, enviado a [Usuario].
 * @property nombreUsuario: Nombre de usuario utilizado para iniciar sesión.
 * @property clave: Clave del administrador.
 */
class Administrador(
    // Atributos heredados
    id: Int,
    nombreCompleto: String,

    // Atributos privados
    private val nombreUsuario: String,
    private val clave: String
) : Usuario(id, nombreCompleto) {

    /**
     * Constructor secundario que crea un administrador con credenciales por defecto.
     * @param id Identificador único del administrador.
     * @param nombreCompleto Nombre completo del administrador.
     */
    constructor(id: Int, nombreCompleto: String) : this(id, nombreCompleto, "admin", "admin")

    fun obtenerNombreUsuario(): String = nombreUsuario
    fun obtenerClave(): String = clave

    /**
     * Comprueba si las credenciales recibidas coinciden con las almacenadas.
     *
     * Ambas comparaciones distinguen entre mayúsculas y minúsculas.
     *
     * @param usuario: Nombre de usuario que se desea validar.
     * @param clave: Clave que se desea validar.
     * @return "true" si ambos valores coinciden; "false" en caso contrario.
     */
    fun validarCredenciales(usuario: String, clave: String): Boolean {
        return (this.nombreUsuario == usuario && this.clave == clave)
    }

    /**
     * Devuelve un resumen con los datos del administrador, incluyendo su nombre de usuario.
     * @return String con ID, nombre y rol de administrador.
     */
    override fun resumenDatos(): String {
        return "${super.resumenDatos()} [Admin: $nombreUsuario]"
    }

    fun toCsvRow(): String {
        return "$id,\"$nombreCompleto\",$nombreUsuario,$clave"
    }
}