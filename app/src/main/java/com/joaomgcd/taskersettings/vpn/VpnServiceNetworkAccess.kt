package com.joaomgcd.taskersettings.vpn

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log
import com.joaomgcd.taskerbackcompat.util.CoroutineScopeIO
import com.joaomgcd.taskersettings.util.startActivityForResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


class VpnServiceNetworkAccess : VpnService() {
    private val coroutineScope = CoroutineScopeIO()

    enum class Mode { AllowAll, Allow, DenyAll, Deny }

    // ---------------------- INSTANCE FUNCS -------------------------- //
    private var loadFlag = true
    fun reload() {
        loadFlag = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        setInstance(this)
        coroutineScope.launch { startSession() }
        return START_STICKY
    }


    override fun onRevoke() {
        stop()
    }

    fun stop() {
        destroyThread()
        stopSelf()
        clearData()
    }

    override fun onDestroy() {
        destroyThread()
        super.onDestroy()
        setInstance(null)
    }

    private fun destroyThread() {
        coroutineScope.cancel()
    }

    private suspend fun startSession() {
        withContext(Dispatchers.IO) {
            var liveInterface: ParcelFileDescriptor? = null
            var oldInterface: ParcelFileDescriptor? = null
            var inputStream: FileInputStream? = null
            try {
                val buffer = ByteArray(32767)
                while (isActive) {
                    // empty any waiting packets to prevent buffer overruns
                    while (inputStream != null && inputStream.read(buffer) > 0) {
                    }
                    if (loadFlag) {
                        loadFlag = false
                        closeInputStream(inputStream)
                        oldInterface = liveInterface
                        liveInterface = makeBuilder(mode, currentPackages).establish()
                        if (liveInterface == null) {
                            Log.w(TAG, "failed to create interface")
                            break
                        } else {
                            inputStream = FileInputStream(liveInterface.fileDescriptor)
                            oldInterface.closeInterface()
                        }
                    }
                    delay(2000)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                oldInterface.closeInterface()
                closeInputStream(inputStream)
                liveInterface.closeInterface()
            }

        }
    }

    private fun ParcelFileDescriptor?.closeInterface() {
        if (this == null) return

        try {
            this.close()
        } catch (e: Exception) {
            Log.e(TAG, "closeInterface", e)
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    private fun makeBuilder(thisMode: Mode?, thisPackages: List<String>?): Builder {
        return Builder().apply {
            setSession(USER_VISIBLE_TAG)
                .addAddress("192.168.0.1", 24)
                .addDnsServer("8.8.8.8")
                .addRoute("0.0.0.0", 0)
                .addRoute("::", 0)
                .addAddress("fde4:8dba:82e1:ffff::1", 64)
            if (thisPackages == null) return@apply

            for (pkg in thisPackages) {
                if (thisMode == Mode.Allow) addDisallowedApplication(pkg) else if (thisMode == Mode.Deny) addAllowedApplication(pkg)
            }
        }
    }

    companion object {
        // don't change
        const val TAG = "MyVpnService"
        const val USER_VISIBLE_TAG = "NetAccessService"
        var currentPackages: List<String>? = null
            private set

        private var mode: Mode? = null
        private var instance: VpnServiceNetworkAccess? = null
        private val instanceLock = Any()
        private val startStopLock = Any()
        val currentMode get() = mode ?: Mode.AllowAll

        fun List<String>?.matchesAnyOrder(b: List<String>?) = this?.toSet() == b?.toSet()

        fun closeInputStream(inputStream: InputStream?) = try {
            inputStream?.close()
            true
        } catch (e: IOException) {
            Log.e(TAG, "failed to close input stream", e)
            false
        }

        // don't run from UI thread
        fun start(c: Context, wantMode: Mode, wantPackages: List<String>?): Boolean {
            synchronized(startStopLock) {
                var okFlag = true
                var wantShutdown = false
                var wantStart = false
                when {
                    wantMode == Mode.AllowAll -> wantShutdown = true
                    wantMode == Mode.DenyAll -> if (Mode.DenyAll != mode) {
                        wantShutdown = true
                        wantStart = true
                    }

                    mode != wantMode || !wantPackages.matchesAnyOrder(currentPackages) -> {
                        wantShutdown = true
                        wantStart = true
                    }
                }
                if (wantShutdown && !wantStart) {
                    shutdown()
                    if (!waitForInstance(false)) {
                        Log.w(TAG, "vpn service still running after shutdown")
                        okFlag = false
                    }
                }
                if (!okFlag) return false

                mode = wantMode
                currentPackages = wantPackages
                if (!wantStart) return true

                Log.d(TAG, "start: have instance: " + haveInstance())
                if (haveInstance()) {
                    instance?.reload()
                    return true
                }
                c.startService(Intent(c, VpnServiceNetworkAccess::class.java))
                if (waitForInstance(true)) return true

                Log.w(TAG, "vpn service failed to start")
                clearData()

                return false
            }
        }

        suspend fun prepareIfNeeded(context: Context) = prepare(context)?.let { context.startActivityForResult(it) }

        private fun haveInstance(): Boolean {
            synchronized(instanceLock) { return instance != null }
        }

        private fun setInstance(toSet: VpnServiceNetworkAccess?) {
            synchronized(instanceLock) { instance = toSet }
        }

        private fun waitForInstance(startFlag: Boolean): Boolean {
            val startTimeMS = System.currentTimeMillis()
            while (System.currentTimeMillis() - startTimeMS < 8000L && startFlag != haveInstance()) {
                Log.d(TAG, "wait for instance start/stop $startFlag")
                try {
                    Thread.sleep(50L)
                } catch (e: InterruptedException) {
                }
            }
            return startFlag == haveInstance()
        }

        fun shutdown() {
            synchronized(startStopLock) {
                synchronized(instanceLock) {
                    instance?.stop()
                }
            }
        }

        private fun clearData() {
            currentPackages = null
            mode = null
        }

    }
}
