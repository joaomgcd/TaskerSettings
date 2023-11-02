package com.joaomgcd.taskerbackcompat.util

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.google.gson.Gson
import com.joaomgcd.taskersettings.admin.MyDeviceAdminReceiver
//import io.reactivex.Completable
//import io.reactivex.Single
//import io.reactivex.schedulers.Schedulers
//import io.reactivex.subjects.SingleSubject
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
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
        return substring(0, 1).toUpperCase() + substring(1)
    }

val Context.devicePolicyManager get() = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
val Context.deviceAdminReceiverComponent get() = ComponentName(packageName, MyDeviceAdminReceiver::class.java.name)

fun Boolean?.isNullOrFalse() = this == null || this == false

val String?.nullIfEmpty get() = if (this.isNullOrEmpty()) null else this
