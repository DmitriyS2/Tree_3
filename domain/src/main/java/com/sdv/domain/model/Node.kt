package com.sdv.domain.model

data class Node(
    val id: Long = 0L,
    val name: String = "",
    val idParent: Long = 0L,
    val parents: List<Long> = listOf(0L),
)
