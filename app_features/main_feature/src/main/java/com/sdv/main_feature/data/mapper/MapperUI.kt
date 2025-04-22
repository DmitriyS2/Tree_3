package com.sdv.main_feature.data.mapper

import com.sdv.domain.model.Node
import com.sdv.main_feature.domain.model.NodeUI

fun Node.toUI() = NodeUI(
    id = this.id,
    name = this.name,
    idParent = this.idParent,
    parents = this.parents,
    children = this.children,
    countParent = this.parents.size,
    countChildren = this.children.size,
)

fun NodeUI.toModel() = Node(
    id = this.id,
    name = this.name,
    idParent = this.idParent,
    parents = this.parents,
    children = this.children,
)

fun List<Node>.toListUI() = this.map { node ->
    node.toUI()
}

fun List<NodeUI>.toListModel() = this.map { nodeUI ->
    nodeUI.toModel()
}