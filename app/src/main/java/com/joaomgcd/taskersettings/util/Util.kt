package com.joaomgcd.taskerbackcompat.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun <T> tryGetOrNull(block: () -> T) = try {
    block()
} catch (ex: Exception) {
    null
}

class CoroutineScopeIO : CoroutineScope by CoroutineScope(Dispatchers.IO + SupervisorJob())
