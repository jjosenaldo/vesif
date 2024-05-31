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
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AssertionsScreen(
    navController: NavHostController,
    viewModel: AssertionsViewModel = koinInject()
) {
    if (viewModel.assertions.isEmpty()) return


    val scope = rememberCoroutineScope()
    val assertionDetails = viewModel.assertionDetails

    if (assertionDetails != null) {
        DialogWindow(
            onCloseRequest = { viewModel.showAssertionDetails(null) },
            title = "Details",
        ) {
            Column {
                Text(assertionDetails.details)
                Button(onClick = { viewModel.showAssertionDetails(null) }) {
                    Text("Ok")
                }
            }
        }
    }

    Column {
        viewModel.assertions.map { AssertionView(it) }

        if (viewModel.assertions.all { it is AssertionInitial || it is AssertionRunning })
            Button(
                onClick = {
                    if (viewModel.assertions.none { it is AssertionRunning }) {
                        scope.launch {
                            viewModel.runSelectedAssertions(navController)
                        }
                    }
                }
            ) {
                Text(text = "Check selected properties")
            }
    }
}

@Composable
fun AssertionView(
    assertionState: AssertionState,
    viewModel: AssertionsViewModel = koinInject()
) {
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

