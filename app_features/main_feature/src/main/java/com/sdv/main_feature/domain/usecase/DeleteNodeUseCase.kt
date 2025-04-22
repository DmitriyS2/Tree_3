package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.domain.model.NodeUI

internal interface DeleteNodeUseCase {

    suspend operator fun invoke(nodeUI: NodeUI)
}