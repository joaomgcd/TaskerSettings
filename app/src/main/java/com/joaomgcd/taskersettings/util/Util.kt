package com.joaomgcd.taskerbackcompat.util

import android.content.Intent
import android.net.Uri
import androidx.annotation.Keep

fun <T> tryGetOrNull(block: () -> T) = try {
    block()
} catch (ex: Exception) {
    null
}
