package presentation.screens.project_selected.select_circuit

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import presentation.model.UiCircuitParams
import presentation.screens.CircuitWithSidePane

@Composable
fun SelectCircuitScreen(
    circuitVm: CircuitViewModel = koinInject()
) {
    CircuitWithSidePane(
        UiCircuitParams(circuitVm.selectedCircuitImage)
    ) { SelectCircuitPane() }
}