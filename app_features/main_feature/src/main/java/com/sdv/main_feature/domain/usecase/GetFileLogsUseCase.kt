package com.sdv.main_feature.domain.usecase

import java.io.File

internal interface GetFileLogsUseCase {

    suspend operator fun invoke(): File
}