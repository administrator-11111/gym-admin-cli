package com.gymadmin.app.ui.pantallas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gymadmin.app.ui.componentes.EtiquetaEstado
import com.gymadmin.app.ui.componentes.PlantillaPantalla
import com.gymadmin.app.ui.demo.DatosDemostrativos
import com.gymadmin.app.ui.navegacion.Seccion

/** Pantalla de clientes: por ahora, una tabla con clientes demostrativos. */
@Composable
fun ClientesScreen() {
    PlantillaPantalla(Seccion.CLIENTES) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column {
                FilaTabla {
                    CeldaTexto("ID", 0.5f, esCabecera = true)
                    CeldaTexto("Nombre", 2f, esCabecera = true)
                    CeldaTexto("Plan", 1f, esCabecera = true)
                    CeldaTexto("Estado", 1f, esCabecera = true)
                }
                HorizontalDivider()
                LazyColumn {
                    items(DatosDemostrativos.clientes) { cliente ->
                        FilaTabla {
                            CeldaTexto(cliente.id.toString(), 0.5f)
                            CeldaTexto(cliente.nombreCompleto, 2f)
                            CeldaTexto(cliente.obtenerTipoPlan(), 1f)
                            Box(Modifier.weight(1f)) {
                                // La regla "¿está moroso?" la decide el modelo (shared), no la pantalla.
                                val alDia = !cliente.esMoroso()
                                EtiquetaEstado(if (alDia) "Al día" else "Moroso", positivo = alDia)
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    }
                }
            }
        }
    }
}
