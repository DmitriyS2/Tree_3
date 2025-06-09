package com.sdv.main_feature.domain.usecase

import com.sdv.common.log.util.TAG
import com.sdv.common.log.util.logDebug
import com.sdv.datastore.DataStorage
import javax.inject.Inject

internal class GoToParentUseCaseImpl @Inject constructor(
    private val dataStorage: DataStorage,
) : GoToParentUseCase {

    override suspend fun invoke(newParentId: Long) {
        dataStorage.setCurrentParent(newParentId)
        "went to parent id=$newParentId".logDebug(TAG)
    }
}