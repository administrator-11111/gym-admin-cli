package com.gymadmin.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.gymadmin.app.ui.navegacion.BarraNavegacion
import com.gymadmin.app.ui.navegacion.Seccion
import com.gymadmin.app.ui.pantallas.ClientesScreen
import com.gymadmin.app.ui.pantallas.DashboardScreen
import com.gymadmin.app.ui.pantallas.SuscripcionesScreen
import com.gymadmin.app.ui.tema.GymAdminTheme

/**
 * Estructura principal de la ventana: encabezado, barra lateral y contenido.
 *
 * El único estado aquí es la sección seleccionada. Cuando cambia, Compose
 * vuelve a dibujar el contenido mostrando la pantalla correspondiente.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymAdminApp() {
    GymAdminTheme {
        var seccionActual by remember { mutableStateOf(Seccion.DASHBOARD) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("GymAdmin", fontWeight = FontWeight.Bold)
                            Text(
                                text = "Sistema de administración de gimnasio",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
            },
        ) { espacioInterno ->
            Row(Modifier.fillMaxSize().padding(espacioInterno)) {
                BarraNavegacion(
                    seccionActual = seccionActual,
                    onSeleccionar = { seccionActual = it },
                )
                VerticalDivider()
                Box(Modifier.fillMaxSize()) {
                    when (seccionActual) {
                        Seccion.DASHBOARD -> DashboardScreen()
                        Seccion.CLIENTES -> ClientesScreen()
                        Seccion.SUSCRIPCIONES -> SuscripcionesScreen()
                    }
                }
            }
        }
    }
}
