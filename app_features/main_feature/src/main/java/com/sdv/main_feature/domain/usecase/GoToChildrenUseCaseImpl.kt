package com.sdv.main_feature.domain.usecase

import com.sdv.common.log.util.TAG
import com.sdv.common.log.util.logDebug
import com.sdv.datastore.DataStorage
import javax.inject.Inject

internal class GoToChildrenUseCaseImpl @Inject constructor(
    private val dataStorage: DataStorage,
) : GoToChildrenUseCase {

    override suspend fun invoke(newParentId: Long) {
        dataStorage.setCurrentParent(newParentId)
        "went to children for node id=$newParentId".logDebug(TAG)
    }
}