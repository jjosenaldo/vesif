package ui.screens.project_selected.select_circuit

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.model.UiCircuitParams
import ui.screens.common.CircuitWithSidePane

@Composable
fun SelectCircuitScreen(
    circuitVm: CircuitViewModel = koinInject()
) {
    CircuitWithSidePane(
        UiCircuitParams(circuitVm.selectedCircuitImage)
    ) { SelectCircuitPane() }
}