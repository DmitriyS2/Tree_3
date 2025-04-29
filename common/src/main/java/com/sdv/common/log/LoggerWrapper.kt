package com.sdv.common.log

import com.sdv.common.log.instance.Logger

object LoggerWrapper {

    private val loggers = ArrayList<Logger>()

    @JvmStatic
    fun registerLogger(logger: Logger) {
        loggers.add(logger)
    }

    @JvmOverloads
    @JvmStatic
    fun error(tag: String, message: String?, throwable: Throwable? = null) {
        loggers.forEach {
            it.error(tag, message, throwable)
        }
    }

    @JvmOverloads
    @JvmStatic
    fun debug(tag: String, message: String?, throwable: Throwable? = null) {
        loggers.forEach {
            it.debug(tag, message, throwable)
        }
    }

    @JvmOverloads
    @JvmStatic
    fun info(tag: String, message: String?, throwable: Throwable? = null) {
        loggers.forEach {
            it.info(tag, message, throwable)
        }
    }

    @JvmOverloads
    @JvmStatic
    fun warn(tag: String, message: String?, throwable: Throwable? = null) {
        loggers.forEach {
            it.warn(tag, message, throwable)
        }
    }
}