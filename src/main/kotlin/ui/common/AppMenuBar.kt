package ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import org.koin.compose.koinInject
import ui.window.AppWindowManager
import ui.window.WindowId

@Composable
fun FrameWindowScope.AppMenuBar(appWindowManager: AppWindowManager = koinInject()) {
    MenuBar {
        Menu("File", mnemonic = 'F') {
            Item(
                "Settings",
                onClick = { appWindowManager.open(WindowId.Settings) },
                shortcut = KeyShortcut(Key.S, ctrl = true)
            )
        }
    }
}