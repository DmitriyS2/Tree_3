package com.sdv.main_feature.di

import com.sdv.main_feature.data.mapper.MapperUI
import com.sdv.main_feature.data.repo.NodeRepository
import com.sdv.main_feature.data.repo.NodeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsLoginRepository(impl: NodeRepositoryImpl): NodeRepository

    companion object {
    @Provides
    @Singleton
    fun provideMapperUi(nodeRepository: NodeRepository): MapperUI {
        return MapperUI(nodeRepository)
    }
}
}