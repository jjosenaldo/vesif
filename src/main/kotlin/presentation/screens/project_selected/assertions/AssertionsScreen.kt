package presentation.screens.project_selected.assertions

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import presentation.model.UiCircuitParams
import presentation.screens.common.CircuitWithSidePane
import presentation.screens.project_selected.select_circuit.CircuitViewModel

@Composable
fun AssertionsScreen(
    circuitViewModel: CircuitViewModel = koinInject()
) {
    CircuitWithSidePane(
        UiCircuitParams(circuitViewModel.selectedCircuitImage)
    ) { AssertionsPane() }
}
