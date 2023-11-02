package com.joaomgcd.taskersettings.actions

import android.content.Context
import androidx.annotation.Keep
import com.joaomgcd.taskerbackcompat.util.json
import com.joaomgcd.taskersettings.vpn.VpnServiceNetworkAccess
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputNetworkAccess(val packageNames: List<String>, val mode: VpnServiceNetworkAccess.Mode, val prepareOnly: Boolean, val shutdownOnly: Boolean)

@Keep
class OutputNetworkAccess(val resultMode: VpnServiceNetworkAccess.Mode?, val currentPackages: Array<String>?)

class ActionNetworkAccess(context: Context, payloadString: String) : Action<InputNetworkAccess>(context, payloadString) {
    override val payloadClass = InputNetworkAccess::class.java

    override suspend fun runSpecific(): PluginResult {
        if (input.shutdownOnly) {
            VpnServiceNetworkAccess.shutdown()
            return PluginResult(true)
        }
        VpnServiceNetworkAccess.prepareIfNeeded(context)
        if (input.prepareOnly) return PluginResult(true)

        val result = VpnServiceNetworkAccess.start(context, input.mode, input.packageNames)
        return PluginResult(result, if (!result) "Couldn't manage network access" else null, payloadJson = OutputNetworkAccess(VpnServiceNetworkAccess.currentMode, VpnServiceNetworkAccess.currentPackages?.toTypedArray()).json)
    }

}

