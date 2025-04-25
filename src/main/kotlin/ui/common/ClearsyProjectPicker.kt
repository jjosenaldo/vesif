package ui.common

import androidx.compose.runtime.Composable
import input.model.InvalidClearsyCircuitException
import input.model.InvalidClearsyProjectException
import input.model.XmlComponentAttributeException
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
    LoadProjectErrorView()

    FolderPicker(
        show = show,
        onShowChanged = onShowChanged,
        onFolderSelected = {
            if (it == null) return@FolderPicker

            scope.launch {
                projectViewModel.loadClearsyProject(it)

                if (projectViewModel.selectProjectState is UiSuccess) {
                    navigator.navToSelectCircuit(projectViewModel.selectProjectState.data ?: listOf())
                }
            }
        }
    )
}


@Composable
private fun LoadProjectErrorView(
    viewModel: ProjectViewModel = koinInject()
) {
    val state = viewModel.selectProjectState
    if (state !is UiError) return

    ErrorDialog(
        when(state.error ?: Exception()) {
            is XmlComponentAttributeException -> ErrorDialogConfig(
                "Invalid component attribute value",
                (state.error as XmlComponentAttributeException).message ?: "",
                onDialogClosed = viewModel::reset
            )
            is InvalidClearsyProjectException -> ErrorDialogConfig(
                "Invalid Clearsy project",
                (state.error as InvalidClearsyProjectException).message ?: "",
                onDialogClosed = viewModel::reset
            )
            is InvalidClearsyCircuitException -> ErrorDialogConfig(
                "Invalid Clearsy circuit",
                (state.error as InvalidClearsyCircuitException).message ?: "",
                onDialogClosed = viewModel::reset
            )
            else -> ErrorDialogConfig(
                "Error",
                "Some unexpected error occurred. Try again with another project.",
                onDialogClosed = viewModel::reset
            )
        }
    )
}