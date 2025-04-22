package com.sdv.main_feature.domain.usecase

import android.util.Log
import com.sdv.common.encrypt
import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class AddNodeUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : AddNodeUseCase {

    override suspend fun invoke(nodeUI: NodeUI?) {
        //вставка ребенка
        nodeUI?.let {
            val tempParents: MutableList<Long> = mutableListOf()
            tempParents.addAll(nodeUI.parents)
            tempParents.add(nodeUI.id)
            val newNodeUI = NodeUI(
                name = encrypt(mainRepository.getMaxId().first().toString()),
                idParent = nodeUI.id,
                parents = tempParents.toList(),
                children = emptyList()
            )
            val newNodeUIid = mainRepository.insert(newNodeUI)
            Log.d("MyLog", "newNodeUI=$newNodeUI ,newNodeUIid=$newNodeUIid")

            // доб нового ребенка в children у родителя
            val tempChildren: MutableList<Long> = mutableListOf()
            tempChildren.addAll(nodeUI.children)
            tempChildren.add(newNodeUIid)
            Log.d("MyLog", "nodeUI.children=${nodeUI.children} tempChildren=$tempChildren")
            val oldNodeUI = NodeUI(
                id = nodeUI.id,
                name = nodeUI.name,
                idParent = nodeUI.idParent,
                parents = nodeUI.parents,
                children = tempChildren.toList()
            )
            Log.d("MyLog", "oldNodeUI=$oldNodeUI")
            val t = mainRepository.insert(oldNodeUI)
            Log.d("MyLog", "t=$t")
        }
    }
}