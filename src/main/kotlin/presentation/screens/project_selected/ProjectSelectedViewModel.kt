package presentation.screens.project_selected

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class ProjectSelectedViewModel {
    var currentScreen by mutableStateOf(ProjectSelectedScreenId.None)
}