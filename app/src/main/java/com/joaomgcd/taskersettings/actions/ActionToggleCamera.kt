package com.joaomgcd.taskersettings.actions

import android.content.Context
import android.net.wifi.WifiManager
import androidx.annotation.Keep
import com.joaomgcd.taskerbackcompat.util.deviceAdminReceiverComponent
import com.joaomgcd.taskerbackcompat.util.devicePolicyManager
import com.joaomgcd.taskerbackcompat.util.isNullOrFalse
import com.joaomgcd.taskerm.windowmanager.WindowManagerGlobal
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputToggleBackCompat(val toggle: Boolean,val enabled:Boolean)

class ActionToggleCamera(context: Context, payloadString: String) : Action<InputToggleBackCompat>(context, payloadString) {
    override val payloadClass = InputToggleBackCompat::class.java
    override suspend fun runSpecific(): PluginResult {
        val manager = context.devicePolicyManager ?: return PluginResult(false,"Couldn't get policy manager")

        val state = if(input.toggle) isOn else !input.enabled
        try {
            manager.setCameraDisabled(context.deviceAdminReceiverComponent, state)
            return PluginResult(true, newState = !state)
        }catch (t:Throwable){
            return PluginResult(false,"Couldn't change setting. Make sure the Tasker Settings app (not Tasker itself) is a device administator.")
        }
    }
    val isOn: Boolean
        get() = context.devicePolicyManager?.getCameraDisabled(context.deviceAdminReceiverComponent).isNullOrFalse()

}
