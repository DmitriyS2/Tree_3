package com.sdv.data.impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class NodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String = "",
    val idParent: Long,
    val parents: String = "0",
    val children:String = "",
)