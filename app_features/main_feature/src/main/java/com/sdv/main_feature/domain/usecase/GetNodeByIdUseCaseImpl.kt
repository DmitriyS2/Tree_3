package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import javax.inject.Inject

internal class GetNodeByIdUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
) : GetNodeByIdUseCase {

    override suspend fun invoke(id: Long): NodeUI? {
        return mainRepository.getItemById(id)
    }
}