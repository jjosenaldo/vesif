package ui.screens.settings

import core.utils.preferences.Preferences
import verifier.util.FdrLoader

sealed class SettingConfig(val id: SettingId) {
    abstract val errorMessage: String?
    abstract fun save()
}

data class FdrPathSetting(
    val data: String,
    private val preferences: Preferences,
    private val fdrLoader: FdrLoader
) : SettingConfig(SettingId.fdrPath) {
    override var errorMessage: String? = null
        private set

    suspend fun validate() {
        try {
            fdrLoader.checkFdrPath(data)
            errorMessage = null
        } catch (e: Throwable) {
            errorMessage = "Invalid FDR path"
        }
    }

    override fun save() {
        preferences.fdrPath = data
    }

}