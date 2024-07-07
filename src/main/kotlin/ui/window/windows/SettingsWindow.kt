package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ui.common.AppWindow
import ui.screens.settings.SettingsScreen
import ui.window.AppWindowManager
import ui.window.WindowId
import java.awt.Dimension

@Composable
fun SettingsWindow(
    windowManager: AppWindowManager = koinInject()
) = AppWindow(
    onCloseRequest = { windowManager.close(WindowId.Settings) },
    title = "Settings",
    size = DpSize(500.dp, 400.dp),
    minSize = Dimension(500, 400)
) {
    SettingsScreen()
}