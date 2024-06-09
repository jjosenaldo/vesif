package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import ui.window.AppWindowManager
import ui.window.WindowId

@Composable
fun SettingsWindow(
    state: AppWindowManager
) = Window(onCloseRequest = { state.close(WindowId.Settings) }, title = "Settings") {

}