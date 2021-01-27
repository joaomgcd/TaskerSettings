//package com.joaomgcd.taskersettings.actions
//
//import android.content.Context
//import android.os.Bundle
//import android.os.ResultReceiver
//import android.support.annotation.Keep
//import com.joaomgcd.taskerbackcompat.util.handlerMain
//import io.reactivex.Single
//import net.dinglisch.android.tasker.PluginResult
//import java.lang.reflect.Method
//
//@Keep
//class InputWifiTether(val enable: Boolean, val actionName: String)
//
//class ActionWifiTether(context: Context, payloadString: String) : Action<InputWifiTether>(context, payloadString) {
//    override val payloadClass = InputWifiTether::class.java
//
//    override fun runSpecific(): PluginResult {
//        if (input.enable) {
//            return enable().blockingGet()
//        }
//        return disable()
//    }
//
//    private val connectivityService: Any by lazy {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
//            ?: throw RuntimeException("${input.actionName}: couldn't get tether status")
//
//
//        val contextField = try {
//            connectivityManager::class.java.getDeclaredField("mService")
//        } catch (ex: NoSuchFieldException) {
//            connectivityManager::class.java.getDeclaredField("mServiceEx")
//        }
//        contextField.apply {
//            isAccessible = true
//        }.run { get(connectivityManager) }
//    }
//
//    private val iConnectivityManager: Class<*> by lazy { Class.forName("android.net.IConnectivityManager") }
//
//    private fun disable(): PluginResult {
//        debugger("Disabling wifi tether")
//        var addPackageName = false
//        val methodName = "stopTethering"
//        val method: Method? = try {
//            iConnectivityManager.getDeclaredMethod(methodName, Int::class.javaPrimitiveType, String::class.java).also {
//                addPackageName = true
//            }
//        } catch (t: Throwable) {
//            try {
//                iConnectivityManager.getDeclaredMethod(methodName, Int::class.javaPrimitiveType)
//            } catch (t: Throwable) {
//                null
//            }
//        }
//        if (method == null) return PluginResult(false, "${input.actionName}: couldn't disable wifi tether")
//
//        if (addPackageName) {
//            method.invoke(connectivityService, 0, context.packageName)
//        } else {
//            method.invoke(connectivityService, 0)
//        }
//        return PluginResult(true)
//    }
//
//    private fun enable(): Single<PluginResult> {
//
//        return Single.create {
//            debugger("Enabling wifi tether")
//            var addPackageName = false
//            val methodName = "startTethering"
//            val method: Method? = try {
//                iConnectivityManager.getDeclaredMethod(
//                    methodName,
//                    Int::class.javaPrimitiveType,
//                    ResultReceiver::class.java,
//                    Boolean::class.javaPrimitiveType,
//                    String::class.java
//                ).also {
//                    addPackageName = true
//                }
//            } catch (t: Throwable) {
//                try {
//                    iConnectivityManager.getDeclaredMethod(
//                        methodName,
//                        Int::class.javaPrimitiveType,
//                        ResultReceiver::class.java,
//                        Boolean::class.javaPrimitiveType
//                    )
//                } catch (t: Throwable) {
//                    null
//                }
//            }
//            if (method == null) return@create it.onSuccess(
//                PluginResult(
//                    false,
//                    "${input.actionName}: couldn't enable wifi tether"
//                )
//            )
//
//            debugger("Got method with package $addPackageName")
//            val resultReceiver = object : ResultReceiver(handlerMain) {
//                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
//                    super.onReceiveResult(resultCode, resultData)
//
//                    debugger("Received result: $resultCode")
//                    it.onSuccess(
//                        if (resultCode == 0) PluginResult(true) else PluginResult(
//                            false,
//                            "${input.actionName}: couldn't enable wifi tether: \"${resultCode.tetherMessage}\""
//                        )
//                    )
//                }
//            }
//
//            if (addPackageName) {
//                method.invoke(connectivityService, 0, resultReceiver, true, context.packageName)
//                debugger("Called method with package")
//            } else {
//                method.invoke(connectivityService, 0, resultReceiver, true)
//                debugger("Called method without package")
//            }
//        }
//
//
//    }
//}
//
//private val Int.tetherMessage
//    get() = when (this) {
//        TETHER_ERROR_NO_ERROR -> "OK"
//        TETHER_ERROR_UNKNOWN_IFACE -> "Unkonwn Iface"
//        TETHER_ERROR_SERVICE_UNAVAIL -> "Service Unavailable"
//        TETHER_ERROR_UNSUPPORTED -> "Unsupported"
//        TETHER_ERROR_UNAVAIL_IFACE -> "Unavailable Iface"
//        TETHER_ERROR_MASTER_ERROR -> "Master Error"
//        TETHER_ERROR_TETHER_IFACE_ERROR -> "Tether Iface Error"
//        TETHER_ERROR_UNTETHER_IFACE_ERROR -> "Untether Iface Rrror"
//        TETHER_ERROR_ENABLE_NAT_ERROR -> "Enable NAT Error"
//        TETHER_ERROR_DISABLE_NAT_ERROR -> "Disable NAT Error"
//        TETHER_ERROR_IFACE_CFG_ERROR -> "Iface Configuration Error"
//        TETHER_ERROR_PROVISION_FAILED -> "Provision Failed"
//        else -> "Unkown error"
//    }
//private const val TETHER_ERROR_NO_ERROR = 0
//private const val TETHER_ERROR_UNKNOWN_IFACE = 1
//private const val TETHER_ERROR_SERVICE_UNAVAIL = 2
//private const val TETHER_ERROR_UNSUPPORTED = 3
//private const val TETHER_ERROR_UNAVAIL_IFACE = 4
//private const val TETHER_ERROR_MASTER_ERROR = 5
//private const val TETHER_ERROR_TETHER_IFACE_ERROR = 6
//private const val TETHER_ERROR_UNTETHER_IFACE_ERROR = 7
//private const val TETHER_ERROR_ENABLE_NAT_ERROR = 8
//private const val TETHER_ERROR_DISABLE_NAT_ERROR = 9
//private const val TETHER_ERROR_IFACE_CFG_ERROR = 10
//private const val TETHER_ERROR_PROVISION_FAILED = 11