package com.sdv.main_feature.data.repo

import com.sdv.domain.model.Node
import kotlinx.coroutines.flow.Flow

internal interface NodeRepository {

    suspend fun getAll(): List<Node>
    suspend fun getAllChildren(idParent: Long): List<Node>
    suspend fun getItemById(id: Long): Node?
    suspend fun insert(node: Node): Long
    suspend fun deleteItemById(id: Long)
    suspend fun deleteItemByIdParent(idParent: Long)
    fun getMaxId(): Flow<Long>
}