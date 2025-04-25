package com.sdv.data.impl.mapper

import com.sdv.data.impl.entity.NodeEntity
import com.sdv.domain.model.Node


internal fun Node.toEntity() = NodeEntity(
    id = this.id,
    name = this.name,
    idParent = this.idParent,
    parents = listToString(this.parents),
    children = listToString(this.children),
)

internal fun NodeEntity.toListModel() = Node(
    id = this.id,
    name = this.name,
    idParent = this.idParent,
    parents = stringToList(this.parents),
    children = stringToList(this.children),
)

internal fun listToString(list: List<Long>) = list.joinToString(",")

internal fun stringToList(str: String): List<Long> {
    return try {
        str
            .split(",")
            .toList()
            .map {
                it.toLong()
            }
    } catch (e: Exception) {
        emptyList()
    }
}

internal fun List<NodeEntity>.toListModel(): List<Node> = this.map { nodeEntity ->
    nodeEntity.toListModel()
}