package ui.screens.main.sub_screens.select_circuit

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.main.MainScreenContent

@Composable
fun SelectCircuitScreen(
    circuitVm: CircuitViewModel = koinInject()
) {
    MainScreenContent(
        circuitVm.selectedCircuitParams
    ) { SelectCircuitPane() }
}