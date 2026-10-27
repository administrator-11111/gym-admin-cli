    package com.admincli.modelos

    import java.time.LocalDate

    class Fecha(
        private var dia: Int,
        private var mes: Int,
        private var anio: Int
    ) {
        constructor():this(         //Constructor secundario que da la fecha actual
            LocalDate.now().dayofmonth,
            LocalDate.now().monthValue,
            LocalDate.now()year
        )
        // Convierte tu Fecha a LocalDate de Java para poder compararla con isAfter() CONSULTAR AL PROFE!!!
        fun aLocalDate(): LocalDate {
            return LocalDate.of(anio, mes, dia)
        }

        fun sumarMeses(n: Int) {
            val nuevaFecha = aLocalDate().plusMonths(n.toLong())
            this.dia = nuevaFecha.dayOfMonth
            this.mes = nuevaFecha.monthValue
            this.anio = nuevaFecha.year
        }

        // Muestra la fecha en formato Dia/Mes/Año
        override fun toString(): String {
            return "$dia/$mes/$anio"
        }
    }