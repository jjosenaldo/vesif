package ui.screens.project_selected.failed_assertion

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.common.CircuitWithSidePane

@Composable
fun FailedAssertionScreen(
    vm: FailedAssertionViewModel = koinInject()
) {
    CircuitWithSidePane(
        vm.selectedFailedAssertion.circuitParams
    ) { FailedAssertionPane() }
}
