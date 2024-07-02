package ui.common

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.rememberDialogState
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

data class ErrorDialogConfig(
    val title: String = "Error",
    val content: String = "An expected problem occurred.",
    val actionTitle: String = "Ok",
    val action: (() -> Unit) = {},
    val onDialogClosed: (() -> Unit) = {}
)

@Composable
fun ErrorDialog(config: ErrorDialogConfig) {
    fun DialogWindowScope.maxTextHeight() = (this.window.height - 125).dp
    var isShowingDialog by remember { mutableStateOf(true) }

    fun closeDialog() {
        isShowingDialog = false
        config.onDialogClosed()
    }

    val (title, text, actionTitle, action) = config
    val windowState = rememberDialogState(size = DpSize(400.dp, 200.dp))

    if (isShowingDialog)
        AppDialogWindow(
            onCloseRequest = ::closeDialog,
            title = title,
            state = windowState
        ) {
            var textMaxHeight by mutableStateOf(maxTextHeight())
            window.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    super.componentResized(e)
                    textMaxHeight = maxTextHeight()
                }
            })
            Column(Modifier.padding(16.dp)) {
                val stateVertical = rememberScrollState(0)
                Box(modifier = Modifier.heightIn(0.dp, textMaxHeight)) {
                    AppText(
                        text,
                        Modifier.verticalScroll(stateVertical)
                    )
                    VerticalScrollbar(
                        modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                        adapter = rememberScrollbarAdapter(stateVertical)
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Row(modifier = Modifier.align(Alignment.End), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    AppButton(onClick = action.then(::closeDialog)) {
                        AppText(actionTitle)
                    }
                    AppButton(onClick = ::closeDialog, kind = AppButtonKind.Secondary) {
                        AppText("Cancel")
                    }
                }
            }
        }
}

fun (() -> Unit).then(next: () -> Unit): () -> Unit {
    return {
        this()
        next()
    }
}