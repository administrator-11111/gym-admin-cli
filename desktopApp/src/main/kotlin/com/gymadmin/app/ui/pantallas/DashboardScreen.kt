package com.gymadmin.app.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gymadmin.app.ui.componentes.PlantillaPantalla
import com.gymadmin.app.ui.componentes.TarjetaIndicador
import com.gymadmin.app.ui.demo.DatosDemostrativos
import com.gymadmin.app.ui.navegacion.Seccion

/** Pantalla de inicio: tarjetas de resumen con valores demostrativos. */
@Composable
fun DashboardScreen() {
    PlantillaPantalla(Seccion.DASHBOARD) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DatosDemostrativos.indicadores.forEach { indicador ->
                TarjetaIndicador(
                    titulo = indicador.titulo,
                    valor = indicador.valor,
                    detalle = indicador.detalle,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Bienvenido a GymAdmin", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Usa la barra lateral para moverte entre las secciones. " +
                        "Las métricas reales del gimnasio se incorporarán en los proximos avances.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
