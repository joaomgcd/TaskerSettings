package com.joaomgcd.taskersettings

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.util.Log
import com.joaomgcd.taskerbackcompat.util.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * A drop-in replacement for IntentService that performs various tasks at the same time instead of one after the other
 *
 */
private const val TAG = "ServiceBackCompat"

abstract class IntentServiceParallel : Service() {
    private fun String.log() = Log.d(TAG, this)
    private suspend fun doInMain(block: suspend CoroutineScope.() -> Unit) {
        withContext(Dispatchers.Main, block)
    }

    protected val coroutineScope = CoroutineScopeIO()

    /**
     * Simply call [onStart] like [IntentService] does
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onStart(intent, startId)
        return START_NOT_STICKY
    }

    protected abstract suspend fun onHandleIntent(intent: Intent)


    /**
     * Keep count of how many tasks are active so that we can stop when they reach 0
     */
    private var jobsCount: AtomicInteger = AtomicInteger(0)


    /**
     * Keep track of the last startId sent to the service. Will be used to make sure we only stop the service if the last startId was actually the last startId that the service received.
     */
    private var lastStartId: Int? = null

    /**
     * Main function of the class. Starts processing each new task in parallel with existing tasks. When all tasks are processed will stop itself. Will ignore null intents.
     */
    override fun onStart(intent: Intent?, startId: Int) {
        if (intent == null) return

        //Count +1 so that we know how many tasks are running
        val jobCountNew = jobsCount.addAndGet(1)
        "New job. Count: $jobCountNew".log()

        /**
         * store the startId so that we will always use [stopSelf] with the correct Id.
         * This is stored after incrementing the count so that if [stopSelf] runs before
         * the increment the service is not stopped because the last startId is not used as a parameter
         */
        lastStartId = startId
        coroutineScope.launch {
            try {
                //run task in parallel
                onHandleIntent(intent)
            } catch (throwable: RuntimeException) {
                //throw any exception in the main thread
                doInMain { throw throwable }
            } finally {
                doInMain {
                    //decrement in the main thread to avoid concurrency issues
                    val jobCountDone = jobsCount.decrementAndGet()

                    "Job done. Count: $jobCountDone".log()
                    if (jobCountDone > 0) return@doInMain

                    //stop only if lastStartId was the last startId that was posted
                    lastStartId.let {
                        "stopping self with startId $it".log()
                        if (it != null) stopSelf(it) else stopSelf()
                    }
                }
            }
        }

    }


    /**
     * Shutdown the executor when the sevice is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}