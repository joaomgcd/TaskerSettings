package com.joaomgcd.taskerbackcompat.util

import android.support.annotation.Keep

fun <T> tryGetOrNull(block: () -> T) = try {
    block()
} catch (ex: Exception) {
    null
}
