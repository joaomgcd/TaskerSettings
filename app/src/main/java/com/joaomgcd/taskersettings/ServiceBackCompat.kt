package com.joaomgcd.taskersettings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.ResultReceiver
import android.util.Log
import com.joaomgcd.taskerbackcompat.util.json
import com.joaomgcd.taskerbackcompat.util.stackTraceString
import com.joaomgcd.taskerbackcompat.util.tryGetOrNull
import com.joaomgcd.taskersettings.actions.Action
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.dinglisch.android.tasker.PluginResult

private const val EXTRA_LOGS = "com.joaomgcd.taskersettings.EXTRA_LOGS"
private const val EXTRA_NEEDED_PERMISSIONS = "com.joaomgcd.taskersettings.EXTRA_NEEDED_PERMISSIONS"
private const val EXTRA_NEW_STATE = "com.joaomgcd.taskersettings.EXTRA_NEW_STATE"
private const val EXTRA_PAYLOAD_JSON = "com.joaomgcd.taskersettings.EXTRA_PAYLOAD_JSON"
private const val EXTRA_INPUT = "com.joaomgcd.taskersettings.EXTRA_INPUT"
private const val EXTRA_TYPE = "com.joaomgcd.taskersettings.EXTRA_TYPE"
private suspend fun Context.runAction(bundle: Bundle?, callback: (PluginResult) -> Unit) {
    val logs = arrayListOf<String>()

    try {

        if (bundle == null) throw ServiceBackCompat.ActionRunnerException("No Input Bundle")

        fun log(message: String) = logs.add(message)
        val type = bundle.getString(EXTRA_TYPE)
            ?: throw ServiceBackCompat.ActionRunnerException("No action Type")
        log("Type: $type")

        val input = bundle.getString(EXTRA_INPUT)
            ?: throw ServiceBackCompat.ActionRunnerException("No action input")
        log("Input: ${input.json}")

        val action = Action.getActionFromType(this, type, input)
        val pluginResult = action.run()
        pluginResult.logs = logs.toTypedArray()
        return callback(pluginResult)
    } catch (t: ServiceBackCompat.ActionRunnerException) {
        return callback(PluginResult(false, t.message, logs.toTypedArray()))
    } catch (t: Throwable) {
        return callback(PluginResult(false, t.stackTraceString))
    }

}


class ServiceBackCompat : IntentServiceParallel() {
    private val PluginResult.resultBundle: Bundle
        get() {
            return Bundle().apply {
                if (!success) {
                    putString(EXTRA_ERROR, errorMessage)
                }
                putStringArray(EXTRA_LOGS, logs)
                putStringArray(EXTRA_NEEDED_PERMISSIONS, permissionsNeeded)
                putString(EXTRA_PAYLOAD_JSON, payloadJson)
                newState?.let { putBoolean(EXTRA_NEW_STATE, it) }
            }
        }

    class ActionRunnerException(message: String) : Exception(message)


    override suspend fun onHandleIntent(intent: Intent) {
        val resultReceiver: ResultReceiver = intent.getParcelableExtra(EXTRA_PLUGIN_RECEIVER) ?: return

        "run action intent service".log()
        runAction(intent.extras) {
            val resultCode = if (it.success) Activity.RESULT_OK else Activity.RESULT_CANCELED
            resultReceiver.send(resultCode, it.resultBundle)
        }
    }

    private val binder by lazy {
        object : IServiceBackCompat.Stub() {
            override fun doBackCompatAction(bundle: Bundle?, callback: IServiceBackCompatCallback) {
                coroutineScope.launch {
                    "run action binder".log()
                    runAction(bundle) {
                        tryGetOrNull { callback.handleResult(it.resultBundle) }
                    }
                }
            }
        }
    }

    private fun String.log() = Log.d(TAG, this)
    override fun onBind(intent: Intent?): IBinder {
        "onBind".log()
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".log()
        coroutineScope.cancel()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        val onUnbind = super.onUnbind(intent)
        "onUnbind".log()
        stopSelf()
        return onUnbind
    }
}

private const val TAG = "ServiceBackCompat"