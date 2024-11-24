package com.capstone.finsight.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPref private constructor(private val dataStore: DataStore<Preferences>) {
    private val userId = stringPreferencesKey("id")
    private val name = stringPreferencesKey("name")
    private val theme = booleanPreferencesKey("theme")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[theme] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[theme] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPref? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPref {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}