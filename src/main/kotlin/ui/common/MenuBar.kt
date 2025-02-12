package ui.common

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.*
import org.koin.compose.koinInject
import ui.window.AppWindowManager
import java.awt.Color as SwingColor
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JMenuBar
import javax.swing.border.LineBorder


@Composable
fun FrameWindowScope.AppMenuBar(appWindowManager: AppWindowManager = koinInject()) {
    AppMenuBar(
        backgroundColor = primaryBackgroundColor,
        borderColor = tertiaryBackgroundColor
    ) {
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


@Composable
private fun FrameWindowScope.AppMenuBar(
    backgroundColor: Color,
    borderColor: Color,
    content: @Composable MenuBarScope.() -> Unit
) {
    val parentComposition = rememberCompositionContext()

    DisposableEffect(Unit) {
        val menu = AppJMenuBar(backgroundColor.toSwingColor()).apply {
            border = LineBorder(borderColor.toSwingColor())
        }
        val composition = menu.setContent(parentComposition, content)
        window.jMenuBar = menu

        onDispose {
            window.jMenuBar = null
            composition.dispose()
        }
    }
}

class AppJMenuBar(private val backgroundColor: SwingColor) : JMenuBar() {
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.color = backgroundColor
        g2d.fillRect(0, 0, width - 1, height - 1)
    }
}

fun Color.toSwingColor(): SwingColor {
    return SwingColor(this.toArgb())
}