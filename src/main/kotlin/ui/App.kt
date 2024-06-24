package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import org.koin.compose.koinInject
import ui.window.AppWindowManager
import ui.window.WindowId
import ui.window.windows.MainWindow
import ui.window.windows.SelectProjectWindow
import ui.window.windows.SettingsWindow

@Composable
fun ApplicationScope.App(windowManager: AppWindowManager = koinInject()) {
    MaterialTheme {
        for (windowId in windowManager.windows) {
            when (windowId) {
                WindowId.Main -> MainWindow()
                WindowId.Settings -> SettingsWindow()
                WindowId.SelectProject -> SelectProjectWindow()
            }
        }
    }
}
