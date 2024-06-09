package ui.screens.project_selected.sub_screens.select_circuit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.files.fileSep
import input.ClearsyCircuitParser
import input.model.ClearsyCircuit
import verifier.AssertionManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.AppNavigator
import ui.screens.select_project.ProjectViewModel
import java.io.File

class CircuitViewModel(
    private val circuitParser: ClearsyCircuitParser,
    private val assertionManager: AssertionManager
) : KoinComponent {
    private val projectViewModel: ProjectViewModel by inject()
    private var loadedCircuits by mutableStateOf<Map<String, ClearsyCircuit>>(mapOf())
    private val loadedCircuitImages = mutableMapOf<String, File>()

    var loadCircuitState by mutableStateOf<LoadCircuitState>(LoadCircuitInitial())
        private set
    var selectedCircuitPath by mutableStateOf("")
        private set
    var selectedCircuit = ClearsyCircuit.DEFAULT
        private set
    var selectedCircuitImage by mutableStateOf<File?>(null)
        private set

    suspend fun selectCircuit(circuitPath: String) {
        loadCircuitState = LoadCircuitLoading()

        try {
            val newCircuit: ClearsyCircuit
            val newImage: File?
            val existingCircuit = loadedCircuits[circuitPath]

            if (existingCircuit == null) {
                val clearsyCircuit = circuitParser.parseClearsyCircuit(
                    circuitPath = circuitPath,
                    projectPath = projectViewModel.projectPath
                )
                newCircuit = clearsyCircuit
                val image = File(newCircuit.circuitImagePath)
                loadedCircuitImages[circuitPath] = image
                newImage = image
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

    fun confirmCircuitSelection(navigator: AppNavigator) {
        navigator.navToAssertions(assertionManager.getAssertionTypes())
    }

    fun getCircuitName(circuitPath: String): String {
        return circuitPath
            .split(fileSep)
            .last()
            .replace(".xml", "")
    }
}