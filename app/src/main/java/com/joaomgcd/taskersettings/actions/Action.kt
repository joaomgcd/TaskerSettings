package com.joaomgcd.taskersettings.actions

//import com.joaomgcd.taskerbackcompat.util.getResultInBackground
//import io.reactivex.Single
import android.content.Context
import android.content.pm.PackageManager
import com.joaomgcd.taskerbackcompat.util.fromJson
import net.dinglisch.android.tasker.PluginResult


abstract class Action<TRunPayload>(val context: Context, val payloadString: String) {
    fun debugger(log: String) = logs.add(log)
    val logs by lazy { arrayListOf<String>() }
    val input: TRunPayload by lazy { payloadString.fromJson(payloadClass) }
    suspend fun run(): PluginResult {
        val notGrantedPermissions = notGrantedPermissions
        if (notGrantedPermissions.isNotEmpty()) return PluginResult(false, permissionsNeeded = notGrantedPermissions)

        val result = runSpecific()
        result.logs = logs.toTypedArray()
        return result

    }

    protected abstract val payloadClass: Class<TRunPayload>
    protected abstract suspend fun runSpecific(): PluginResult

    open val neededPermissions: Array<String> get() = arrayOf()
    private val notGrantedPermissions get() = neededPermissions.filter { context.checkCallingOrSelfPermission(it) != PackageManager.PERMISSION_GRANTED }.toTypedArray()

    companion object {
        fun getActionFromType(context: Context, type: String, input: String): Action<*> {
            val clzz = Class.forName("com.joaomgcd.taskersettings.actions.Action$type")
            val constructor = clzz.getConstructor(Context::class.java, String::class.java)
            return constructor.newInstance(context, input) as Action<*>
        }
    }
}