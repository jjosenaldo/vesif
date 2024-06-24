package ui.screens.select_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import input.ClearsyProjectParser
import ui.common.*

class ProjectViewModel(private val projectParser: ClearsyProjectParser) {
    var projectPath = ""
        private set
    var circuitsPaths by mutableStateOf(listOf<String>())
        private set
    var selectProjectState by mutableStateOf<UiState<Void?>>(UiInitial())
        private set

    suspend fun loadClearsyProject(path: String) {
        selectProjectState = UiLoading()
        try {
            val circuitsPaths = projectParser.getCircuitsPaths(path)
            selectProjectState = UiSuccess(null)
            this.circuitsPaths = circuitsPaths
            projectPath = path
        } catch (e: Exception) {
            selectProjectState = UiError(error = e)
        }
    }
}