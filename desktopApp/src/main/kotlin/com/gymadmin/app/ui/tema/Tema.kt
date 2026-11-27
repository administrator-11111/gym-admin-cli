package com.gymadmin.app.ui.tema

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = AzulPrincipal,
    onPrimary = SuperficieClara,
    primaryContainer = AzulContenedor,
    onPrimaryContainer = AzulOscuro,
    secondary = VerdeSecundario,
    secondaryContainer = VerdeContenedor,
    onSecondaryContainer = VerdeOscuro,
    background = FondoClaro,
    surface = FondoClaro,
    surfaceContainerLow = SuperficieClara,
    surfaceVariant = SuperficieVariante,
)

/**
 * Tema Material 3 de la aplicación. Todas las pantallas se dibujan dentro de él,
 * así los colores y la tipografía se definen en un solo lugar.
 */
@Composable
fun GymAdminTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        content = content,
    )
}
