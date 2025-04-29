package com.sdv.tree3.di

import com.sdv.common.log.file.FileLogs
import com.sdv.common.log.file.FileLogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFileLogs(
        fileLogsRepository: FileLogsRepository,
    ): FileLogs = FileLogs(fileLogsRepository)
}