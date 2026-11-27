package com.gymadmin.app.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gymadmin.app.ui.navegacion.Seccion

/**
 * Estructura común de todas las pantallas: márgenes, encabezado, aviso de datos
 * demostrativos y el contenido propio de la sección.
 */
@Composable
fun PlantillaPantalla(
    seccion: Seccion,
    mostrarAvisoDemo: Boolean = true,
    contenido: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        EncabezadoPantalla(titulo = seccion.titulo, descripcion = seccion.descripcion)
        if (mostrarAvisoDemo) {
            AvisoDatosDemostrativos()
        }
        contenido()
    }
}
