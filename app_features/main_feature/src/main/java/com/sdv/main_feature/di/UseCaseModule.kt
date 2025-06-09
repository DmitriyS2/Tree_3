package com.sdv.main_feature.di

import com.sdv.main_feature.domain.usecase.AddNodeUseCase
import com.sdv.main_feature.domain.usecase.AddNodeUseCaseImpl
import com.sdv.main_feature.domain.usecase.DeleteNodeUseCase
import com.sdv.main_feature.domain.usecase.DeleteNodeUseCaseImpl
import com.sdv.main_feature.domain.usecase.GetAllNodesUseCase
import com.sdv.main_feature.domain.usecase.GetAllNodesUseCaseImpl
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCase
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCaseImpl
import com.sdv.main_feature.domain.usecase.GetFileLogsUseCase
import com.sdv.main_feature.domain.usecase.GetFileLogsUseCaseImpl
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCase
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCaseImpl
import com.sdv.main_feature.domain.usecase.GoToChildrenUseCase
import com.sdv.main_feature.domain.usecase.GoToChildrenUseCaseImpl
import com.sdv.main_feature.domain.usecase.GoToParentUseCase
import com.sdv.main_feature.domain.usecase.GoToParentUseCaseImpl
import com.sdv.main_feature.domain.usecase.SetFirstParentUseCase
import com.sdv.main_feature.domain.usecase.SetFirstParentUseCaseImpl
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
    fun bindSetFirstParentUseCase(impl: SetFirstParentUseCaseImpl): SetFirstParentUseCase

    @Binds
    @Singleton
    fun bindAddNodeUseCase(impl: AddNodeUseCaseImpl): AddNodeUseCase

    @Binds
    @Singleton
    fun bindGetNodeByIdUseCase(impl: GetNodeByIdUseCaseImpl): GetNodeByIdUseCase

    @Binds
    @Singleton
    fun bindGetChildrenForParentByIdUseCase(impl: GetChildrenForParentByIdUseCaseImpl): GetChildrenForParentByIdUseCase

    @Binds
    @Singleton
    fun bindDeleteNodeUseCase(impl: DeleteNodeUseCaseImpl): DeleteNodeUseCase

    @Binds
    @Singleton
    fun bindGoToParentUseCase(impl: GoToParentUseCaseImpl): GoToParentUseCase

    @Binds
    @Singleton
    fun bindGoToChildrenUseCase(impl: GoToChildrenUseCaseImpl): GoToChildrenUseCase

    @Binds
    @Singleton
    fun bindGetAllNodesUseCase(impl: GetAllNodesUseCaseImpl): GetAllNodesUseCase

    @Binds
    @Singleton
    fun bindGetFileLogsUseCase(impl: GetFileLogsUseCaseImpl): GetFileLogsUseCase
}