package ui.screens.project_selected.sub_screens.failed_assertion

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.screens.project_selected.SelectedProjectScreenContent

@Composable
fun FailedAssertionScreen(
    vm: FailedAssertionViewModel = koinInject()
) {
    SelectedProjectScreenContent(
        vm.selectedFailedAssertion.circuitParams
    ) { FailedAssertionPane() }
}
