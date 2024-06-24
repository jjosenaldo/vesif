package ui.common

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.navigation.AppNavigator
import ui.screens.select_project.ProjectViewModel

@Composable
fun ClearsyProjectPicker(
    show: Boolean,
    onShowChanged: (Boolean) -> Unit,
    scope: CoroutineScope,
    projectViewModel: ProjectViewModel = koinInject(),
    navigator: AppNavigator = koinInject()
) {
    FolderPicker(
        show = show,
        onShowChanged = onShowChanged,
        onFolderSelected = {
            if (it == null) return@FolderPicker

            scope.launch {
                projectViewModel.loadClearsyProject(it)

                if (projectViewModel.selectProjectState is UiSuccess) {
                    navigator.navToSelectCircuit()
                }
            }
        }
    )
}