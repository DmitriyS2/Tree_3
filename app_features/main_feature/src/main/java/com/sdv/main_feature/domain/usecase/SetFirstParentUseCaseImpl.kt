package com.sdv.main_feature.domain.usecase

import android.util.Log
import com.sdv.common.encrypt
import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import javax.inject.Inject

internal class SetFirstParentUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
): SetFirstParentUseCase {

    override suspend fun invoke() {
        val firstParent = NodeUI(
            name = encrypt("1"),
            idParent = 0,
            parents = emptyList(),
            children = emptyList(),
        )
        val parentId = mainRepository.insert(firstParent)
        Log.d("MyLog", "parentId=$parentId")
    }
}