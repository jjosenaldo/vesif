package presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.koinInject
import presentation.assertions.AssertionState
import presentation.assertions.AssertionsView
import presentation.assertions.AssertionsViewModel
import presentation.circuit.CircuitViewModel
import presentation.circuit.XmlSelector

@Composable
fun AppContent() {
    val vm = koinInject<AssertionsViewModel>()
    val circuitVm = koinInject<CircuitViewModel>()
    val assertionsVm = remember { vm }

    when (val assertions = assertionsVm.assertions) {
        listOf<AssertionState>() -> XmlSelector(onFileSelected = circuitVm::loadCircuitFromXml)
        else -> AssertionsView(assertions, failedAssertion = assertionsVm.assertionDetails)
    }
}