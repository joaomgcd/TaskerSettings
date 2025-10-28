package com.joaomgcd.taskersettings

import android.app.Application
import android.content.Context
import android.os.Build
import com.joaomgcd.taskerbackcompat.util.enableDisableComponent
import org.lsposed.hiddenapibypass.HiddenApiBypass

class ApplicationTaskerSettings : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationTaskerSettings.context = this
        enableDisableComponent(MainActivity::class.java, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.setHiddenApiExemptions("L")
        }
    }

    companion object {
        lateinit var context: Context
    }
}