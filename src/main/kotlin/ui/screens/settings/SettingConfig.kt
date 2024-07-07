package ui.screens.settings

import core.utils.preferences.Preferences
import verifier.util.FdrLoader

sealed class SettingConfig(val id: SettingId) {
    abstract fun save()
    open fun validate(): String? = null
    open suspend fun validateAsync(): String? = null
    abstract fun copy(data: String): SettingConfig
}

data class TimeoutSetting(
    val data: String,
    private val preferences: Preferences
) : SettingConfig(SettingId.TimeoutTime) {
    private val minTimeMinutes = 1
    private val errorTime = minTimeMinutes - 1

    constructor(
        data: Int,
        preferences: Preferences
    ) : this(data.toString(), preferences)

    private val time: Int
        get() {
            val dataAsInt = data.trim().toIntOrNull() ?: return errorTime
            if (dataAsInt !in minTimeMinutes..MAX_TIME_MINUTES)
                return errorTime

            return dataAsInt
        }

    companion object {
        const val MAX_TIME_MINUTES = 100000
        const val MAX_TIME_MINUTES_LENGTH = MAX_TIME_MINUTES.toString().length
    }

    override fun validate(): String? {
        return if (time == errorTime) "Must be a number between $minTimeMinutes and $MAX_TIME_MINUTES" else null
    }

    override fun save() {
        preferences.timeoutTimeMinutes = time
    }

    override fun copy(data: String): SettingConfig {
        return copy(data = data, preferences = preferences)
    }
}

data class FdrPathSetting(
    val data: String,
    private val preferences: Preferences,
    private val fdrLoader: FdrLoader
) : SettingConfig(SettingId.FdrPath) {
    override suspend fun validateAsync(): String? {
        try {
            fdrLoader.checkFdrPath(data)
            return null
        } catch (e: Throwable) {
            return "Invalid FDR path"
        }
    }

    override fun save() {
        preferences.fdrPath = data
    }

    override fun copy(data: String): SettingConfig {
        return copy(data = data, preferences = preferences)
    }
}

