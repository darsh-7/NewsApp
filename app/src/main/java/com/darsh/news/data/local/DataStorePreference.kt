package com.darsh.news.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import com.darsh.news.data.Constant.PREFERENCES_COUNTRY_NAME
import com.darsh.news.data.Constant.PREFERENCES_NAME

import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStorePreference (
     private val context: Context
) {
    companion object {
        private object PreferenceKeys {
            val countryName = stringPreferencesKey(PREFERENCES_COUNTRY_NAME)
        }

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PREFERENCES_NAME
        )
    }

    suspend fun saveIsFirstTimeEnterApp(countryName: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferenceKeys.countryName] = countryName
        }
    }

     fun readIsFirstTimeEnterApp(): String {
        return runBlocking {
            context.dataStore.data.first()[PreferenceKeys.countryName] ?: ""
        }

    }
}