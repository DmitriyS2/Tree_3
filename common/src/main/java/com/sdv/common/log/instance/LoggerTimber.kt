package com.sdv.common.log.instance

import timber.log.Timber
import javax.inject.Inject

internal class LoggerTimber @Inject constructor() : Logger {

    override fun error(tag: String?, message: String?, throwable: Throwable?) {
        Timber.e("${throwable ?: ""} ${tag ?: ""} ${message ?: ""}")
    }

    override fun debug(tag: String?, message: String?, throwable: Throwable?) {
        Timber.d("${throwable ?: ""} ${tag ?: ""} ${message ?: ""}")
    }

    override fun info(tag: String?, message: String?, throwable: Throwable?) {
        Timber.i("${throwable ?: ""} ${tag ?: ""} ${message ?: ""}")
    }

    override fun warn(tag: String?, message: String?, throwable: Throwable?) {
        Timber.w("${throwable ?: ""} ${tag ?: ""} ${message ?: ""}")
    }
}
