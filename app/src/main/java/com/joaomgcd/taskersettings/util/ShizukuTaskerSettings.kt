package com.joaomgcd.taskersettings.util

import android.content.pm.PackageManager
import android.util.Log
import com.joaomgcd.taskerbackcompat.util.isAppInstalled
import com.joaomgcd.taskerbackcompat.util.tryGetOrNull
import com.joaomgcd.taskersettings.ApplicationTaskerSettings.Companion.context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper

private const val TAG = "SHZKUSTaskerSettings"
private fun String.log() = Log.d(TAG, this)
class ShizukuTaskerSettings {
    companion object {
        class Availability(val available: Boolean, val running: Boolean, val hasPermissions: Boolean, val installed: Boolean)

        private val stateAvailability = MutableStateFlow<Availability?>(null)
        val flowAvailability = stateAvailability.asStateFlow()
        private val currentAvailability
            get() = Availability(
                available = isAvailable,
                running = isRunning,
                hasPermissions = hasPermission,
                installed = isInstalled
            )

        private fun updateStateAvailability() {
            stateAvailability.tryEmit(currentAvailability)
        }

        val packageName get() = "moe.shizuku.privileged.api"

        @JvmStatic
        val isInstalled = context.isAppInstalled(packageName)

        @get:JvmStatic
        val isRunning get() = Shizuku.getBinder() != null
        val hasPermission get():Boolean = tryGetOrNull { Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED } ?: false

        @get:JvmStatic
        val isAvailable get() = isRunning && hasPermission
        const val ADB_PACKAGE_NAME = "com.android.shell"
        fun getShizukuService(name: String) =
            SystemServiceHelper.getSystemService(name)?.let {
                ShizukuBinderWrapper(it)
            } ?: throw RuntimeException("Unable to get service: $name")
    }

    init {
        Shizuku.addBinderReceivedListenerSticky {
            "Binder received".log()
            updateStateAvailability()
        }
        Shizuku.addBinderDeadListener {
            "Binder dead".log()
            updateStateAvailability()
        }
        Shizuku.addRequestPermissionResultListener { _, grantResult ->
            "Permissions Received: $grantResult".log()
            updateStateAvailability()
        }
    }
}