package presentation.select_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import input.ClearsyProjectParser

class ProjectViewModel(private val projectParser: ClearsyProjectParser) {
    var projectPath = ""
        private set
    var circuitsPaths by mutableStateOf(listOf<String>())
        private set
    var selectProjectState by mutableStateOf<SelectProjectState>(SelectProjectInitial())
        private set

    suspend fun loadClearsyProject(path: String, onProjectSelected: () -> Unit) {
        selectProjectState = SelectProjectLoading()
        try {
            val circuitsPaths = projectParser.getCircuitsPaths(path)
            selectProjectState = SelectProjectSuccess()
            this.circuitsPaths = circuitsPaths
            projectPath = path
            onProjectSelected()
        } catch (e: Exception) {
            selectProjectState = SelectProjectError()
        }
    }
}