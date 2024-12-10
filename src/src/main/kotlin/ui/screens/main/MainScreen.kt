package ui.screens.main

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import ui.navigation.AppNavigator
import ui.screens.main.sub_screens.assertions.AssertionsScreen
import ui.screens.main.sub_screens.failed_assertion.FailedAssertionScreen
import ui.screens.main.sub_screens.select_circuit.SelectCircuitScreen

@Composable
fun MainScreen(
    navigator: AppNavigator = koinInject()
) {
    when (navigator.currentMainScreen) {
        MainScreenId.SelectCircuit -> SelectCircuitScreen()
        MainScreenId.Assertions -> AssertionsScreen()
        MainScreenId.FailedAssertion -> FailedAssertionScreen()
    }
}
