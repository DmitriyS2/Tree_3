package com.sdv.main_feature.domain.model

data class NodeUI(
    val id: Long = 0L,
    val name: String = "",
    val idParent: Long = 0L,
    val countParent:Int = 0,
    val countChildren:Int = 0,
    val parents: List<Long> = emptyList(),
    val children: List<Long> = emptyList(),
)
