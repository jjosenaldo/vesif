package ui.screens.main.sub_screens.assertions

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.main.MainScreenContent
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel

@Composable
fun AssertionsScreen(
    circuitViewModel: CircuitViewModel = koinInject(),
    assertionsViewModel: AssertionsViewModel = koinInject()
) {
    MainScreenContent(
        assertionsViewModel.uiCircuitModifier(circuitViewModel.selectedCircuitParams)
    ) { AssertionsPane() }
}
