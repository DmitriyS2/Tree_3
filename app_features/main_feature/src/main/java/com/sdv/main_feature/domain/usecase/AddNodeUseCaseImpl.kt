package com.sdv.main_feature.domain.usecase

import com.sdv.common.encrypt
import com.sdv.common.log.util.TAG
import com.sdv.common.log.util.logDebug
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class AddNodeUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStorage: DataStorage,
) : AddNodeUseCase {

    override suspend fun invoke(nodeUI: NodeUI?) {
        //вставка ребенка
        nodeUI?.let {
            val tempParents: MutableList<Long> = mutableListOf()
            tempParents.addAll(nodeUI.parents)
            tempParents.add(nodeUI.id)
            val newChild = NodeUI(
                name = encrypt(dataStorage.countId.first().toString()),
                idParent = nodeUI.id,
                parents = tempParents.toList(),
                children = emptyList()
            )
            val newNodeUIid = mainRepository.insert(newChild)
            "added new child id=$newNodeUIid, name=${newChild.name}".logDebug(TAG)
            dataStorage.updateCountId()

            // доб нового ребенка в children у родителя
            val tempChildren: MutableList<Long> = mutableListOf()
            tempChildren.addAll(nodeUI.children)
            tempChildren.add(newNodeUIid)
            val parentWithNewChild = NodeUI(
                id = nodeUI.id,
                name = nodeUI.name,
                idParent = nodeUI.idParent,
                parents = nodeUI.parents,
                children = tempChildren.toList()
            )
            mainRepository.insert(parentWithNewChild)
        }
    }
}