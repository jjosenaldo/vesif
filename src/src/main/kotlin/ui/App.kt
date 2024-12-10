package ui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ApplicationScope
import core.utils.files.FileManager
import org.koin.compose.koinInject
import ui.common.*
import ui.window.AppWindowManager
import ui.window.WindowId
import ui.window.windows.MainWindow
import ui.window.windows.SelectProjectWindow
import ui.window.windows.SettingsWindow
import java.awt.Font
import javax.swing.UIManager

@Composable
fun ApplicationScope.App(windowManager: AppWindowManager = koinInject()) {
    MaterialTheme(
        colorScheme = darkColorScheme().copy(
            primary = defaultInputSelectedColor,
            onSurface = defaultInputUnselectedColor,
            surface = defaultInputSelectedColor
        )
    ) {
        CompositionLocalProvider(
            LocalPrimaryButtonColors provides defaultPrimaryButtonColors,
            LocalSecondaryButtonColors provides defaultSecondaryButtonColors,
            LocalShapes provides LocalShapes.current,
            LocalTextStyle provides LocalTextStyle.current.merge(
                defaultTextStyle
            )
        ) {
            loadSwingFonts()

            for (windowId in windowManager.windows) {
                when (windowId) {
                    WindowId.Main -> MainWindow()
                    WindowId.Settings -> SettingsWindow()
                    WindowId.SelectProject -> SelectProjectWindow()
                }
            }
        }
    }
}

// TODO(td): create fonts once at the beginning of the application and derive them later
@Composable
private fun loadSwingFonts() {
    val font = Font
        .createFont(Font.TRUETYPE_FONT, FileManager.getResource("fonts/segoe/segoeuithis.ttf"))
        .deriveFont(Font.PLAIN, LocalTextStyle.current.fontSize.value)
//    val fontColor = LocalTextStyle.current.color.toSwingColor()
//    val backgroundColor = backgroundColor.toSwingColor()
    UIManager.put("Menu.font", font)
//    UIManager.put("Menu.foreground", fontColor)
//    UIManager.put("Menu.background", backgroundColor)
//    UIManager.put("MenuItem.foreground", fontColor)
    UIManager.put("MenuItem.font", font)
//    UIManager.put("MenuItem.background", backgroundColor)
//    UIManager.put("PopupMenu.background", backgroundColor)

}
