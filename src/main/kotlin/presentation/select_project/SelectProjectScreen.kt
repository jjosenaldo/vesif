package presentation.select_project

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.AppScreen

@Composable
fun SelectProjectScreen(navController: NavHostController) {
    ProjectSelector(onProjectSelected = { navController.navigate(AppScreen.SelectCircuit.name) })
}

@Composable
fun ProjectSelector(
    viewModel: ProjectViewModel = koinInject(),
    onProjectSelected: () -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.selectProjectState

    Column {
        when (state) {
            is SelectProjectError -> TODO()
            is SelectProjectInitial -> Button(onClick = {
                showPicker = true
            }) {
                Text("Select a Clearsy project folder")
            }

            is SelectProjectLoading -> Button(onClick = {
            }, enabled = false) {
                CircularProgressIndicator()
            }

            is SelectProjectSuccess -> Text("")
        }
        ClearsyProjectPicker(
            show = showPicker,
            onShowChanged = { showPicker = it },
            onFolderSelected = { scope.launch { viewModel.loadClearsyProject(it, onProjectSelected) } }
        )
    }
}


@Composable
private fun ClearsyProjectPicker(
    show: Boolean,
    onShowChanged: (Boolean) -> Unit,
    onFolderSelected: (String) -> Unit
) {
    DirectoryPicker(show) { path ->
        onShowChanged(false)
        onFolderSelected(path ?: "")
    }
}
