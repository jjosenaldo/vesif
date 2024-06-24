package ui.screens.select_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.common.FolderPicker
import ui.navigation.AppNavigator

@Composable
fun SelectProjectScreen(
    navigator: AppNavigator = koinInject(),
    viewModel: ProjectViewModel = koinInject()
) {
    var showPicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.selectProjectState

    LaunchedEffect(state) {
        if (state is SelectProjectSuccess) {
            navigator.navToSelectCircuit()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

            is SelectProjectSuccess -> Box {}
        }
        FolderPicker(
            show = showPicker,
            onShowChanged = { showPicker = it },
            onFolderSelected = { scope.launch { viewModel.loadClearsyProject(it) } }
        )
    }
}

