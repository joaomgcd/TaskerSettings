package com.joaomgcd.taskersettings

import android.content.Intent
import android.provider.Settings
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.joaomgcd.taskersettings.util.ActivitySuspend
import com.joaomgcd.taskersettings.util.getWithActivity
import com.joaomgcd.taskersettings.util.startActivityForResult
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.seconds


@RunWith(AndroidJUnit4::class)
class TestActivitySuspend {
    val context get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

    @Test
    fun testStartMultipleActivitiesInARow() = runTest {
        val activity1 = context.getWithActivity { this }
        val activity2 = context.getWithActivity { this }

        assertNotEquals(activity1, activity2)
        assert(activity1.isDestroyed)
        assert(activity2.isDestroyed)
    }

    @Test
    fun testStartMultipleActivitiesConcurrently() = runTest(timeout = 30.seconds) {
        val activity1Deferred = async { context.getWithActivity { this } }
        val activity2Deferred = async { context.getWithActivity { this } }

        val activity1 = activity1Deferred.await()
        val activity2 = activity2Deferred.await()

        assertEquals(activity1, activity2)
        assert(activity1.isDestroyed)
        assert(activity2.isDestroyed)
    }

    @Test
    fun testStartMultipleActivitiesNested() = runTest {
        var nestedActivity: ActivitySuspend? = null
        val activity1 = context.getWithActivity {
            val inner = context.getWithActivity { this }
            nestedActivity = inner
            inner
        }
        val activity2 = context.getWithActivity { this }

        assertEquals(activity1, nestedActivity)
        assertNotEquals(activity1, activity2)
        assert(nestedActivity?.isDestroyed == true)
        assert(activity1.isDestroyed)
        assert(activity2.isDestroyed)
    }

    private val openAccessibilityIntent get() = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)

    @Test
    fun testStartActivityForResult() = runTest(timeout = 30.seconds) {
        val result = context.startActivityForResult(openAccessibilityIntent)
        assertNull(result)
    }

    @Test
    fun testMultipleStartActivityForResult() = runTest(timeout = 30.seconds) {
        val array: Array<Intent?> = context.getWithActivity {
            val first = startActivityForResult(openAccessibilityIntent)
            val second = startActivityForResult(openAccessibilityIntent)
            arrayOf(first, second)
        }
        assertArrayEquals(array, arrayOf(null as Intent?, null as Intent?))
    }
}
