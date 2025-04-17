package com.sdv.data.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdv.data.impl.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NodeDao {

    //дать все item'ы
    @Query("SELECT * FROM NodeEntity")
    suspend fun getAll(): List<NodeEntity>

    //дать все item'ы детей конкретного родителя
    @Query("SELECT * FROM NodeEntity WHERE idParent = :idParent")
    suspend fun getAllChildren(idParent: Long): List<NodeEntity>

    //дать item по Id
    @Query("SELECT * FROM NodeEntity WHERE id = :id")
    suspend fun getItemById(id: Long): NodeEntity?

    //добавить новый item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nodeItemEntity: NodeEntity): Long

    //удалить item по id
    @Query("DELETE FROM NodeEntity WHERE id = :id")
    suspend fun deleteItemById(id: Long)

    //удалить item по idParent
    @Query("DELETE FROM NodeEntity WHERE idParent = :idParent")
    suspend fun deleteItemByIdParent(idParent: Long)

    //дать maxId в БД
    @Query("SELECT MAX(id) FROM NodeEntity")
    fun getMaxId(): Flow<Long>
}