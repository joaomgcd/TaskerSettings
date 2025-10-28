package com.joaomgcd.taskersettings.actions

import android.content.Context
import android.net.IIntResultListener
import android.net.ITetheringConnector
import android.net.TetheringManagerHidden
import android.net.TetheringManagerHidden.TETHERING_WIFI
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import androidx.annotation.Keep
import com.joaomgcd.taskersettings.util.ShizukuTaskerSettings
import com.joaomgcd.taskersettings.util.ShizukuTaskerSettings.Companion.ADB_PACKAGE_NAME
import kotlinx.coroutines.CompletableDeferred
import net.dinglisch.android.tasker.PluginResult

@Keep
class InputToggleWifiTether(val value: Boolean)

class ActionToggleWifiTether(context: Context, payloadString: String) : Action<InputToggleWifiTether>(context, payloadString) {
    override val payloadClass = InputToggleWifiTether::class.java


    override suspend fun runSpecific(): PluginResult {
        if (!ShizukuTaskerSettings.isRunning) return PluginResult(false, "Shizuku is not running. Please enable Shizuku before using this action.")
        if (!ShizukuTaskerSettings.hasPermission) return PluginResult(false, "Tasker Settings doesn't have permission in Shizuku. Please enable it there.")

        try {
            val resultCode = if (input.value) {
                startTether()
            } else {
                stopTether()
            }
            return if (resultCode == 0) {
                PluginResult(true)
            } else {
                PluginResult(false, "Tethering operation failed with code: $resultCode")
            }
        } catch (t: Throwable) {
            return PluginResult(false, t.message)
        }
    }

    private class CompletableIntResultListener : IIntResultListener.Stub() {
        private val completable = CompletableDeferred<Int>()
        override fun onResult(resultCode: Int) {
            completable.complete(resultCode)
        }

        suspend fun await(): Int {
            return completable.await()
        }

    }

    private fun getShizukuTetherService() = ITetheringConnector.Stub.asInterface(ShizukuTaskerSettings.getShizukuService("tethering"))
    private suspend fun startTether(): Int {
        val tetheringConnector = getShizukuTetherService()
        val resultReceiver = CompletableIntResultListener()
        val request = TetheringManagerHidden.TetheringRequest.Builder(TETHERING_WIFI).build()
        try {
            tetheringConnector.startTethering(
                request.parcel,
                ADB_PACKAGE_NAME,
                null,
                resultReceiver,
            )
        } catch (_: NoSuchMethodException) {
            tetheringConnector.startTethering(
                request.parcel,
                ADB_PACKAGE_NAME,
                resultReceiver,
            )
        } catch (_: NoSuchMethodException) {
            tetheringConnector.startTethering(
                TETHERING_WIFI,
                ResultReceiver(Handler(Looper.getMainLooper())),
                false,
                ADB_PACKAGE_NAME,
            )
        } catch (_: NoSuchMethodException) {
            tetheringConnector.startTethering(
                TETHERING_WIFI,
                ResultReceiver(Handler(Looper.getMainLooper())),
                false,
            )
        } catch (_: NoSuchMethodException) {
            throw RuntimeException("No method available to start Wifi Tether")
        }
        return resultReceiver.await()
    }

    private suspend fun stopTether(): Int {
        val tetheringConnector = getShizukuTetherService()
        val resultReceiver = CompletableIntResultListener()
        try {
            tetheringConnector.stopTethering(
                TETHERING_WIFI,
                ADB_PACKAGE_NAME,
                null,
                resultReceiver,
            )
        } catch (_: NoSuchMethodException) {
            tetheringConnector.stopTethering(
                TETHERING_WIFI,
                ADB_PACKAGE_NAME,
                resultReceiver,
            )
        } catch (_: NoSuchMethodException) {
            tetheringConnector.stopTethering(TETHERING_WIFI)
        } catch (_: NoSuchMethodException) {
            throw RuntimeException("No method available to stop Wifi Tether")
        }

        return resultReceiver.await()
    }
}
