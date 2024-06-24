package core.utils.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import java.util.prefs.Preferences as JavaPreferences

object Preferences {
    private val userPreferences by lazy { JavaPreferences.userRoot() }

    // TODO(platform): default path
    var fdrPath: String by preference(userPreferences, "fdr_path", "C:\\Program Files\\FDR")
}

private inline fun <reified T : Any> preference(preferences: JavaPreferences, key: String, defaultValue: T) =
    PreferenceDelegate(preferences, key, defaultValue, T::class)

private class PreferenceDelegate<T : Any>(
    private val preferences: JavaPreferences,
    private val key: String,
    private val defaultValue: T,
    val type: KClass<T>
) : ReadWriteProperty<Any, T> {

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        with(preferences) {
            when (type) {
                Int::class -> putInt(key, value as Int)
                Long::class -> putLong(key, value as Long)
                Float::class -> putFloat(key, value as Float)
                Boolean::class -> putBoolean(key, value as Boolean)
                String::class -> put(key, value as String)
                ByteArray::class -> putByteArray(key, value as ByteArray)
                else -> error("Unsupported preference type $type.")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return with(preferences) {
            when (type) {
                Int::class -> getInt(key, defaultValue as Int)
                Long::class -> getLong(key, defaultValue as Long)
                Float::class -> getFloat(key, defaultValue as Float)
                Boolean::class -> getBoolean(key, defaultValue as Boolean)
                String::class -> get(key, defaultValue as String)
                ByteArray::class -> getByteArray(key, defaultValue as ByteArray)
                else -> error("Unsupported preference type $type.")
            }
        } as T
    }

}