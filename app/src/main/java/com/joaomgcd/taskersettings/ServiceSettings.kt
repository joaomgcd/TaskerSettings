package com.joaomgcd.taskersettings

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import com.joaomgcd.taskersettings.securesettings.SecureSettingType
import com.joaomgcd.taskersettings.securesettings.SecureSettingWithValue
import com.joaomgcd.taskersettings.securesettings.put
import net.dinglisch.android.tasker.PluginResult

const val EXTRA_PLUGIN_RECEIVER = "com.joaomgcd.taskersettings.EXTRA_PLUGIN_RECEIVER"
const val EXTRA_ERROR = "com.joaomgcd.taskersettings.EXTRA_ERROR"
private const val EXTRA_SETTING_KEY = "com.joaomgcd.taskersettings.EXTRA_SETTING_KEY"
private const val EXTRA_SETTING_VALUE = "com.joaomgcd.taskersettings.EXTRA_SETTING_VALUE"

class ServiceSettings : IntentService("ServiceSettings") {
    private fun ResultReceiver.cancel(message: String) = send(Activity.RESULT_CANCELED, getResultBundle(false, message))
    private fun getResultBundle(success: Boolean, error: String? = null) = getResultBundle(PluginResult(success, error))
    private fun getResultBundle(result: PluginResult) = Bundle().apply { if (!result.success) putString(EXTRA_ERROR, result.errorMessage) }
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) return

        var resultReceiver: ResultReceiver? = null
        try {
            resultReceiver = intent.getParcelableExtra(EXTRA_PLUGIN_RECEIVER) ?: return
            val taskerSettingKey = intent.getStringExtra(EXTRA_SETTING_KEY)
            val taskerSettingValue = intent.getStringExtra(EXTRA_SETTING_VALUE)

            if (taskerSettingKey == null) {
                resultReceiver.cancel("No Key to set")
                return
            }
            if (taskerSettingValue == null) {
                resultReceiver.cancel("No Value to set")
                return
            }
            val result = try {
                if (put(this, SecureSettingWithValue(SecureSettingType.System, taskerSettingKey, taskerSettingValue))) {
                    PluginResult(true)
                } else {
                    PluginResult(false, "Unknown Error")
                }
            } catch (t: Throwable) {
                PluginResult(false, t.message)
            }
            val resultCode = if (result.success) Activity.RESULT_OK else Activity.RESULT_CANCELED
            resultReceiver.send(resultCode, getResultBundle(result))
        } catch (t: Throwable) {
            resultReceiver?.cancel(t.toString())
        }
    }
}