package com.joaomgcd.taskersettings.securesettings

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import java.util.*

fun getChanger(context: Context, secureSettingType: SecureSettingType): SecureSettingsChanger {
    val changer = when (secureSettingType) {
        SecureSettingType.Global -> SecureSettingsChangerGlobal(context)
        SecureSettingType.Secure -> SecureSettingsChangerSecure(context)
        SecureSettingType.System -> SecureSettingsChangerSystem(context)
    }
    return changer
}

enum class SecureSettingType(val settingsClass: Class<*>, val getter: (ContentResolver, String) -> String?, val setter: (ContentResolver, String, String) -> Boolean) {
    Global(
            Settings.Global::class.java,
            { cr, key -> android.provider.Settings.Global.getString(cr, key) },
            { cr, key, value -> android.provider.Settings.Global.putString(cr, key, value) }
    ),
    Secure(
            Settings.Secure::class.java,
            { cr, key -> android.provider.Settings.Secure.getString(cr, key) },
            { cr, key, value -> android.provider.Settings.Secure.putString(cr, key, value) }
    ),
    System(
            Settings.System::class.java,
            { cr, key -> android.provider.Settings.System.getString(cr, key) },
            { cr, key, value -> android.provider.Settings.System.putString(cr, key, value) }
    )
}

open class SecureSetting(val type: SecureSettingType, val key: String, val minApi: Int = 0, val maxApi: Int = Int.MAX_VALUE)
class SecureSettingWithValue(type: SecureSettingType, key: String, val value: String, minApi: Int = 0, maxApi: Int = Int.MAX_VALUE) : SecureSetting(type, key, minApi, maxApi) {
    override fun equals(other: Any?): Boolean {
        if (!(other is SecureSettingWithValue)) return false
        return other.key == key && other.value == value && other.type == type
    }

    override fun hashCode() = type.hashCode() xor key.hashCode() xor value.hashCode()
}

class SecureSettingWithValueChange(val old: SecureSettingWithValue, val new: SecureSettingWithValue) {
    override fun toString() = "$old => $new"
}
