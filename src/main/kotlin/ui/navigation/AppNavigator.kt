package ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screens.main.MainScreenId
import ui.screens.main.sub_screens.assertions.AssertionsViewModel
import ui.screens.main.sub_screens.failed_assertion.FailedAssertionViewModel
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class AppNavigator : KoinComponent {
    private val failedAssertionsViewModel: FailedAssertionViewModel by inject()
    private val assertionsViewModel: AssertionsViewModel by inject()
    private val circuitViewModel: CircuitViewModel by inject()

    var currentMainScreen by mutableStateOf(MainScreenId.SelectCircuit)
        private set

    fun navBack() {
        navToScreen(
            when (currentMainScreen) {
                MainScreenId.Assertions -> {
                    assertionsViewModel.onGoBack()
                    MainScreenId.SelectCircuit
                }

                MainScreenId.FailedAssertion -> MainScreenId.Assertions
                else -> currentMainScreen
            }
        )
    }

    fun navToSelectCircuit() {
        circuitViewModel.reset()
        navToScreen(MainScreenId.SelectCircuit)
    }

    fun navToAssertions(types: List<AssertionType>) {
        assertionsViewModel.setup(types)
        navToScreen(MainScreenId.Assertions)
    }

    fun navToFailedAssertions(results: List<AssertionRunResult>) {
        failedAssertionsViewModel.setup(results)
        navToScreen(MainScreenId.FailedAssertion)
    }

    private fun navToScreen(destination: MainScreenId) {
        currentMainScreen = destination
    }
}