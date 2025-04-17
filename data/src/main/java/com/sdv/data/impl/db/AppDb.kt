package com.sdv.data.impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdv.data.impl.dao.NodeDao
import com.sdv.data.impl.entity.NodeEntity

@Database(
    entities = [NodeEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDb : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}