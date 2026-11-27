package com.gymadmin.app.modelos

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * Comprueba que la versión común de [Fecha] (sin java.time) se comporte igual que la original,
 * que usaba LocalDate.of(...) y LocalDate.plusMonths(...).
 */
class FechaTest {

    private fun sumar(fecha: Fecha, meses: Int): String = fecha.also { it.sumarMeses(meses) }.toString()

    @Test
    fun sumarMesesSimple() {
        assertEquals("17/9/2027", sumar(Fecha(17, 8, 2027), 1))
        assertEquals("14/3/2028", sumar(Fecha(14, 12, 2027), 3))
        assertEquals("7/5/2028", sumar(Fecha(7, 5, 2027), 12))
    }

    @Test
    fun sumarMesesAjustaAlUltimoDiaDelMes() {
        assertEquals("28/2/2027", sumar(Fecha(31, 1, 2027), 1))
        assertEquals("29/2/2028", sumar(Fecha(31, 1, 2028), 1)) // año bisiesto
        assertEquals("30/4/2026", sumar(Fecha(31, 3, 2026), 1))
    }

    @Test
    fun rechazaFechasInexistentes() {
        assertFailsWith<IllegalArgumentException> { Fecha(29, 2, 2027) }
        assertFailsWith<IllegalArgumentException> { Fecha(31, 4, 2026) }
        assertFailsWith<IllegalArgumentException> { Fecha(1, 13, 2026) }
        Fecha(29, 2, 2028) // bisiesto: válida
    }

    @Test
    fun comparaFechasCronologicamente() {
        assertTrue(Fecha(1, 1, 2027) > Fecha(31, 12, 2026))
        assertTrue(Fecha(5, 3, 2026) < Fecha(6, 3, 2026))
        assertEquals(0, Fecha(5, 3, 2026).compareTo(Fecha(5, 3, 2026)))
    }

    @Test
    fun desdeStringYToStringSonCompatiblesConElCsv() {
        assertEquals("16/9/2027", Fecha.desdeString(" 16/9/2027 ").toString())
    }
}
