package com.joaomgcd.taskersettings.actions

import android.content.Context
import androidx.annotation.Keep
import com.joaomgcd.taskerbackcompat.util.*
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.SingleSubject
import net.dinglisch.android.tasker.PluginResult
import java.io.*
import java.util.concurrent.TimeUnit

@Keep
class InputRunShell(val command: String, val timeoutMilliseconds: Long, val useRoot: Boolean, val useGlobalNamespace: Boolean)

@Keep
class OutputRunShell(val output: String?)
class ActionRunShell(context: Context, payloadString: String) : Action<InputRunShell>(context, payloadString) {
    override val payloadClass = InputRunShell::class.java

    private val <T> Single<T>.withTimeout get() = this.timeout(input.timeoutMilliseconds, TimeUnit.MILLISECONDS)
    override fun runSpecific(): PluginResult {
        val pluginResultSubject = SingleSubject.create<PluginResult>()
        var process: Process? = null
        try {
            val finalCommand = if (input.useRoot) {
                "su ${if (input.useGlobalNamespace) " --mount-master" else ""}"
            } else {
                "sh"
            }
            process = Runtime.getRuntime().exec(finalCommand)
            process.outputStream.bufferedWriter().apply {
                write("${input.command}\n")
                flush()
                write("exit\n")
                flush()
                close()
            }
            fun List<String>.fromLines() = joinToString("\n").trim()
            val outputStreams = process.inputStream.linesInBackgroundThread.withTimeout + process.errorStream.linesInBackgroundThread.withTimeout
            val (output, errorMessage) = outputStreams.blockingGet()
            pluginResultSubject.onSuccess(PluginResult(errorMessage.isEmpty(), errorMessage = errorMessage.fromLines(), payloadJson = OutputRunShell(output.fromLines()).json))
        } catch (t: Throwable) {
            process?.destroy()
            pluginResultSubject.onErrorIfHasObservers(t)
        }

        return pluginResultSubject.withTimeout.blockingGet()
    }

}