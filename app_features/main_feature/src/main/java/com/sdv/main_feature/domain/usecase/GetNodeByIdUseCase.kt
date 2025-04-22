package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.domain.model.NodeUI

internal interface GetNodeByIdUseCase {

    suspend operator fun invoke(id: Long): NodeUI?
}