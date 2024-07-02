package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import ui.common.AppWindow
import ui.screens.select_project.SelectProjectScreen

@Composable
fun ApplicationScope.SelectProjectWindow() = AppWindow(onCloseRequest = ::exitApplication, title = "Select project") {
    SelectProjectScreen()
}