package com.sdv.datastore.impl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@FlowPreview
@Module
@InstallIn(SingletonComponent::class)
internal class DataStoreModule {

    companion object {
        const val USER_PREFERENCE = "user_data_store_preference"
    }

    @Provides
    @Singleton
    fun providePreferenceDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        scope = CoroutineScope(Dispatchers.IO+ SupervisorJob()),
        produceFile = { context.preferencesDataStoreFile(USER_PREFERENCE) }
    )
}