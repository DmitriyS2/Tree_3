package com.sdv.main_feature.domain.usecase

import android.util.Log
import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import javax.inject.Inject

internal class DeleteNodeUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : DeleteNodeUseCase {

    override suspend fun invoke(nodeUI: NodeUI) {
        mainRepository.deleteItemById(nodeUI.id)
        mainRepository.deleteItemByIdParent(nodeUI.id)

        // удалить данный node в children у его родителя
        val hisParent = mainRepository.getItemById(nodeUI.idParent)
        hisParent?.let {
            val hisBrothers: MutableList<Long> = mutableListOf()
            hisBrothers.addAll(hisParent.children)
            Log.d("MyLog", "hisBrothers1=${hisBrothers}")
            hisBrothers.remove(nodeUI.id)
            Log.d("MyLog", "hisBrothers2=${hisBrothers}")
            val newHisParent = NodeUI(
                id = hisParent.id,
                name = hisParent.name,
                idParent = hisParent.idParent,
                parents = hisParent.parents,
                children = hisBrothers.toList()
            )
            Log.d("MyLog", "newHisParent=$newHisParent")
            val t = mainRepository.insert(newHisParent)
            Log.d("MyLog", "t=$t")
        }
    }
}