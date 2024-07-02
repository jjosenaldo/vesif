package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.*
import androidx.compose.ui.window.ApplicationScope
import core.utils.files.fileSep
import org.koin.compose.koinInject
import ui.common.AppMenuBar
import ui.common.AppWindow
import ui.screens.main.MainScreen
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel
import ui.screens.select_project.ProjectViewModel

// TODO(ft): window title
@Composable
fun ApplicationScope.MainWindow(
    circuitViewModel: CircuitViewModel = koinInject(),
    projectViewModel: ProjectViewModel = koinInject()
) =
    AppWindow(
        onCloseRequest = ::exitApplication,
        title = projectViewModel.projectPath.split(fileSep).last(),
        onKeyEvent = {
            if (it.isCtrlPressed && it.type == KeyEventType.KeyDown && it.key in listOf(Key.Equals, Key.Minus)) {
                circuitViewModel.zoom(it.key == Key.Equals)
                true
            } else {
                // let other handlers receive this event
                false
            }
        }) {
        AppMenuBar()
        MainScreen()
    }

