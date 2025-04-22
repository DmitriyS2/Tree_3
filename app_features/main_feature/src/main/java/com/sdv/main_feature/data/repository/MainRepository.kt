package com.sdv.main_feature.data.repository

import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.Flow

internal interface MainRepository {

    suspend fun getAll(): List<NodeUI>
    suspend fun getAllChildren(idParent: Long): List<NodeUI>
    suspend fun getItemById(id: Long): NodeUI?
    suspend fun insert(nodeUI: NodeUI): Long
    suspend fun deleteItemById(id: Long)
    suspend fun deleteItemByIdParent(idParent: Long)
    fun getMaxId(): Flow<Long>
}