package ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.DialogWindow
import org.koin.compose.koinInject
import ui.window.AppWindowManager
import verifier.util.FdrNotFoundException
import verifier.util.InvalidFdrException

private data class DialogConfig(
    val title: String,
    val content: String,
    val actionTitle: String?,
    val action: (() -> Unit)?
)

@Composable
fun ErrorDialog(error: Throwable, windowManager: AppWindowManager = koinInject()) {
    var isShowingDialog by remember { mutableStateOf(true) }

    fun closeDialog() {
        isShowingDialog = false
    }

    val (title, text, actionTitle, action) = remember {
        when (error) {
            is InvalidFdrException -> DialogConfig(
                "Invalid FDR",
                "Your FDR is not valid. Please provide a path to a valid FDR version in Settings or add said folder to your PATH.",
                "Go to settings",
                windowManager::openSettings.then(::closeDialog)
            )

            is FdrNotFoundException -> DialogConfig(
                "FDR not found",
                "FDR not found. Please provide a path to a valid FDR version in Settings or add said folder to your PATH.",
                "Go to settings",
                windowManager::openSettings.then(::closeDialog)
            )

            else -> DialogConfig(
                "Error",
                "An error has occurred: ${error.message}",
                null,
                null
            )
        }
    }

    if (isShowingDialog)
        DialogWindow(
            onCloseRequest = ::closeDialog,
            title = title,
        ) {
            Column {
                Text(text)
                Row {
                    if (actionTitle != null && action != null) {
                        Button(onClick = action) {
                            Text(actionTitle)
                        }
                        Button(onClick = ::closeDialog) {
                            Text("Cancel")
                        }
                    } else {
                        Button(onClick = ::closeDialog) {
                            Text("Ok")
                        }
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