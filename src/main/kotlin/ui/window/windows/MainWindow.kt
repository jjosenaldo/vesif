package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.*
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import org.koin.compose.koinInject
import ui.common.AppMenuBar
import ui.navigation.AppNavHost
import ui.screens.project_selected.sub_screens.select_circuit.CircuitViewModel
import ui.window.AppWindowManager

// TODO(ft): window title
@Composable
fun ApplicationScope.MainWindow(
    circuitViewModel: CircuitViewModel = koinInject(), windowManager: AppWindowManager = koinInject()
) =
    Window(onCloseRequest = ::exitApplication, title = "App", onKeyEvent = {
        if (it.isCtrlPressed && it.type == KeyEventType.KeyDown && it.key in listOf(Key.Equals, Key.Minus)) {
            circuitViewModel.zoom(it.key == Key.Equals)
            true
        } else {
            // let other handlers receive this event
            false
        }
    }) {
        AppMenuBar(windowManager)
        AppNavHost()
    }