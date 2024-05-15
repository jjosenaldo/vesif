package presentation.assertions

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window

@Composable
private fun AssertionFailedDetailsView(
    details: String
) {
    var isOpen by remember { mutableStateOf(true) }

    if (isOpen)
        Window(onCloseRequest = { }, title = "Assertion details") {
            Text(details)
        }
}