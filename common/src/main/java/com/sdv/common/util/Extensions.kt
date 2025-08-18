package com.sdv.common.util

import android.content.Context
import android.widget.Toast

fun Long?.orZero() = this ?: 0L

fun Int?.orZero() = this ?: 0

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}