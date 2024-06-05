package ui.screens.project_selected.select_circuit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.screens.common.BidirectionalScrollBar
import ui.screens.common.Pane
import ui.screens.select_project.ProjectViewModel


@Composable
fun SelectCircuitPane(
    projectVm: ProjectViewModel = koinInject(),
    circuitVm: CircuitViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()

    Pane {
        BidirectionalScrollBar(modifier = Modifier.fillMaxHeight().weight(1f)) {
            projectVm.circuitsPaths.forEach { path ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = circuitVm.selectedCircuitPath == path,
                        onClick = {
                            if (circuitVm.selectedCircuitPath != path && circuitVm.loadCircuitState !is LoadCircuitLoading) {
                                scope.launch { circuitVm.selectCircuit(path) }
                            }
                        }
                    )
                    Text(
                        text = circuitVm.getCircuitName(path)
                    )
                }
            }
        }

        SelectCircuitButton()
    }
}


@Composable
private fun SelectCircuitButton(
    circuitVm: CircuitViewModel = koinInject()
) {
    Button(
        enabled = circuitVm.selectedCircuitPath.isNotEmpty() && circuitVm.loadCircuitState !is LoadCircuitLoading,
        onClick = circuitVm::confirmCircuitSelection
    ) {
        if (circuitVm.loadCircuitState is LoadCircuitLoading)
            CircularProgressIndicator()
        else
            Text(
                text = "Select circuit"
            )
    }
}