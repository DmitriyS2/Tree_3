package com.sdv.main_feature.domain.usecase

import com.sdv.common.encrypt
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class SetFirstParentUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStorage: DataStorage,
) : SetFirstParentUseCase {

    override suspend fun invoke() {
        val firstParent = NodeUI(
            name = encrypt(dataStorage.countId.first().toString()),
            idParent = 0,
            parents = emptyList(),
            children = emptyList(),
        )
        mainRepository.insert(firstParent)
        dataStorage.updateCountId()
    }
}