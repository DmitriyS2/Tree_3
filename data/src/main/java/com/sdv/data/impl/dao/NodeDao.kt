package com.sdv.data.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdv.data.impl.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NodeDao {

    //дать все nodes
    @Query("SELECT * FROM NodeEntity")
    fun getAllNodes(): Flow<List<NodeEntity>>

    //дать все nodes детей конкретного родителя
    @Query("SELECT * FROM NodeEntity WHERE idParent = :idParent")
    suspend fun getAllChildrenByParent(idParent: Long): List<NodeEntity>

    //дать node по Id
    @Query("SELECT * FROM NodeEntity WHERE id = :id")
    suspend fun getNodeById(id: Long): NodeEntity?

    //добавить новый node
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nodeItemEntity: NodeEntity): Long

    //удалить node по id
    @Query("DELETE FROM NodeEntity WHERE id = :id")
    suspend fun deleteNodeById(id: Long)

    //удалить node по idParent
    @Query("DELETE FROM NodeEntity WHERE idParent = :idParent")
    suspend fun deleteNodeByIdParent(idParent: Long)
}