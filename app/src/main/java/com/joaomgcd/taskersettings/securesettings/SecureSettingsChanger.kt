package com.joaomgcd.taskersettings.securesettings

import android.content.ContentResolver
import android.content.Context

open class ExceptionSecureSetting(message: String) : RuntimeException(message)


private fun <T> doForSetting(context: Context, secureSetting: SecureSetting, block: SecureSettingsChanger.() -> T): T {
    val changer = getChanger(context, secureSetting.type)
    return changer.block()
}


fun put(context: Context, secureSetting: SecureSettingWithValue): Boolean {
    return doForSetting(context, secureSetting) {
        try {
            put(secureSetting.key, secureSetting.value)
        } catch (ex: SecurityException) {
            throw ExceptionSecureSetting("No permission to change setting.")
        }

    }
}

fun get(context: Context, secureSetting: SecureSetting): String {
    return doForSetting(context, secureSetting) { get(secureSetting.key) }
}


abstract class SecureSettingsChanger(val context: Context) {
    protected val contentResolver: ContentResolver get() = context.contentResolver

    fun put(key: String, value: Int) = put(key, value.toString())
    abstract val type: SecureSettingType
    abstract fun put(key: String, value: String): Boolean
    abstract fun get(key: String): String


}

abstract class SecureSettingsChangerNoRoot(context: Context) : SecureSettingsChanger(context) {

    override fun put(key: String, value: String) = type.setter(contentResolver, key, value);
    override fun get(key: String): String = type.getter(contentResolver, key) ?: "";


}

class SecureSettingsChangerGlobal(context: Context) : SecureSettingsChangerNoRoot(context) {
    override val type = SecureSettingType.Global
}


class SecureSettingsChangerSecure(context: Context) : SecureSettingsChangerNoRoot(context) {
    override val type = SecureSettingType.Secure

}

class SecureSettingsChangerSystem(context: Context) : SecureSettingsChangerNoRoot(context) {
    override val type = SecureSettingType.System
}
