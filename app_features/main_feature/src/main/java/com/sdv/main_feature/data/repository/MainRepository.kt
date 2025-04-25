package com.sdv.main_feature.data.repository

import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.Flow

internal interface MainRepository {

    suspend fun getAllNodes(): Flow<List<NodeUI>>
    suspend fun getAllChildrenByParent(idParent: Long): List<NodeUI>
    suspend fun getNodeById(id: Long): NodeUI?
    suspend fun insert(nodeUI: NodeUI): Long
    suspend fun deleteNodeById(id: Long)
    suspend fun deleteNodeByIdParent(idParent: Long)
}