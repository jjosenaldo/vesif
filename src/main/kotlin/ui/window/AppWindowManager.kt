package ui.window

import androidx.compose.runtime.mutableStateListOf

class AppWindowManager {
    private val _windows = mutableStateListOf(WindowId.MainWindow)
    val windows: List<WindowId> get() = _windows

    fun open(window: WindowId) {
        _windows.add(window)
    }

    fun close(window: WindowId) {
        _windows.remove(window)
    }
}