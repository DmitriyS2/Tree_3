package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetAllNodesUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
) : GetAllNodesUseCase {

    override suspend fun invoke(): Flow<List<NodeUI>> {
        return mainRepository.getAllNodes()
    }
}