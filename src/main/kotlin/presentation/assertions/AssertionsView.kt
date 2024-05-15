package presentation.assertions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogWindow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AssertionsView(
    assertions: List<AssertionState>,
    failedAssertion: AssertionFailed?,
    viewModel: AssertionsViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()
    if (assertions.isEmpty()) return

    if (failedAssertion != null) {
        DialogWindow(
            onCloseRequest = { viewModel.showAssertionDetails(null) },
            title = "Details",
        ) {
            Column {
                Text(failedAssertion.details)
                Button(onClick = { viewModel.showAssertionDetails(null) }) {
                    Text("Ok")
                }
            }
        }
    }

    Column {
        assertions.map { AssertionView(it) }

        if (assertions.all { it is AssertionInitial || it is AssertionRunning })
            Button(
                onClick = {
                    if (assertions.none { it is AssertionRunning }) {
                        scope.launch {
                            viewModel.runSelectedAssertions()
                        }
                    }
                }
            ) {
                Text(text = "Check selected properties")
            }
    }
}

@Composable
fun AssertionView(assertionState: AssertionState, viewModel: AssertionsViewModel = koinInject()) {
    Column {
        when (assertionState) {
            is AssertionFailed -> Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Sharp.Close,
                        "Error icon",
                        tint = Color(0xfff72702)
                    )
                    Text(text = assertionState.name)
                }
                Button(onClick = { viewModel.showAssertionDetails(assertionState) }) {
                    Text("View details")
                }
            }

            is AssertionPassed -> Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Sharp.Check,
                    "Check icon",
                    tint = Color(0xff5fe322)
                )
                Text(text = assertionState.name)
            }

            is AssertionRunning -> Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator()
                Text(text = assertionState.name)
            }

            is AssertionInitial -> Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = assertionState.selected,
                    onCheckedChange = { viewModel.setSelected(it, assertionState) })
                Text(text = assertionState.name)
            }

            is AssertionNotSelected -> {
                Text(text = assertionState.name)
            }
        }
    }
}

