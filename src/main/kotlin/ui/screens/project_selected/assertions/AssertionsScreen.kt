package ui.screens.project_selected.assertions

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.model.UiCircuitParams
import ui.screens.common.CircuitWithSidePane
import ui.screens.project_selected.select_circuit.CircuitViewModel

@Composable
fun AssertionsScreen(
    circuitViewModel: CircuitViewModel = koinInject()
) {
    CircuitWithSidePane(
        UiCircuitParams(circuitViewModel.selectedCircuitImage)
    ) { AssertionsPane() }
}
