package com.joaomgcd.taskersettings.actions

import android.Manifest
import android.content.Context
import android.net.wifi.WifiManager
import android.support.annotation.Keep
import com.joaomgcd.taskerbackcompat.util.nullIfEmpty
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputConnectToWifi(val ssid: String)

class ActionConnectToWifi(context: Context, payloadString: String) : Action<InputConnectToWifi>(context, payloadString) {
    override val payloadClass = InputConnectToWifi::class.java
    override val neededPermissions get() = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun runSpecific(): PluginResult {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager? ?: return PluginResult(false, "Couldn't get wifi manager")

        val configuredNetworks = wifiManager.configuredNetworks
        val matchingNetwork = configuredNetworks.firstOrNull {
            val ssid = it.SSID.nullIfEmpty ?: return@firstOrNull false

            ssid.replace("\"", "") == input.ssid
        } ?: return PluginResult(false, "Couldn't find matching configured network")

        val result = wifiManager.enableNetwork(matchingNetwork.networkId, true)
        return PluginResult(result,if(result) null else "Couldn't connect to ${input.ssid}")
    }

}
