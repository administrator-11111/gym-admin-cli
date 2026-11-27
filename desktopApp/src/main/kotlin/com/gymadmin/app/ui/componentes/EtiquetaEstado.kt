package com.gymadmin.app.ui.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Etiqueta de color para mostrar un estado.
 * Solo decide colores: si algo está "al día" o no lo determina la lógica del negocio.
 */
@Composable
fun EtiquetaEstado(
    texto: String,
    positivo: Boolean,
    modifier: Modifier = Modifier,
) {
    val colores = MaterialTheme.colorScheme
    Surface(
        modifier = modifier,
        color = if (positivo) colores.secondaryContainer else colores.errorContainer,
        contentColor = if (positivo) colores.onSecondaryContainer else colores.onErrorContainer,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        )
    }
}
