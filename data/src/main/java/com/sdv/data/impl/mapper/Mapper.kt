package com.sdv.data.impl.mapper

import com.sdv.data.impl.entity.NodeEntity
import com.sdv.domain.model.Node


fun Node.toEntity() = NodeEntity(
    id = this.id,
    name = this.name,
    idParent = this.idParent,
    parents = listToString(this.parents)
)

fun NodeEntity.toModel() = Node(
        id = this.id,
        name = this.name,
        idParent = this.idParent,
        parents = stringToList(this.parents)
)

fun listToString(list: List<Long>) = list.joinToString(",")

fun stringToList(str: String) = str
    .split(",")
    .toList()
    .map {
        it.toLong()
    }

fun List<NodeEntity>.toModel(): List<Node> = this.map{ nodeEntity ->
    nodeEntity.toModel()
}