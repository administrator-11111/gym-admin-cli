package com.gymadmin.app.ui.navegacion

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Barra lateral de navegación (Navigation Rail de Material 3).
 *
 * No guarda estado propio: recibe la sección actual y avisa con [onSeleccionar]
 * cuando el usuario elige otra. Quien la usa (GymAdminApp) decide qué hacer.
 */
@Composable
fun BarraNavegacion(
    seccionActual: Seccion,
    onSeleccionar: (Seccion) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        Spacer(Modifier.height(12.dp))
        Seccion.entries.forEach { seccion ->
            NavigationRailItem(
                selected = seccion == seccionActual,
                onClick = { onSeleccionar(seccion) },
                icon = {
                    Text(
                        text = seccion.simbolo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                label = { Text(seccion.titulo) },
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}
