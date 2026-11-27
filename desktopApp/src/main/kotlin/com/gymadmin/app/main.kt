package com.gymadmin.app

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gymadmin.app.ui.GymAdminApp
import java.awt.Dimension

/**
 * Punto de entrada de la aplicación de escritorio: solo abre la ventana.
 * Todo el contenido visual está en el paquete ui (ver GymAdminApp).
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GymAdmin",
        state = rememberWindowState(width = 1100.dp, height = 720.dp),
    ) {
        window.minimumSize = Dimension(820, 560)
        GymAdminApp()
    }
}
