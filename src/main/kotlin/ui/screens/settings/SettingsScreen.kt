package ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.common.*
import ui.window.AppWindowManager
import ui.window.WindowId

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = koinInject(),
    windowManager: AppWindowManager = koinInject()
) {
    Column {
        settingsViewModel.settingsStates.forEach {
            SettingRow(it)
        }

        Row {
            Button(
                onClick = {
                    settingsViewModel.saveSettings()
                    windowManager.closeSettings()
                },
                enabled = settingsViewModel.isSaveEnabled(),
            ) {
                Text("Save")
            }
            Button(onClick = windowManager::closeSettings) {
                Text("Cancel")
            }
        }
    }
}

@Composable
private fun SettingRow(data: UiState<SettingConfig>) {
    data.data.let {
        when (it) {
            is FdrPathSetting -> FdrPathSettingRow(it)
            null -> {}
        }
    }
}

@Composable
private fun FdrPathSettingRow(
    data: FdrPathSetting,
    settingsViewModel: SettingsViewModel = koinInject()
) {
    val scope = rememberCoroutineScope()
    var showPicker by remember { mutableStateOf(false) }
    Text("FDR location")
    Column {
        Row {
            Text(data.data)
            Button(onClick = { showPicker = true }) {
                Text("Select")
            }
        }
        data.errorMessage?.let {
            Text(it, color = Color.Red)
        }
    }

    FolderPicker(
        show = showPicker,
        onShowChanged = { showPicker = it },
        onFolderSelected = { scope.launch { settingsViewModel.setFdrLocation(it) } }
    )
}


private fun AppWindowManager.closeSettings() {
    close(WindowId.Settings)
}