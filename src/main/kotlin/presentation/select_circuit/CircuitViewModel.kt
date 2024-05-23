package presentation.select_circuit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.files.fileSep
import input.ClearsyCircuitParser
import core.model.Circuit
import verifier.AssertionManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.assertions.AssertionsViewModel
import presentation.select_project.ProjectViewModel

class CircuitViewModel(
    private val circuitParser: ClearsyCircuitParser,
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val assertionsViewModel: AssertionsViewModel by inject()
    private val projectViewModel: ProjectViewModel by inject()

    var loadCircuitState by mutableStateOf<LoadCircuitState>(LoadCircuitInitial())
        private set
    var selectedCircuitPath by mutableStateOf("")
        private set
    var circuit = Circuit.DEFAULT
        private set

    fun selectCircuit(circuitPath: String) {
        selectedCircuitPath = circuitPath
    }

    fun getCircuitName(circuitPath: String): String {
        return circuitPath
            .split(fileSep)
            .last()
            .replace(".xml", "")
    }

    suspend fun loadCircuitFromXml(circuitPath: String, onCircuitLoaded: () -> Unit) {
        loadCircuitState = LoadCircuitLoading()

        try {
            val newCircuit =
                circuitParser.parseCircuitXml(circuitPath = circuitPath, projectPath = projectViewModel.projectPath)
            val types = assertionManager.getAssertionTypes()
            assertionsViewModel.setAssertionsFromTypes(types)
            circuit = newCircuit
            loadCircuitState = LoadCircuitSuccess()
            onCircuitLoaded()
        } catch (e: Exception) {
            loadCircuitState = LoadCircuitError()
        }

    }
}