package com.sdv.main_feature.di

import com.sdv.main_feature.data.repo.MainRepository
import com.sdv.main_feature.data.repo.MainRepositoryImpl
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
    fun bindsMainRepository(impl: MainRepositoryImpl): MainRepository
}