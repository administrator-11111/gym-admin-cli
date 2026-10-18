package com.admincli.modelos

class Suscripcion(
    private val tipoPlan:String,
    private val costo:Double,
    private val fechaInicio:Fecha,
    private val fechaFin:Fecha
) {
    
    fun renovar(meses:Int) : Unit{
        
    }

    fun obtenerTipoPlan() : String {
        return this.tipoPlan
    }

    fun obtenerCosto() : Double {
        return this.costo
    }

    fun obtenerFechaInicio() : Fecha {
        return this.fechaInicio
    }

    fun obtenerFechaFin() : Fecha {
        return this.fechaFin
    }
}