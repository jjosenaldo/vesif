package ui.screens.select_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        AppButton(
            onClick = {
                showPicker = true
            },
            enabled = state !is UiLoading
        ) {
            if (state is UiLoading)
                CircularProgressIndicator()
            else
                AppText("Select a Clearsy project folder")
        }

        ClearsyProjectPicker(
            show = showPicker,
            onShowChanged = { showPicker = it },
            scope = scope
        )

        if (state is UiError)
            ErrorDialog(
                ErrorDialogConfig(
                    "Invalid project",
                    "The folder provided is not a Clearsy project. Try again with another folder.",
                    onDialogClosed = viewModel::reset
                )
            )
    }
}

