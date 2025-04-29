package com.sdv.main_feature.domain.usecase

import com.sdv.common.log.util.TAG
import com.sdv.common.log.util.logDebug
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import javax.inject.Inject

internal class DeleteNodeUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStorage: DataStorage,
) : DeleteNodeUseCase {

    override suspend fun invoke(nodeUI: NodeUI, newParentId: Long) {
        mainRepository.deleteNodeById(nodeUI.id)
        mainRepository.deleteNodeByIdParent(nodeUI.id)
        "deleted $nodeUI".logDebug(TAG)
        // удалить данный node в children у его родителя
        val hisParent = mainRepository.getNodeById(nodeUI.idParent)
        hisParent?.let {
            val hisBrothers: MutableList<Long> = mutableListOf()
            hisBrothers.addAll(hisParent.children)
            hisBrothers.remove(nodeUI.id)
            val newHisParent = NodeUI(
                id = hisParent.id,
                name = hisParent.name,
                idParent = hisParent.idParent,
                parents = hisParent.parents,
                children = hisBrothers.toList()
            )
            mainRepository.insert(newHisParent)
            dataStorage.setCurrentParent(newParentId)
        }
    }
}