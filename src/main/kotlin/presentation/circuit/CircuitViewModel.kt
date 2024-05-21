package presentation.circuit

import input.CircuitInputManager
import core.model.Circuit
import verifier.AssertionManager
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

    // TODO: validate documents
    suspend fun loadCircuitFromXml(objectsPath: String, circuitPath: String) {
        circuit = circuitInputManager.parseCircuitXml(objectsPath = objectsPath, circuitPath = circuitPath)
        val types = assertionManager.getAssertionTypes()
        assertionsViewModel.setAssertionsFromTypes(types)
    }
}