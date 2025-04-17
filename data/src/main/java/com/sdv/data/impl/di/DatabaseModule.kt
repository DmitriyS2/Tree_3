package com.sdv.data.impl.di

import com.sdv.data.DatabaseApi
import com.sdv.data.impl.DatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    @Singleton
    @Binds
    fun bindDatabaseModule(impl: DatabaseImpl): DatabaseApi
}