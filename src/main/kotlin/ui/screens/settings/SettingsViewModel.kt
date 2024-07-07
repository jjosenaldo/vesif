package ui.screens.settings

import androidx.compose.runtime.*
import core.utils.preferences.Preferences
import ui.common.UiError
import ui.common.UiLoading
import ui.common.UiState
import ui.common.UiSuccess
import verifier.util.FdrLoader

class SettingsViewModel(private val preferences: Preferences, private val fdrLoader: FdrLoader) {
    var settingsStates = mutableStateMapOf<SettingId, UiState<SettingConfig>>()
        private set

    init {
        loadCurrentSettings()
    }

    fun loadCurrentSettings() {
        settingsStates.clear()
        settingsStates.putAll(
            mapOf(
                SettingId.FdrPath to UiSuccess(FdrPathSetting(preferences.fdrPath, preferences, fdrLoader)),
                SettingId.TimeoutTime to UiSuccess(TimeoutSetting(preferences.timeoutTimeMinutes, preferences))
            )
        )
    }

    fun saveSettings() {
        settingsStates.values.forEach { it.data?.save() }
    }

    fun isSaveEnabled(): Boolean {
        return settingsStates.values.all { it is UiSuccess }
    }

    fun setSetting(settingId: SettingId, newData: String) {
        val oldSetting = settingsStates[settingId]?.data ?: return
        val newSetting = oldSetting.copy(data = newData)
        setSettingState(settingId, newSetting, newSetting.validate())
    }

    suspend fun setSettingAsync(settingId: SettingId, newData: String) {
        val oldSetting = settingsStates[settingId]?.data ?: return
        val newSetting = oldSetting.copy(data = newData)
        settingsStates[settingId] = UiLoading(newSetting)
        setSettingState(settingId, newSetting, newSetting.validateAsync())
    }

    private fun setSettingState(settingId: SettingId, newConfig: SettingConfig, validation: String?) {
        settingsStates[settingId] =
            if (validation == null) UiSuccess(newConfig) else UiError(message = validation, data = newConfig)
    }
}