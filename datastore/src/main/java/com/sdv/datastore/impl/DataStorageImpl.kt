package com.sdv.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.sdv.datastore.DataStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DataStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStorage {

    companion object {
        private const val CURRENT_PARENT_KEY = "current_parent_key"
        private const val DEFAULT_CURRENT_PARENT = 0L
        private val currentParentKey = longPreferencesKey(CURRENT_PARENT_KEY)
    }

    override val currentParent: Flow<Long>
        get() = dataStore.data.map { it[currentParentKey] ?: DEFAULT_CURRENT_PARENT }

    override suspend fun setCurrentParent(currentParent: Long) {
        dataStore.edit { it[currentParentKey] = currentParent }
    }
}