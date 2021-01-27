package com.joaomgcd.taskerm.windowmanager

import android.os.UserHandle
import com.joaomgcd.taskersettings.util.call
import com.joaomgcd.taskersettings.util.run
import com.joaomgcd.taskersettings.util.staticFunction

class ExceptionWindowManager(message: String) : RuntimeException(message)
class WindowManagerService internal constructor(val iWindowManager: Any) {
    enum class DisplayScalingMode(val modeNumber: Int) { Auto(0), Disable(0) }

    private fun String.call(methodDesc: String) = iWindowManager.call(this, methodDesc)
    private fun String.run(methodDesc: String) = iWindowManager.run(this, methodDesc)
    private val displayId by lazy { 0 }
    private val userId: Int by lazy { UserHandle::class.java.declaredMethods.first { it.name == "myUserId" }.invoke(null) as Int }
    private val setForcedDisplayDensityForUserMethod by lazy { "setForcedDisplayDensityForUser".run("density") }
    private val getInitialDisplayDensityMethod by lazy { "getInitialDisplayDensity".call("get initial density") }
    private val getBaseDisplayDensityMethod by lazy { "getBaseDisplayDensity".call("get current density") }
    private val setForcedDisplayScalingModeMethod by lazy { "setForcedDisplayScalingMode".run("scaling mode") }
    private val setForcedDisplaySizeMethod by lazy { "setForcedDisplaySize".run("display size") }
    private val setOverscanMethod by lazy { "setOverscan".run("overscan") }

    fun resetDisplayDensity() = setDisplayDensity(getInitialDisplayDensity())
    fun setDisplayDensity(density: Int) = setForcedDisplayDensityForUserMethod(0, density, userId)

    fun getInitialDisplayDensity() = getInitialDisplayDensityMethod<Int, Int>(displayId)

    fun getCurrentDisplayDensity() = getBaseDisplayDensityMethod<Int, Int>(displayId)

    fun setForcedDisplayScalingMode(mode: DisplayScalingMode) = setForcedDisplayScalingModeMethod.invoke(displayId, mode.modeNumber)

    fun setForcedDisplaySize(width: Int, height: Int) = setForcedDisplaySizeMethod.invoke(displayId, width, height)

    fun setOverscan(left: Int, top: Int, right: Int, bottom: Int) = setOverscanMethod.invoke(displayId, left, top, right, bottom)
}

class WindowManagerGlobal {

    companion object {

        private val windowManagerGlobalClass: Class<*>? by lazy { Class.forName("android.view.WindowManagerGlobal") }
        val windowManagerService: WindowManagerService by lazy {
            val method = windowManagerGlobalClass?.staticFunction("getWindowManagerService", "window manager")
                    ?: throw ExceptionWindowManager("Window Manager Service doesn't exist")
            val iWindowManager = method<Any>()
            WindowManagerService(iWindowManager)
        }

    }
}