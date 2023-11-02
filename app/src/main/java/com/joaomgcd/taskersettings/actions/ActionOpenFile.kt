package com.joaomgcd.taskersettings.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.Keep
import androidx.core.content.FileProvider
import com.joaomgcd.taskersettings.BuildConfig
import net.dinglisch.android.tasker.PluginResult
import java.io.File


@Keep
class InputOpenFile(val path: String, val mimeType: String?)

@Keep
class OutputOpenFile()

class ActionOpenFile(context: Context, payloadString: String) : Action<InputOpenFile>(context, payloadString) {
    override val payloadClass = InputOpenFile::class.java

    override suspend fun runSpecific(): PluginResult {
        return try {
            val uri: Uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", File(input.path))
            val intent = Intent(Intent.ACTION_VIEW).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_FROM_BACKGROUND)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                setDataAndType(uri, input.mimeType)
            }
            context.startActivity(intent)
            PluginResult(true)
        } catch (t: Throwable) {
            PluginResult(false, t.message)
        }
    }


}