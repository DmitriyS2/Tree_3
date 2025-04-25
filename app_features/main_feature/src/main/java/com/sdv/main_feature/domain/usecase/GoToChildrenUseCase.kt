package com.sdv.main_feature.domain.usecase

internal interface GoToChildrenUseCase {

    suspend operator fun invoke(newParentId: Long)
}