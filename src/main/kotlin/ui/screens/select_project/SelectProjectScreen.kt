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
import ui.common.*
import ui.window.AppWindowManager

@Composable
fun SelectProjectScreen(
    viewModel: ProjectViewModel = koinInject(),
    windowManager: AppWindowManager = koinInject()
) {
    var showPicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.selectProjectState

    LaunchedEffect(state) {
        if (state is UiSuccess) {
            windowManager.openProject()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is UiError -> TODO()
            is UiInitial -> Button(onClick = {
                showPicker = true
            }) {
                Text("Select a Clearsy project folder")
            }

            is UiLoading -> Button(onClick = {
            }, enabled = false) {
                CircularProgressIndicator()
            }

            is UiSuccess -> Box {}
        }
        FolderPicker(
            show = showPicker,
            onShowChanged = { showPicker = it },
            onFolderSelected = { scope.launch { viewModel.loadClearsyProject(it) } }
        )
    }
}

