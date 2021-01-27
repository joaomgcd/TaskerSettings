package com.joaomgcd.taskersettings.actions

import android.Manifest
import android.content.Context
import android.support.annotation.Keep
import android.telecom.TelecomManager
import net.dinglisch.android.tasker.PluginResult

//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!
//HHAHAHSDHASHDHASHDASH LOOK HERE!!!

//DOESN'T WORK BECAUSE NEEDS TO TARGET API 26 OR SOMETHING
@Keep
class InputEndCall()

class ActionEndCall(context: Context, payloadString: String) : Action<InputEndCall>(context, payloadString) {
    override val payloadClass = InputEndCall::class.java
//    override val neededPermissions get() = arrayOf(Manifest.permission.ANSWER_PHONE_CALLS)

    override fun runSpecific(): PluginResult {
        val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager?
                ?: return PluginResult(false, "Couldn't get telecom manager")
        val result = telecomManager.endCall()
        return PluginResult(result)
    }

}
