package com.sdv.common.log.file

import android.os.Environment
import com.sdv.common.log.util.TAG
import com.sdv.common.log.util.ZipHelper
import com.sdv.common.log.util.formatFromNameString
import com.sdv.common.log.util.isTrue
import com.sdv.common.log.util.logDebug
import com.sdv.common.log.util.logError
import com.sdv.common.log.util.substringBetween
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.joda.time.LocalDateTime
import org.joda.time.Period
import org.joda.time.PeriodType
import org.joda.time.format.DateTimeFormat
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FileLogs @Inject constructor(
    private val fileLogsRepository: FileLogsRepository,
) : Timber.Tree() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val date = Date().getRecordingLogDate

        val log = if (t == null && tag == null) "$DATE$date $PRIORITY$priority $MESSAGE$message\r"
        else if (t == null) "$DATE$date $PRIORITY$priority ${FileLogs.TAG}$tag $MESSAGE$message \r"
        else if (tag == null) "$DATE$date $PRIORITY$priority $MESSAGE$message $THROWABLE$t\r"
        else "$DATE$date $PRIORITY$priority ${FileLogs.TAG}$tag $MESSAGE$message $THROWABLE$t\r"

        val fileName = LocalDateTime().toFileName.formatFromNameString

        coroutineScope.launch {
            try {
                fileLogsRepository.makeNewFolder()
                fileLogsRepository.writeLogs(log = log, fileName = fileName)
            } catch (t: Throwable) {
                t.message.toString().logError(TAG)
            }
        }
    }

    fun deleteOldLogs() {
        val filesName = fileLogsRepository.getAllFileName().orEmpty()
        val currentDate = LocalDateTime()
        filesName.filter { fileName ->
            fileName.toLocalDateTime().let { localDateTime ->
                val period = Period(localDateTime, currentDate, PeriodType.dayTime())
                val delete = period.days >= LOG_STORAGE_DAYS
                "deleteLogFile fileDate: $localDateTime period:  ${period.days}d ${period.hours}h delete: $delete".logDebug(
                    TAG
                )
                delete
            }.isTrue()
        }.forEach { fileNameForDelete ->
            val delete = fileLogsRepository.deleteLogsByDate(fileNameForDelete)
            "deleteLogFile: $fileNameForDelete delete: $delete".logDebug(TAG)
        }
    }

    fun sendLogs(): File {
        val path = "${Environment.getDataDirectory().absolutePath}/data/$FOLDER_NAME/files"
        val dir = File(path)
        val files = getLogsInPeriod()
        val pathName: String
        val outFile: File

        if (files.isNotEmpty()) {
            pathName = "upload_logs.zip"
            outFile = File(dir, pathName)
            files.toTypedArray()
            val filesForZipList: MutableList<File> = mutableListOf()
            files.forEachIndexed { _, value ->
                "${value.name} added to zip".logDebug(TAG)
                filesForZipList.add(value)
            }
            ZipHelper.zipFiles(outFile, *filesForZipList.toTypedArray())
        } else {
            pathName = "upload_logs.txt"
            outFile = File(dir, pathName)
            outFile.writeText("лог файлов не найдено")
            "no logs found".logDebug(TAG)
        }
        "file logs $pathName (length=${outFile.length()}) ready for sending".logDebug(TAG)
        return outFile
    }

    private fun getLogsInPeriod(): List<File> {
        val fileNames = fileLogsRepository.getAllFileName()
        return fileNames?.map {
            fileLogsRepository.getLogsByDate(it)
        }.orEmpty()
    }

    private companion object {
        private const val FOLDER_NAME = "com.sdv.tree3"
        private const val DATE = "Date:"
        private const val PRIORITY = "priority:"
        private const val TAG = "tag:"
        private const val MESSAGE = "message:"
        private const val THROWABLE = "throwable:"
        private const val LOG_STORAGE_DAYS = 30
        private const val DATE_DAY_WITH_HOUR_PATTERN = "yyyy-MM-dd-HH"
        private const val DATE_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss"
        private val logFormatter = SimpleDateFormat(DATE_SECOND_PATTERN, Locale.US)
        private val nameFileFormatter = DateTimeFormat.forPattern(DATE_DAY_WITH_HOUR_PATTERN)
    }

    private val Date.getRecordingLogDate: String
        get() = logFormatter.format(this)

    private val LocalDateTime.toFileName: String
        get() = this.toString(nameFileFormatter)

    private fun String.toLocalDateTime(): LocalDateTime {
        val subString = substringBetween("_", ".").orEmpty()
        return LocalDateTime.parse(subString, DateTimeFormat.forPattern(DATE_DAY_WITH_HOUR_PATTERN))
    }
}