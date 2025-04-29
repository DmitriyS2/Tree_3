package com.sdv.common.log.util

import com.sdv.common.log.LoggerWrapper

fun String.logError(tag: String, throwable: Throwable? = null) =
        LoggerWrapper.error(tag, this, throwable)


fun String.logDebug(tag: String, throwable: Throwable? = null) =
        LoggerWrapper.debug(tag, this, throwable)


fun String.logInfo(tag: String, throwable: Throwable? = null) =
        LoggerWrapper.info(tag, this, throwable)


fun String.logWarn(tag: String, throwable: Throwable? = null) =
        LoggerWrapper.warn(tag, this, throwable)

val String.formatFromNameString: String
        get() = LOG_PREFIX.plus(this).plus(FORMAT_TXT)

fun String.substringBetween(start: String, end: String): String? {
        val firstIndex = indexOf(start)
        val endIndex = indexOf(end)
        if (firstIndex == -1 || endIndex == -1) return null
        return substring(firstIndex + 1, endIndex)
}

private const val LOG_PREFIX = "log_"
private const val FORMAT_TXT = ".txt"