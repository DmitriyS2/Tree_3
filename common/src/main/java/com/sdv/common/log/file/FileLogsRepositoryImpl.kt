package com.sdv.common.log.file

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

internal class FileLogsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) :FileLogsRepository {

    override fun getAllFileName(): List<String>? = path?.let { filePath ->
        File(filePath).listFiles()?.map { file ->
            file.name
        }
    }

    override fun getLogFile(fileName: String): File = File(path, fileName)

    override fun deleteAllLogs() {
        File(context.filesDir.toURI()).delete()
    }

    override fun deleteLogsByDate(fileName: String) = File(path, fileName).delete()

    override fun getLogsByDate(fileName: String): File = File(path, fileName)

    override fun makeNewFolder() = context.getExternalFilesDir(FOLDER_NAME)?.mkdir()!!

    override fun writeLogs(log: String, fileName: String) = File(path, fileName).appendText(log)

    override fun makeNewEmptyFile(fileName: String) = File(path, fileName)

    private val path = context.getExternalFilesDir(FOLDER_NAME)?.absolutePath

    private companion object {
        private const val FOLDER_NAME = "LogsFolder"
    }
}