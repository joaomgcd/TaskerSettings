package com.joaomgcd.taskersettings.actions

import android.content.Context
import android.net.wifi.WifiManager
import androidx.annotation.Keep
import net.dinglisch.android.tasker.PluginResult


@Keep
class InputWifiNet(val action: Action) {
    @Keep
    enum class Action { Disconnect, Reassociate, Reconnect }
}

class ActionWifiNet(context: Context, payloadString: String) : Action<InputWifiNet>(context, payloadString) {
    override val payloadClass = InputWifiNet::class.java

    override suspend fun runSpecific(): PluginResult {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
            ?: return PluginResult(false, "Couldn't get wifi manager")
        val result = when (input.action) {
            InputWifiNet.Action.Disconnect -> wifiManager.disconnect()
            InputWifiNet.Action.Reassociate -> wifiManager.reassociate()
            InputWifiNet.Action.Reconnect -> wifiManager.reconnect()
        }
        return PluginResult(result)
    }

}
