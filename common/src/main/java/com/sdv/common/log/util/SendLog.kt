package com.sdv.common.log.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import androidx.core.net.toUri

fun sendLog(context: Context, file: File, sendingByMessenger: Boolean) {
    val uri = FileProvider.getUriForFile(context, "UploadLogs", file)
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        putExtra(Intent.EXTRA_STREAM, uri)
        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    if (sendingByMessenger) {
        intent.type = "*/*"
    } else {
        val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
        emailSelectorIntent.setData("mailto:".toUri())
        intent.selector = emailSelectorIntent
    }
    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}