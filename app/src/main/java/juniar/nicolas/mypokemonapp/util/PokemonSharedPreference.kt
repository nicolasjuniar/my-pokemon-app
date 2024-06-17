package juniar.nicolas.mypokemonapp.util

import android.content.SharedPreferences
import kotlin.reflect.KProperty

class PokemonSharedPreference<T>(
    private val sharedPreferences: SharedPreferences,
    val name: String,
    private val default: T
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = with(sharedPreferences) {
        val res: Any = when (default) {
            is Boolean -> getBoolean(name, default)
            is Int -> getInt(name, default)
            is Float -> getFloat(name, default)
            is Long -> getLong(name, default)
            is String -> getString(name, default) ?: ""
            is Double -> getString(name, default.toString())?.toDouble() as Double
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }
        res as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(name, value)
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is Double -> putString(name, value.toString())
                else -> throw IllegalArgumentException("This type can't be saved into Preferences")
            }.apply()
        }
}