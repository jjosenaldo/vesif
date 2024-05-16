package presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.assertions.AssertionState
import presentation.assertions.AssertionsView
import presentation.assertions.AssertionsViewModel
import presentation.circuit.CircuitViewModel
import presentation.circuit.CircuitXmlSelector

@Composable
fun AppContent() {
    val vm = koinInject<AssertionsViewModel>()
    val circuitVm = koinInject<CircuitViewModel>()
    val assertionsVm = remember { vm }
    val scope = rememberCoroutineScope()

    when (val assertions = assertionsVm.assertions) {
        listOf<AssertionState>() -> CircuitXmlSelector(onFilesSelected = { objectsPath, circuitPath ->
            scope.launch {
                circuitVm.loadCircuitFromXml(
                    circuitPath = circuitPath,
                    objectsPath = objectsPath
                )
            }
        })

        else -> AssertionsView(assertions, failedAssertion = assertionsVm.assertionDetails)
    }
}