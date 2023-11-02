package com.joaomgcd.taskersettings.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private val activityFlow = MutableStateFlow(null as ActivitySuspend?)

private var jobsCount: AtomicInteger = AtomicInteger(0)
private val mutex = Mutex()
private const val TAG = "ActivitySuspend"
private fun String.log() = Log.d(TAG, this)
suspend fun <T> Context.getWithActivity(block: suspend ActivitySuspend.() -> T): T {
    suspend fun ActivitySuspend.doBlock(): T {
        val jobCountNew = jobsCount.addAndGet(1)
        "New job. Count: $jobCountNew".log()
        val result = block()
        val jobCountDone = jobsCount.decrementAndGet()
        "Job Count after done: $jobCountDone".log()
        if (jobCountDone == 0) {
            mutex.withLock {
                finish()
                activityFlow.filter { it == null }.first()
            }
        }
        return result
    }
    if (this is ActivitySuspend) return this.doBlock()
    activityFlow.value?.let { return it.doBlock() }

    val activityBackCompat = mutex.withLock {
        startActivity(Intent(this, ActivitySuspend::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        activityFlow.filterNotNull().first()
    }
    return activityBackCompat.doBlock()
}

suspend fun Context.startActivityForResult(intent: Intent): Intent? = getWithActivity { startActivityForResult(intent) }

class ActivitySuspend : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate with ${jobsCount.get()} jobs".log()
        activityFlow.tryEmit(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy with ${jobsCount.get()} jobs".log()
        activityFlow.tryEmit(null)
    }

    private val requestCodes = hashMapOf<Int, Continuation<Intent?>?>()
    suspend fun startActivityForResult(intent: Intent): Intent? {
        return suspendCoroutine {
            val requestCode = intent.hashCode()
            try {
                startActivityForResult(intent, requestCode)
                "startActivityForResult with ${jobsCount.get()} jobs".log()
                requestCodes[requestCode] = it
            } catch (t: Throwable) {
                "startActivityForResult Throwable with ${jobsCount.get()} jobs: ${t.message}".log()
                it.resumeWithException(t)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestCodes[requestCode]?.resume(data)
        requestCodes[requestCode] = null
        "onActivityResult with ${jobsCount.get()} jobs".log()
    }

}