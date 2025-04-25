package ui.screens.select_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import input.ClearsyCircuitParser
import input.ClearsyProjectParser
import input.model.ClearsyCircuit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ui.common.*

class ProjectViewModel(
    private val projectParser: ClearsyProjectParser,
    private val circuitParser: ClearsyCircuitParser,
) {
    var projectPath by mutableStateOf("")
        private set

    var selectProjectState by mutableStateOf<UiState<List<ClearsyCircuit>>>(UiInitial())
        private set

    suspend fun loadClearsyProject(path: String) {
        selectProjectState = UiLoading()
        try {
            val circuitsPaths = projectParser.getCircuitsPaths(path)
            val circuits = loadCircuitsFromProject(circuitsPaths, path)
            selectProjectState = UiSuccess(circuits)
            projectPath = path
        } catch (e: Throwable) {
            selectProjectState = UiError(error = e)
        }
    }

    private suspend fun loadCircuitsFromProject(circuitsPaths: List<String>, projectPath: String) = coroutineScope {
        return@coroutineScope circuitsPaths.map { circuitPath ->
            async(Dispatchers.IO) {
                circuitParser.parseClearsyCircuit(
                    circuitPath = circuitPath,
                    projectPath = projectPath
                )
            }
        }.awaitAll()
    }

    fun reset() {
        projectPath = ""
        selectProjectState = UiInitial()
    }
}