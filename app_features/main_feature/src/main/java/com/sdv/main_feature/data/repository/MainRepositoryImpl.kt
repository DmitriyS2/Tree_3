package com.sdv.main_feature.data.repository

import com.sdv.data.DatabaseApi
import com.sdv.main_feature.data.mapper.toListUI
import com.sdv.main_feature.data.mapper.toModel
import com.sdv.main_feature.data.mapper.toUI
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MainRepositoryImpl @Inject constructor(
    private val databaseApi: DatabaseApi,
): MainRepository {
    override suspend fun getAll(): List<NodeUI> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChildren(idParent: Long): List<NodeUI> {
        return databaseApi.getAllChildren(idParent).toListUI()
    }

    override suspend fun getItemById(id: Long): NodeUI? {
        return databaseApi.getItemById(id)?.toUI()
    }

    override suspend fun insert(nodeUI: NodeUI): Long {
        return databaseApi.insert(nodeUI.toModel())
    }

    override suspend fun deleteItemById(id: Long) {
        databaseApi.deleteItemById(id)
    }

    override suspend fun deleteItemByIdParent(idParent: Long) {
        databaseApi.deleteItemByIdParent(idParent)
    }

    override fun getMaxId(): Flow<Long> {
        return databaseApi.getMaxId()
    }
}