package com.sdv.main_feature.data.repository

import com.sdv.data.DatabaseApi
import com.sdv.main_feature.data.mapper.toListUI
import com.sdv.main_feature.data.mapper.toModel
import com.sdv.main_feature.data.mapper.toUI
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

internal class MainRepositoryImpl @Inject constructor(
    private val databaseApi: DatabaseApi,
) : MainRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllNodes(): Flow<List<NodeUI>> {
        return databaseApi.getAllNodes().mapLatest { it.toListUI() }
    }

    override suspend fun getAllChildrenByParent(idParent: Long): List<NodeUI> {
        return databaseApi.getAllChildrenByParent(idParent).toListUI()
    }

    override suspend fun getNodeById(id: Long): NodeUI? {
        return databaseApi.getNodeById(id)?.toUI()
    }

    override suspend fun insert(nodeUI: NodeUI): Long {
        return databaseApi.insert(nodeUI.toModel())
    }

    override suspend fun deleteNodeById(id: Long) {
        databaseApi.deleteNodeById(id)
    }

    override suspend fun deleteNodeByIdParent(idParent: Long) {
        databaseApi.deleteNodeByIdParent(idParent)
    }
}