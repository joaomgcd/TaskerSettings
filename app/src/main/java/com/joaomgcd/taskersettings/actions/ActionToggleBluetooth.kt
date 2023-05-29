package com.joaomgcd.taskersettings.actions

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import androidx.annotation.Keep
import com.joaomgcd.taskerm.windowmanager.WindowManagerGlobal
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputToggleBluetooth(val value: Boolean)

class ActionToggleBluetooth(context: Context, payloadString: String) : Action<InputToggleBluetooth>(context, payloadString) {
    override val payloadClass = InputToggleBluetooth::class.java
//    override val neededPermissions get() = arrayOf(Manifest.permission.WRITE_SECURE_SETTINGS)

    private val bluetoothAdapter by lazy {
        val manager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        manager.adapter
    }

    override fun runSpecific(): PluginResult {
        val couldToggle = if (input.value) bluetoothAdapter.enable() else bluetoothAdapter.disable()
        return PluginResult(couldToggle)
    }

}
