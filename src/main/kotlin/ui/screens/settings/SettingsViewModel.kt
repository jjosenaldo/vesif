package ui.screens.settings

import androidx.compose.runtime.*
import core.utils.preferences.Preferences
import ui.common.UiInitial
import ui.common.UiLoading
import ui.common.UiState
import ui.common.UiSuccess
import verifier.util.FdrLoader

class SettingsViewModel(private val preferences: Preferences, private val fdrLoader: FdrLoader) {
    var settingsStates = mutableStateListOf<UiState<SettingConfig>>()
        private set

    init {
        loadCurrentSettings()
    }

    fun loadCurrentSettings() {
        settingsStates.clear()
        settingsStates.add(UiInitial(FdrPathSetting(preferences.fdrPath, preferences, fdrLoader)))
    }

    fun saveSettings() {
        settingsStates.forEach { it.data?.save() }
    }

    fun isSaveEnabled(): Boolean {
        return settingsStates.all { (it is UiSuccess || it is UiInitial) && it.data?.errorMessage == null }
    }

    suspend fun setFdrLocation(newLocation: String?) {
        if (newLocation == null) return

        val (index, oldSetting) = getSettingWithIndex<FdrPathSetting>() ?: return
        val newSetting = oldSetting.copy(data = newLocation)

        settingsStates[index] = UiLoading(newSetting)
        newSetting.validate()
        settingsStates[index] = UiSuccess(newSetting)
    }

    private inline fun <reified T : SettingConfig> getSettingWithIndex(): Pair<Int, T>? {
        val index = settingsStates.indexOfFirst { it.data is T }
        if (index == -1) return null
        val setting = settingsStates[index].data as? T ?: return null

        return Pair(index, setting)
    }
}