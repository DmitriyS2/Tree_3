package com.sdv.main_feature.domain.model

data class NodeUI(
    val id: Long = 0L,
    val name: String = "",
    val idParent: Long = 0L,
    val parents: List<Long> = listOf(0L),
    val children: List<NodeUI> = emptyList(),
)
