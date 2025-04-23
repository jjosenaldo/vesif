package ui.screens.main.sub_screens.select_circuit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import input.model.XmlComponentAttributeException
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.common.*
import ui.navigation.AppNavigator
import ui.screens.select_project.ProjectViewModel


@Composable
fun SelectCircuitPane(
    projectVm: ProjectViewModel = koinInject(),
    circuitVm: CircuitViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()
    ErrorView()

    Pane {
        BidirectionalScrollBar(modifier = Modifier.fillMaxHeight().weight(1f)) {
            projectVm.circuitsPaths.forEach { path ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = circuitVm.selectedCircuitPath == path,
                        onClick = {
                            if (circuitVm.selectedCircuitPath != path && circuitVm.loadCircuitState !is UiLoading) {
                                scope.launch { circuitVm.selectCircuit(path) }
                            }
                        }
                    )
                    AppText(
                        text = circuitVm.getCircuitName(path)
                    )
                }
            }
        }
        Divider(color = tertiaryBackgroundColor)
        SelectCircuitButton()
    }
}


@Composable
private fun SelectCircuitButton(
    circuitVm: CircuitViewModel = koinInject(),
    navigator: AppNavigator = koinInject()
) {
    AppButton(
        onClick = { circuitVm.confirmCircuitSelection(navigator) },
        enabled = circuitVm.selectedCircuitPath.isNotEmpty() && circuitVm.loadCircuitState !is UiLoading,
        modifier = Modifier.padding(16.dp)
    ) {
        if (circuitVm.loadCircuitState is UiLoading)
            CircularProgressIndicator()
        else
            AppText(
                text = "Select circuit"
            )
    }
}

@Composable
private fun ErrorView(
    viewModel: CircuitViewModel = koinInject()
) {
    val state = viewModel.loadCircuitState
    if (state !is UiError) return

    ErrorDialog(
        when(state.error ?: Exception()) {
            is XmlComponentAttributeException -> ErrorDialogConfig(
                "Invalid component attribute value",
                (state.error as XmlComponentAttributeException).message ?: "",
                onDialogClosed = viewModel::reset
            )
            else -> ErrorDialogConfig(
                "Invalid Clearsy circuit",
                "There is a problem with your circuit. Check whether there are any errors on the diagram editor.",
                onDialogClosed = viewModel::reset
            )
        }
    )
}