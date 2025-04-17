package com.sdv.datastore.impl.di

import com.sdv.datastore.DataStorage
import com.sdv.datastore.impl.DataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataStorageModule {

    @Singleton
    @Binds
    abstract fun bindDataStorage(impl: DataStorageImpl): DataStorage
}