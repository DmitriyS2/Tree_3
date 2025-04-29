package com.sdv.common.log.util

import com.sdv.common.log.LoggerWrapper
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ZipHelper {

    var TAG: String = "LogUtils"
    private const val BUFFER_SIZE = 1024 * 4

    fun zipFiles(outFile: File?, vararg files: File) {
        var fi: InputStream? = null
        try {
            var origin: BufferedInputStream

            ZipOutputStream(BufferedOutputStream(FileOutputStream(outFile))).use { out ->
                val buffer = ByteArray(BUFFER_SIZE)
                for (file in files) {
                    fi = FileInputStream(file)
                    origin = BufferedInputStream(fi, BUFFER_SIZE)
                    try {
                        val entry = ZipEntry(file.name)
                        out.putNextEntry(entry)
                        var count: Int
                        while ((origin.read(buffer, 0, BUFFER_SIZE).also {
                                count = it
                            }) != -1) {
                            out.write(buffer, 0, count)
                        }
                    } finally {
                        origin.close()
                    }
                }
            }
        } catch (e: IOException) {
            LoggerWrapper.error(TAG, e.message, e)
        } finally {
            if (fi != null) {
                try {
                    fi!!.close()
                } catch (e: IOException) {
                    LoggerWrapper.error(TAG, e.message, e)
                }
            }
        }
    }
}