package core
import modelos.Administrador
import modelos.Cliente
import modelos.Fecha
import modelos.Suscripcion
class Gestor(
  private val clientes: MutableList<Cliente> = mutableListOf(

    // --- CLIENTES CON PLAN MENSUAL ---
    Cliente(1, "Juan Pérez", Suscripcion("Mensual", 20000.0,  Fecha(1, 10, 2026),  Fecha(30, 10, 2026))),
    Cliente(2, "María González", Suscripcion("Mensual", 20000.0,  Fecha(1, 9, 2026),  Fecha(30, 9, 2026))), // Moroso
    Cliente(3, "Pedro Soto", Suscripcion("Mensual", 20000.0,  Fecha(15, 10, 2026),  Fecha(15, 11, 2026))),
    Cliente(4, "Ana Rojas", Suscripcion("Mensual", 22000.0, Fecha(1, 8, 2026),  Fecha(31, 8, 2026))), // Moroso
    Cliente(5, "Luis Muñoz", Suscripcion("Mensual", 20000.0, Fecha(5, 10, 2026), Fecha(5, 11, 2026))),

    // --- CLIENTES CON PLAN TRIMESTRAL ---
    Cliente(6, "Carla Díaz", Suscripcion("Trimestral", 50000.0, Fecha(1, 10, 2026),  Fecha(31, 12, 2026))),
    Cliente(7, "Diego Contreras", Suscripcion("Trimestral", 50000.0,  Fecha(1, 7, 2026),  Fecha(30, 9, 2026))), // Moroso
    Cliente(8, "Sofía Silva", Suscripcion("Trimestral", 55000.0,  Fecha(10, 9, 2026),  Fecha(10, 12, 2026))),
    Cliente(9, "Andrés Sepúlveda", Suscripcion("Trimestral", 50000.0,  Fecha(1, 10, 2026),  Fecha(31, 12, 2026))),
    Cliente(10, "Valentina Morales", Suscripcion("Trimestral", 50000.0,  Fecha(20, 10, 2026),Fecha(20, 1, 2027))),
    Cliente(11, "Matías Castro", Suscripcion("Trimestral", 50000.0,  Fecha(1, 6, 2026),  Fecha(31, 8, 2026))), // Moroso
    Cliente(12, "Camila Herrera", Suscripcion("Trimestral", 52000.0,  Fecha(15, 10, 2026),  Fecha(15, 1, 2027))),

    // --- CLIENTES CON PLAN ANUAL ---
    Cliente(13, "Sebastián Fuentes", Suscripcion("Anual", 180000.0,  Fecha(1, 1, 2026), Fecha(31, 12, 2026))),
    Cliente(14, "Javiera Araya", Suscripcion("Anual", 180000.0,  Fecha(1, 10, 2026),  Fecha(30, 9, 2027))),
    Cliente(15, "Felipe Gutiérrez", Suscripcion("Anual", 180000.0,  Fecha(15, 3, 2026),  Fecha(15, 3, 2027))),
    Cliente(16, "Daniela Torres", Suscripcion("Anual", 190000.0,  Fecha(1, 5, 2026),  Fecha(30, 4, 2027))),
    Cliente(17, "Cristóbal Espinoza", Suscripcion("Anual", 180000.0, Fecha(1, 1, 2025),  Fecha(31, 12, 2025))), // Moroso
    Cliente(18, "Constanza Valdés", Suscripcion("Anual", 180000.0,  Fecha(1, 11, 2026),  Fecha(31, 10, 2027))),
    Cliente(19, "Ignacio Riquelme", Suscripcion("Anual", 180000.0,  Fecha(1, 8, 2026), Fecha(31, 7, 2027))),
    Cliente(20, "Fernanda Pizarro", Suscripcion("Anual", 200000.0,  Fecha(1, 10, 2026),  Fecha(30, 9, 2027)))
),

    private val administradores: List<Administrador> = listOf(
        Administrador(1, "Alonso Lopez Riquelme", "Alonso", "1234"),
        Administrador(2, "Ivan Maldonado Rodriguez", "ivan", "1234"),
        Administrador(3, "Lucas Sierra Aguirre", "Lucas", "1234"),
        Administrador(4, "Yeral Mendez Torres", "Yeral", "1234")
    )

){
  
fun iniciarSesion(nombreUsuario: String, clave: String): Boolean {          //Recordar que dentro de imprimible.kt donde este el menu se debe crear las variables contra ingresada y nombre de usuario para validar
    for (admin in administradores) {
        if (admin.validarCredenciales(nombreUsuario, clave)) {
            println("Has iniciado sesion exitosamente")
            return true
        }
    }

    println("Credenciales Invalidas")
    return false
}

    fun inscribirCliente(cliente:Cliente):Unit{
        clientes.add(cliente)
    }
    fun quitarCliente(cliente:Cliente):Unit{
        cliente.remove(cliente)
    }
    fun mostrarMorosos():Unit{
        for(cliente in clientes){
            if(clientes.esMoroso)
            
        }
    }
    fun calcularGanancias():Double{
        
    }   

}