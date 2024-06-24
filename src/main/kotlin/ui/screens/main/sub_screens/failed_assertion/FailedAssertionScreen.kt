package ui.screens.main.sub_screens.failed_assertion

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.main.MainScreenContent
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel

@Composable
fun FailedAssertionScreen(
    circuitViewModel: CircuitViewModel = koinInject(),
    vm: FailedAssertionViewModel = koinInject()
) {
    MainScreenContent(
        circuitViewModel.selectedCircuitParams.let { vm.selectedFailedAssertion.modifyCircuitParams(it) }
    ) { FailedAssertionPane() }
}
