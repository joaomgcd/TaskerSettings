package com.joaomgcd.taskerbackcompat.util

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.joaomgcd.taskersettings.admin.MyDeviceAdminReceiver
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.PrintWriter
import java.io.StringWriter

val handlerMain = Handler(Looper.getMainLooper())
val backgroundThread = Schedulers.io()
fun <T> Single<T>.observeInBackground() = this.observeOn(backgroundThread)
fun <T> Single<T>.subscribeInBackground() = this.subscribeOn(backgroundThread)

fun <T> getResultInBackground(runnable: () -> T): Single<T> = Single.fromCallable(runnable).observeInBackground().subscribeInBackground()

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
fun <T> String.fromJson(clzz:Class<T>) = gson.fromJson<T>(this, clzz)
val String.withUppercaseFirstLetter
    get() :String {
        return substring(0, 1).toUpperCase() + substring(1)
    }

val Context.devicePolicyManager get() = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
val Context.deviceAdminReceiverComponent get() = ComponentName(packageName, MyDeviceAdminReceiver::class.java.name)

fun Boolean?.isNullOrFalse() = this == null || this == false