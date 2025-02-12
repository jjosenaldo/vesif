package ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.rememberDialogState

@Composable
fun AppDialogWindow(
    onCloseRequest: () -> Unit,
    title: String,
    state: DialogState = rememberDialogState(),
    content: @Composable (DialogWindowScope.() -> Unit)
) {
    DialogWindow(
        onCloseRequest = onCloseRequest,
        title = title,
        state = state,
        content = {
            Box(Modifier.fillMaxSize().background(color = primaryBackgroundColor)) {
                content()
            }
        }
    )
}