package ui.screens.project_selected

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.project_selected.assertions.AssertionsScreen
import ui.screens.project_selected.failed_assertion.FailedAssertionScreen
import ui.screens.project_selected.select_circuit.SelectCircuitScreen

@Composable
fun ProjectSelectedScreen(
    viewModel: ProjectSelectedViewModel = koinInject()
) {
    when (viewModel.currentScreen) {
        ProjectSelectedScreenId.SelectCircuit -> SelectCircuitScreen()
        ProjectSelectedScreenId.Assertions -> AssertionsScreen()
        ProjectSelectedScreenId.FailedAssertion -> FailedAssertionScreen()
        ProjectSelectedScreenId.None -> Box { }
    }
}
