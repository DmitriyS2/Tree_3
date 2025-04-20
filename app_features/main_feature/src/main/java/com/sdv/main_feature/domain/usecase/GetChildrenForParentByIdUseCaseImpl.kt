package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.data.repo.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import javax.inject.Inject

internal class GetChildrenForParentByIdUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
):GetChildrenForParentByIdUseCase {

    override suspend fun invoke(idParent: Long): List<NodeUI> {
        return mainRepository.getAllChildren(idParent)
    }
}