package net.dinglisch.android.tasker

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.os.ResultReceiver
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
class PluginResultReceiver(handler: Handler, val receiver: (Int, Bundle?) -> Unit) : ResultReceiver(handler)

@Parcelize
class PluginResult constructor(val success: Boolean, val errorMessage: String? = null, var logs: Array<String>? = null, var permissionsNeeded: Array<String>? = null, val newState: Boolean? = null, val payloadJson: String? = null) : Parcelable