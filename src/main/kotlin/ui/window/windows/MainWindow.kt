package ui.window.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import ui.navigation.AppNavHost

@Composable
fun ApplicationScope.MainWindow() =
    Window(onCloseRequest = ::exitApplication, title = "App") { // TODO(ft): window title
//    AppMenuBar(appWindowManager)
        AppNavHost()
    }