package com.joaomgcd.taskerbackcompat.util

//import io.reactivex.Completable
//import io.reactivex.Single
//import io.reactivex.schedulers.Schedulers
//import io.reactivex.subjects.SingleSubject
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.google.gson.Gson
import com.joaomgcd.taskersettings.admin.MyDeviceAdminReceiver
import java.io.PrintWriter
import java.io.StringWriter

val Throwable?.allCauses: List<Throwable>
    get() {
        val result = ArrayList<Throwable>()
        var cause = this
        while (cause != null) {
            result.add(cause)
            val childCause = cause.cause
            if (childCause == cause) break

            cause = childCause
        }
        return result
    }
val Throwable.stackTraceString
    get():String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        printStackTrace(pw)
        return sw.toString()
    }

val gson by lazy { Gson() }
val Any.json get() = gson.toJson(this)
inline fun <reified T> String.fromJson() = gson.fromJson<T>(this, T::class.java)
fun <T> String.fromJson(clzz: Class<T>) = gson.fromJson<T>(this, clzz)
val String.withUppercaseFirstLetter
    get() :String {
        return substring(0, 1).uppercase() + substring(1)
    }

val Context.devicePolicyManager get() = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
val Context.deviceAdminReceiverComponent get() = ComponentName(packageName, MyDeviceAdminReceiver::class.java.name)

fun Context.isAppInstalled(packageName: String): Boolean {
    val pm = packageManager
    return try {
        pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    } catch (e: Throwable) {
        false
    }

}

 fun Context.enableDisableComponent(clss: Class<*>, enable: Boolean)  {
    val context = this@enableDisableComponent
    val state = if (enable) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
    packageManager.setComponentEnabledSetting(ComponentName(context, clss), state, PackageManager.DONT_KILL_APP)
}

fun Boolean?.isNullOrFalse() = this == null || this == false

val String?.nullIfEmpty get() = if (this.isNullOrEmpty()) null else this
