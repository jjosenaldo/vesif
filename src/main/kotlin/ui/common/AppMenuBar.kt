package ui.common

import androidx.compose.runtime.*
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuScope
import org.koin.compose.koinInject
import ui.window.AppWindowManager

@Composable
fun FrameWindowScope.AppMenuBar(appWindowManager: AppWindowManager = koinInject()) {
    MenuBar {
        Menu("File", mnemonic = 'F') {
            Item(
                "Settings",
                onClick = appWindowManager::openSettings,
                shortcut = KeyShortcut(Key.S, ctrl = true)
            )
            OpenProjectItem()
        }
    }
}

@Composable
private fun MenuScope.OpenProjectItem() {
    var showPicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Item(
        "Open project",
        onClick = { showPicker = true },
        shortcut = KeyShortcut(Key.O, ctrl = true)
    )
    ClearsyProjectPicker(
        show = showPicker,
        onShowChanged = { showPicker = it },
        scope = scope
    )
}