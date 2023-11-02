package com.joaomgcd.taskersettings.actions

import android.content.Context
import androidx.annotation.Keep
import com.joaomgcd.taskerbackcompat.util.json
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import net.dinglisch.android.tasker.PluginResult
import java.io.BufferedReader
import java.io.InputStream

@Keep
class InputRunShell(val command: String, val timeoutMilliseconds: Long, val useRoot: Boolean, val useGlobalNamespace: Boolean)

@Keep
class OutputRunShell(val output: String?)
class ActionRunShell(context: Context, payloadString: String) : Action<InputRunShell>(context, payloadString) {
    override val payloadClass = InputRunShell::class.java
    val InputStream.lines get() = this.bufferedReader().use(BufferedReader::readLines)
    private suspend fun InputStream.linesInBackgroundThread(): Deferred<List<String>> = withContext(Dispatchers.IO) {
        async {
            withTimeout(input.timeoutMilliseconds) {
                lines
            }
        }
    }


    override suspend fun runSpecific(): PluginResult {
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
            withTimeout(input.timeoutMilliseconds) {

            }
            fun List<String>.fromLines() = joinToString("\n").trim()
            val result = withContext(Dispatchers.IO) {
                val outputInputStreamAsync = process.inputStream.linesInBackgroundThread()
                val outputErrorStreamAsync = process.errorStream.linesInBackgroundThread()
                val outputInputStream = outputInputStreamAsync.await()
                val outputErrorStream = outputErrorStreamAsync.await()
                PluginResult(outputErrorStream.isEmpty(), errorMessage = outputErrorStream.fromLines(), payloadJson = OutputRunShell(outputInputStream.fromLines()).json)
            }
            return result
        } catch (t: Throwable) {
            process?.destroy()
            throw t
        }
    }

}