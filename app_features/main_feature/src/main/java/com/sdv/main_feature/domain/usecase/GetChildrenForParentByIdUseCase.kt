package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.domain.model.NodeUI

internal interface GetChildrenForParentByIdUseCase {

    suspend operator fun invoke(idParent:Long):List<NodeUI>
}