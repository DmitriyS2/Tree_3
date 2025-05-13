package com.sdv.common

fun Long?.orZero() = this ?: 0L

fun Int?.orZero() = this ?: 0