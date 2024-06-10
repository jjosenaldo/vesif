package ui.screens.project_selected.sub_screens.failed_assertion

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.project_selected.SelectedProjectScreenContent
import ui.screens.project_selected.sub_screens.select_circuit.CircuitViewModel

@Composable
fun FailedAssertionScreen(
    circuitViewModel: CircuitViewModel = koinInject(),
    vm: FailedAssertionViewModel = koinInject()
) {
    SelectedProjectScreenContent(
        circuitViewModel.selectedCircuitParams.let { vm.selectedFailedAssertion.modifyCircuitParams(it) }
    ) { FailedAssertionPane() }
}
