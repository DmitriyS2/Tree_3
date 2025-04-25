package com.sdv.main_feature.domain.usecase

internal interface GoToParentUseCase {

    suspend operator fun invoke(newParentId: Long)
}