package presentation.circuit

import input.CircuitInputManager
import org.example.core.model.Circuit
import org.example.verifier.fdr.AssertionManager
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.assertions.AssertionsViewModel

class CircuitViewModel(
    private val circuitInputManager: CircuitInputManager,
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val assertionsViewModel: AssertionsViewModel by inject()
    var circuit = Circuit.DEFAULT
        private set

    fun loadCircuitFromXml(xmlFile: String) {
        circuit = circuitInputManager.parseCircuitXml(xmlFile)
        val types = assertionManager.getAssertionTypes()
        assertionsViewModel.setAssertionsFromTypes(types)
    }
}