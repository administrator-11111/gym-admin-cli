package core
import modelos.*
import Interfaces.Imprimible
class Gestor(
    private val clientes: MutableList<Cliente> = mutableListOf(),
    private val administradores: MutableList<Administrador> = mutableListOf()
){
    fun iniciarSesion(usuario:String,clave:String):Boolean{

    }
    fun inscribirCliente(cliente:Cliente):Unit{

    }
    fun quitarCliente(cliente:Cliente):Unit{

    }
    fun mostrarMorosos():Unit{

    }
    fun calcularGanancias():Double{
        
    }

}