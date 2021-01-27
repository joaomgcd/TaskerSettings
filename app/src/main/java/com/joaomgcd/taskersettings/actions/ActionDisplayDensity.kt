package com.joaomgcd.taskersettings.actions

import android.Manifest
import android.content.Context
import android.support.annotation.Keep
import com.joaomgcd.taskerm.windowmanager.WindowManagerGlobal
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputDisplayDensity(val value: Int)

class ActionDisplayDensity(context: Context, payloadString: String) : Action<InputDisplayDensity>(context, payloadString) {
    override val payloadClass = InputDisplayDensity::class.java
    override val neededPermissions get() = arrayOf(Manifest.permission.WRITE_SECURE_SETTINGS)

    override fun runSpecific(): PluginResult {
        val windowManagerService = WindowManagerGlobal.windowManagerService
        windowManagerService.setDisplayDensity(input.value)
        return PluginResult(true)
    }

}
