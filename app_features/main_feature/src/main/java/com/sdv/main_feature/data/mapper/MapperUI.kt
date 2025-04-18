package com.sdv.main_feature.data.mapper

import com.sdv.domain.model.Node
import com.sdv.main_feature.data.repo.NodeRepository
import com.sdv.main_feature.domain.model.NodeUI
import javax.inject.Inject

internal class MapperUI @Inject constructor(
    private val nodeRepository: NodeRepository,
){

    companion object {
        fun Node.toUI(listChildren:List<NodeUI>) = NodeUI(
            id = this.id,
            name = this.name,
            idParent = this.idParent,
            parents = this.parents,
            children = listChildren,

        )

        fun NodeUI.toModel() = Node(
            id = this.id,
            name = this.name,
            idParent = this.idParent,
            parents = this.parents,
            children = this.children.toListId()
        )

        fun listToString(list: List<Long>) = list.joinToString(",")

        fun stringToList(str: String) = str
            .split(",")
            .toList()
            .map {
                it.toLong()
            }

        fun List<NodeUI>.toModel(): List<Node> = this.map { nodeUI ->
            nodeUI.toModel()
        }

        fun List<NodeUI>.toListId(): List<Long> {
            val listId:MutableList<Long> = mutableListOf()
            this.forEach{ nodeUI ->
                nodeUI.children.forEach { current ->
                    listId.add(current.id)
                }
            }
            return listId.toList()
        }
    }
}