package com.sdv.common.log.di

import com.sdv.common.log.file.FileLogsRepository
import com.sdv.common.log.file.FileLogsRepositoryImpl
import com.sdv.common.log.instance.Logger
import com.sdv.common.log.instance.LoggerTimber
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsFileLogsRepository(impl: FileLogsRepositoryImpl): FileLogsRepository

    @Binds
    @Singleton
    fun bindsLogger(impl: LoggerTimber): Logger
}