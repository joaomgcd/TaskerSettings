package com.joaomgcd.taskersettings.admin

import android.app.admin.DeviceAdminReceiver
import android.content.ComponentName
import android.content.Context

class MyDeviceAdminReceiver : DeviceAdminReceiver() {
    companion object {
        @JvmStatic
        fun getMyComponentName(c: Context): ComponentName? {
            return ComponentName(c.packageName, MyDeviceAdminReceiver::class.java.name)
        }
    }
}