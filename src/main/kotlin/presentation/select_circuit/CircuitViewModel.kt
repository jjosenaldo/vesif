package presentation.select_circuit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.files.fileSep
import input.ClearsyCircuitParser
import verifier.AssertionManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.assertions.AssertionsViewModel
import presentation.model.UiCircuit
import presentation.select_project.ProjectViewModel
import java.io.File

class CircuitViewModel(
    private val circuitParser: ClearsyCircuitParser,
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val assertionsViewModel: AssertionsViewModel by inject()
    private val projectViewModel: ProjectViewModel by inject()
    private var loadedCircuits by mutableStateOf<Map<String, UiCircuit>>(mapOf())
    private val loadedCircuitImages = mutableMapOf<String, File>()

    var loadCircuitState by mutableStateOf<LoadCircuitState>(LoadCircuitInitial())
        private set
    var selectedCircuitPath by mutableStateOf("")
        private set
    var selectedCircuit = UiCircuit.DEFAULT
        private set
    var selectedCircuitImage by mutableStateOf<File?>(null)
        private set

    suspend fun selectCircuit(circuitPath: String) {
        loadCircuitState = LoadCircuitLoading()

        try {
            val newCircuit: UiCircuit
            val newImage: File?
            val existingCircuit = loadedCircuits[circuitPath]

            if (existingCircuit == null) {
                val clearsyCircuit = circuitParser.parseClearsyCircuit(
                    circuitPath = circuitPath,
                    projectPath = projectViewModel.projectPath
                )
                newCircuit = UiCircuit(clearsyCircuit, clearsyCircuit.circuitImagePath)
                val image = File(newCircuit.imagePath)
                loadedCircuitImages[circuitPath] = image
                newImage = image
//                val types = assertionManager.getAssertionTypes()
//                assertionsViewModel.setAssertionsFromTypes(types)

            } else {
                newCircuit = existingCircuit
                newImage = loadedCircuitImages[circuitPath]
            }

            selectedCircuitImage = newImage
            selectedCircuit = newCircuit
            loadCircuitState = LoadCircuitSuccess()
            selectedCircuitPath = circuitPath
        } catch (e: Exception) {
            loadCircuitState = LoadCircuitError()
        }
    }

    fun setAssertionTypes() {
        assertionsViewModel.setAssertionsFromTypes(assertionManager.getAssertionTypes())
    }

    fun getCircuitName(circuitPath: String): String {
        return circuitPath
            .split(fileSep)
            .last()
            .replace(".xml", "")
    }
}