package ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screens.main.MainScreenId
import ui.screens.main.sub_screens.assertions.AssertionsViewModel
import ui.screens.main.sub_screens.failed_assertion.FailedAssertionViewModel
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class AppNavigator : KoinComponent {
    private val failedAssertionsViewModel: FailedAssertionViewModel by inject()
    private val assertionsViewModel: AssertionsViewModel by inject()

    var currentMainScreen by mutableStateOf(MainScreenId.SelectCircuit)
        private set

    fun navBack() {
        when (currentMainScreen) {
            MainScreenId.Assertions -> navToScreen(MainScreenId.SelectCircuit)
            MainScreenId.FailedAssertion -> navToScreen(MainScreenId.Assertions)
            MainScreenId.SelectCircuit -> {}
        }
    }

    fun navToFailedAssertions(results: List<AssertionRunResult>) {
        failedAssertionsViewModel.setup(results)
        navToScreen(MainScreenId.FailedAssertion)
    }

    fun navToAssertions(types: List<AssertionType>) {
        assertionsViewModel.setAssertionsFromTypes(types)
        navToScreen(MainScreenId.Assertions)
    }

    private fun navToScreen(destination: MainScreenId) {
        currentMainScreen = destination
    }
}