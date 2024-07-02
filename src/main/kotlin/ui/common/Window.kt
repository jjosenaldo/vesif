package ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension


@Composable
fun AppWindow(
    title: String,
    onCloseRequest: () -> Unit,
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    alwaysOnTop: Boolean = false,
    size: DpSize = DpSize(800.dp, 600.dp),
    minSize: Dimension? = null,
    content: @Composable FrameWindowScope.() -> Unit
) {
    Window(
        onKeyEvent = onKeyEvent,
        onCloseRequest = onCloseRequest,
        title = title,
        alwaysOnTop = alwaysOnTop,
        state = rememberWindowState(size = size)
    ) {
        minSize?.let {
            window.minimumSize = it
        }

        // TODO(ux)
        Box(Modifier.fillMaxSize().background(color = primaryBackgroundColor)) {
            content()
        }
    }
}