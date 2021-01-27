package com.joaomgcd.taskersettings

import android.Manifest.permission.ANSWER_PHONE_CALLS
import android.annotation.TargetApi
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

//import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity
//import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard

private const val PERMISSION_WRITE_SETTINGS = "android.settings.action.MANAGE_WRITE_SETTINGS"
private const val PACKAGE_TASKER = "net.dinglisch.android.taskerm"

class MainActivity : /*FancyWalkthroughActivity()*/Activity() {
//    private fun hasPermissions(): Boolean {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
//        return Settings.System.canWrite(this)
//    }

    //    @TargetApi(Build.VERSION_CODES.M)
//    override fun onFinishButtonPressed() {
//        startActivity(if (hasPermissions()) {
//            if (isTaskerInstalled()) {
//                packageManager.getLaunchIntentForPackage(PACKAGE_TASKER)
//            } else {
//                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$PACKAGE_TASKER"))
//            }
//        } else {
//            Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply { data = Uri.parse("package:$packageName") }
//        })
//        disableActivityMain()
//        finish()
//    }
//
    private fun disableActivityMain() = packageManager.setComponentEnabledSetting(ComponentName(this, MainActivity::class.java), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableActivityMain()

        Toast.makeText(this, "App successfully installed. Use Tasker with it.", Toast.LENGTH_LONG).show();
//        val cardIntro = FancyWalkthroughCard(
//                "Tasker Settings",
//                "This is just a helper app for Tasker. It allows you to change some settings that Tasker can't change by itself. This app won't do anything by itself.",
//                R.mipmap.ic_launcher).setup()
//        val cardTasker = FancyWalkthroughCard("Tasker Settings",
//                "Click below to start!",
//                R.mipmap.ic_launcher).setup()
//        setOnboardPages(arrayListOf(cardIntro, cardTasker));
//        showNavigationControls(true);
//        setColorBackground(R.color.colorAccent);
    }

//    override fun onResume() {
//        super.onResume()
//        val title =
//                if (hasPermissions()) {
//                    if (isTaskerInstalled()) {
//                        "Open Tasker"
//                    } else {
//                        "Install Tasker"
//                    }
//                } else {
//                    "Set Permissions"
//                }
//        setFinishButtonTitle(title)
//    }

    private fun isTaskerInstalled() = isAppInstalled(this, PACKAGE_TASKER)
}

fun isAppInstalled(context: Context, packageName: String): Boolean {

    val pm = context.packageManager
    return try {
        pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    } catch (e: Throwable) {
        false
    }

}

