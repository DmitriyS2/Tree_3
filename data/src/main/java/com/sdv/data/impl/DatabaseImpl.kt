package com.sdv.data.impl

import com.sdv.data.DatabaseApi
import com.sdv.data.impl.dao.NodeDao
import com.sdv.data.impl.mapper.toEntity
import com.sdv.data.impl.mapper.toListModel
import com.sdv.domain.model.Node
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

internal class DatabaseImpl @Inject constructor(
    private val nodeDao: NodeDao,
) : DatabaseApi {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllNodes(): Flow<List<Node>> = nodeDao.getAllNodes().mapLatest { it.toListModel() }

    override suspend fun getAllChildrenByParent(idParent: Long): List<Node> = nodeDao.getAllChildrenByParent(idParent).toListModel()

    override suspend fun getNodeById(id: Long): Node? = nodeDao.getNodeById(id)?.toListModel()

    override suspend fun insert(node: Node): Long = nodeDao.insert(node.toEntity())

    override suspend fun deleteNodeById(id: Long) = nodeDao.deleteNodeById(id)

    override suspend fun deleteNodeByIdParent(idParent: Long) = nodeDao.deleteNodeByIdParent(idParent)
}