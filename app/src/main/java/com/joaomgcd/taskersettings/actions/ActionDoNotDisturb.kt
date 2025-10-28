package com.joaomgcd.taskersettings.actions

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.Keep
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputDoNotDisturb(val interruptionFilter: Int)

class ActionDoNotDisturb(context: Context, payloadString: String) : Action<InputDoNotDisturb>(context, payloadString) {
    override val payloadClass = InputDoNotDisturb::class.java

    override suspend fun runSpecific(): PluginResult {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return PluginResult(false, "Can only be used on Android 6 or above")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager? ?: return PluginResult(false, "Couldn't get notification manager")
        if (!notificationManager.isNotificationPolicyAccessGranted) return PluginResult(false, "No permission to change DND state.\n\nPlease enable the DND permission for 'Tasker Settings' (not Tasker itself) in Android settings.")

        notificationManager.setInterruptionFilter(input.interruptionFilter)
        return PluginResult(true)
    }

}
