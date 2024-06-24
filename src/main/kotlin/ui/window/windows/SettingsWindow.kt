package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import ui.screens.settings.SettingsScreen
import ui.window.AppWindowManager
import ui.window.WindowId

@Composable
fun SettingsWindow(
    windowManager: AppWindowManager
) = Window(onCloseRequest = { windowManager.close(WindowId.Settings) }, title = "Settings") {
    SettingsScreen()
}