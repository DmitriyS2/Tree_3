package com.sdv.common.log.file

import java.io.File

interface FileLogsRepository {

    fun getAllFileName(): List<String>?
    fun deleteAllLogs()
    fun deleteLogsByDate(fileName: String): Boolean
    fun getLogsByDate(fileName: String): File
    fun writeLogs(log: String, fileName: String)
    fun getLogFile(fileName: String): File
    fun makeNewFolder(): Boolean
    fun makeNewEmptyFile(fileName: String): File
}