package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.Flow

internal interface GetAllNodesUseCase{

    suspend operator fun invoke(): Flow<List<NodeUI>>
}