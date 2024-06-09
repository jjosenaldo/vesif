package ui.screens.project_selected.sub_screens.assertions

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.model.UiCircuitParams
import ui.screens.project_selected.SelectedProjectScreenContent
import ui.screens.project_selected.sub_screens.select_circuit.CircuitViewModel

@Composable
fun AssertionsScreen(
    circuitViewModel: CircuitViewModel = koinInject()
) {
    SelectedProjectScreenContent(
        UiCircuitParams(circuitViewModel.selectedCircuitImage)
    ) { AssertionsPane() }
}
