package com.joaomgcd.taskersettings

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import com.joaomgcd.taskerbackcompat.util.json
import com.joaomgcd.taskerbackcompat.util.stackTraceString
import com.joaomgcd.taskersettings.actions.Action
import net.dinglisch.android.tasker.PluginResult

private const val EXTRA_LOGS = "com.joaomgcd.taskersettings.EXTRA_LOGS"
private const val EXTRA_NEEDED_PERMISSIONS = "com.joaomgcd.taskersettings.EXTRA_NEEDED_PERMISSIONS"
private const val EXTRA_NEW_STATE = "com.joaomgcd.taskersettings.EXTRA_NEW_STATE"
private const val EXTRA_INPUT = "com.joaomgcd.taskersettings.EXTRA_INPUT"
private const val EXTRA_TYPE = "com.joaomgcd.taskersettings.EXTRA_TYPE"
private const val EXTRA_PAYLOAD_JSON = "com.joaomgcd.taskersettings.EXTRA_PAYLOAD_JSON"

class ServiceBackCompat : IntentService("ServiceRun") {
    private fun ResultReceiver.cancel(message: String, logs: Array<String>? = null) = send(Activity.RESULT_CANCELED, getResultBundle(false, message))
    private fun getResultBundle(success: Boolean, error: String? = null, logs: Array<String>? = null) = getResultBundle(PluginResult(success, error, logs))
    private fun getResultBundle(result: PluginResult) =
            Bundle().apply {
                if (!result.success) {
                    putString(EXTRA_ERROR, result.errorMessage)
                }
                putStringArray(EXTRA_LOGS, result.logs)
                putStringArray(EXTRA_NEEDED_PERMISSIONS, result.permissionsNeeded)
                putString(EXTRA_PAYLOAD_JSON, result.payloadJson)
                result.newState?.let { putBoolean(EXTRA_NEW_STATE, it) }
            }

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) return

        var resultReceiver: ResultReceiver? = null
        val logs = arrayListOf<String>()
        fun cancel(message: String): Unit = resultReceiver?.cancel(message, logs.toTypedArray())
                ?: Unit

        fun log(message: String) = logs.add(message)
        try {
            resultReceiver = intent.getParcelableExtra(EXTRA_PLUGIN_RECEIVER)
                    ?: return cancel("No Result Receiver")
            val type = intent.getStringExtra(EXTRA_TYPE)
                    ?: return cancel("No action Type")
            log("Type: $type")

            val input = intent.getStringExtra(EXTRA_INPUT)
                    ?: return cancel("No action input")
            log("Input: ${input.json}")

            val action = Action.getActionFromType(this, type, input)
                    ?: return cancel("No action from type ($type) and input ($input)")

            val result = action.run().blockingGet()

            val resultCode = if (result.success) Activity.RESULT_OK else Activity.RESULT_CANCELED
            resultReceiver.send(resultCode, getResultBundle(result))
        } catch (t: Throwable) {
            resultReceiver?.cancel(t.stackTraceString)
        }
    }

}