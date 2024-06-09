package ui.screens.project_selected

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.navigation.AppNavigator
import ui.screens.project_selected.sub_screens.assertions.AssertionsScreen
import ui.screens.project_selected.sub_screens.failed_assertion.FailedAssertionScreen
import ui.screens.project_selected.sub_screens.select_circuit.SelectCircuitScreen

@Composable
fun ProjectSelectedScreen(
    navigator: AppNavigator = koinInject()
) {
    when (navigator.currentProjectSelectedSubScreen) {
        ProjectSelectedSubScreenId.SelectCircuit -> SelectCircuitScreen()
        ProjectSelectedSubScreenId.Assertions -> AssertionsScreen()
        ProjectSelectedSubScreenId.FailedAssertion -> FailedAssertionScreen()
        ProjectSelectedSubScreenId.None -> Box { }
    }
}
