package com.sdv.data.impl

import com.sdv.data.DatabaseApi
import com.sdv.data.impl.dao.NodeDao
import com.sdv.data.impl.mapper.toEntity
import com.sdv.data.impl.mapper.toModel
import com.sdv.domain.model.Node
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DatabaseImpl @Inject constructor(
    private val nodeDao: NodeDao,
) : DatabaseApi {

    override suspend fun getAllChildren(idParent: Long): List<Node> = nodeDao.getAllChildren(idParent).toModel()

    override suspend fun getItemById(id: Long): Node? = nodeDao.getItemById(id)?.toModel()

    override suspend fun insert(node: Node): Long = nodeDao.insert(node.toEntity())

    override suspend fun deleteItemById(id: Long) = nodeDao.deleteItemById(id)

    override suspend fun deleteItemByIdParent(idParent: Long) = nodeDao.deleteItemByIdParent(idParent)

    override fun getMaxId(): Flow<Long> = nodeDao.getMaxId()
}