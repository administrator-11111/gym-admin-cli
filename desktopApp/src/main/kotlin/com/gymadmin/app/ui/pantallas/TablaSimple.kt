package com.gymadmin.app.ui.pantallas

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Piezas pequeñas para dibujar tablas simples en las pantallas de Clientes y Suscripciones.

/** Fila de una tabla con celdas alineadas horizontalmente. */
@Composable
internal fun FilaTabla(
    modifier: Modifier = Modifier,
    celdas: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = celdas,
    )
}

/** Celda de texto que ocupa una proporción [peso] del ancho de la fila. */
@Composable
internal fun RowScope.CeldaTexto(
    texto: String,
    peso: Float,
    esCabecera: Boolean = false,
) {
    Text(
        text = texto,
        modifier = Modifier.weight(peso),
        style = if (esCabecera) MaterialTheme.typography.labelLarge else MaterialTheme.typography.bodyMedium,
        fontWeight = if (esCabecera) FontWeight.SemiBold else FontWeight.Normal,
        color = if (esCabecera) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
    )
}
