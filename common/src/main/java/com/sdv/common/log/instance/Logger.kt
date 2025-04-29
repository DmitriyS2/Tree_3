package com.sdv.common.log.instance

interface Logger {
    fun error(tag: String?, message: String?, throwable: Throwable? = null)
    fun debug(tag: String?, message: String?, throwable: Throwable? = null)
    fun info(tag: String?, message: String?, throwable: Throwable? = null)
    fun warn(tag: String?, message: String?, throwable: Throwable? = null)
}