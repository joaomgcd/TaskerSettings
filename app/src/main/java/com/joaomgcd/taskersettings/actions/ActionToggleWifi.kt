package com.joaomgcd.taskersettings.actions

import android.content.Context
import android.net.wifi.WifiManager
import androidx.annotation.Keep
import com.joaomgcd.taskerm.windowmanager.WindowManagerGlobal
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputToggleWifi(val value: Boolean)

class ActionToggleWifi(context: Context, payloadString: String) : Action<InputToggleWifi>(context, payloadString) {
    override val payloadClass = InputToggleWifi::class.java
//    override val neededPermissions get() = arrayOf(Manifest.permission.WRITE_SECURE_SETTINGS)

    override suspend fun runSpecific(): PluginResult {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager? ?: return PluginResult(false,"Couldn't get wifi manager")

        wifiManager.isWifiEnabled = input.value
        return PluginResult(true)
    }

}
