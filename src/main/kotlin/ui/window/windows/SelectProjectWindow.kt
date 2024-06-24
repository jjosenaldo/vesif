package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import ui.screens.select_project.SelectProjectScreen

@Composable
fun ApplicationScope.SelectProjectWindow() = Window(onCloseRequest = ::exitApplication, title = "Select project") {
    SelectProjectScreen()
}