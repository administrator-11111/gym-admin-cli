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

/** Pantalla de suscripciones: por ahora, una tabla con suscripciones demostrativas. */
@Composable
fun SuscripcionesScreen() {
    PlantillaPantalla(Seccion.SUSCRIPCIONES) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column {
                FilaTabla {
                    CeldaTexto("Cliente", 2f, esCabecera = true)
                    CeldaTexto("Plan", 1f, esCabecera = true)
                    CeldaTexto("Inicio", 1f, esCabecera = true)
                    CeldaTexto("Término", 1f, esCabecera = true)
                    CeldaTexto("Estado", 1f, esCabecera = true)
                }
                HorizontalDivider()
                LazyColumn {
                    items(DatosDemostrativos.clientes) { cliente ->
                        FilaTabla {
                            CeldaTexto(cliente.nombreCompleto, 2f)
                            CeldaTexto(cliente.obtenerTipoPlan(), 1f)
                            CeldaTexto(cliente.obtenerFechaInicio().toString(), 1f)
                            CeldaTexto(cliente.obtenerFechaFin().toString(), 1f)
                            Box(Modifier.weight(1f)) {
                                val vigente = !cliente.esMoroso()
                                EtiquetaEstado(if (vigente) "Vigente" else "Vencida", positivo = vigente)
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    }
                }
            }
        }
    }
}
