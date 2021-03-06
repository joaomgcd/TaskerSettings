package net.dinglisch.android.tasker

import android.os.Handler
import android.os.Parcelable
import android.os.ResultReceiver
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
class PluginResultReceiver(handler: Handler?) : ResultReceiver(handler)

@Parcelize
class PluginResult(val success: Boolean, val errorMessage: String? = null, var logs: Array<String>? = null, var permissionsNeeded: Array<String>? = null,val newState:Boolean? = null) : Parcelable