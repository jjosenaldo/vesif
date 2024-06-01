package presentation.screens.project_selected.select_circuit

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
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
import presentation.select_project.ProjectViewModel


@Composable
fun SelectCircuitPane(
    projectVm: ProjectViewModel = koinInject(),
    circuitVm: CircuitViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxHeight()) {
        Box(modifier = Modifier.weight(1f, false)) {
            val stateVertical = rememberScrollState(0)

            Column(modifier = Modifier.verticalScroll(stateVertical)) {
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
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    stateVertical
                )
            )
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