package com.sdv.datastore

import kotlinx.coroutines.flow.Flow

interface DataStorage {

    val currentParent: Flow<Long>
    suspend fun setCurrentParent(currentParent:Long)
}