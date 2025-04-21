package com.sdv.main_feature.di

import com.sdv.main_feature.domain.usecase.AddNodeUseCase
import com.sdv.main_feature.domain.usecase.AddNodeUseCaseImpl
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCase
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCaseImpl
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCase
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCaseModule {

    @Binds
    @Singleton
    fun bindAddNodeUseCase(impl: AddNodeUseCaseImpl): AddNodeUseCase

    @Binds
    @Singleton
    fun bindGetNodeByIdUseCase(impl: GetNodeByIdUseCaseImpl): GetNodeByIdUseCase

    @Binds
    @Singleton
    fun bindGetChildrenForParentByIdUseCase(impl: GetChildrenForParentByIdUseCaseImpl): GetChildrenForParentByIdUseCase
}