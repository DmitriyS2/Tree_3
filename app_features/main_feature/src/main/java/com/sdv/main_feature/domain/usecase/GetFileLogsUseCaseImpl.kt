package com.sdv.main_feature.domain.usecase

import com.sdv.common.log.file.FileLogs
import java.io.File
import javax.inject.Inject

internal class GetFileLogsUseCaseImpl @Inject constructor(
    private val fileLogs: FileLogs,
) : GetFileLogsUseCase {

    override suspend fun invoke(): File {
        return fileLogs.sendLogs()
    }
}