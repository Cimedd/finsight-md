package com.capstone.finsight.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.finsight.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPref private constructor(private val dataStore: DataStore<Preferences>) {
    private val userId = stringPreferencesKey("id")
    private val username = stringPreferencesKey("username")
    private val userImage = stringPreferencesKey("imagePath")
    private val theme = booleanPreferencesKey("theme")
    private val profileRisk = stringPreferencesKey("risk")
    private val notification = booleanPreferencesKey("notification")

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

    fun getNotificationSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[notification] ?: false
        }
    }

    suspend fun saveNotificationSetting(isTrue: Boolean) {
        dataStore.edit { preferences ->
            preferences[notification] = isTrue
            Log.d("NOTIFs",preferences[notification].toString() )
        }
    }

    suspend fun getRiskSetting() : Flow<String>{
        return dataStore.data.map { preferences ->
            preferences[profileRisk] ?: ""
        }
    }

    suspend fun saveRiskSetting(risks: String) {
        dataStore.edit { preferences ->
            preferences[profileRisk] = risks
        }
    }

    fun getUserProfileSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[userImage] ?: ""
        }
    }

    suspend fun loggedIn(user : String, name : String, url: String){
        dataStore.edit { preferences ->
            preferences[userId] = user
            preferences[userImage] = url
            preferences[username] = name
        }
    }

    suspend fun loggedOut(){
        dataStore.edit { preferences ->
            preferences[userId] = ""
            preferences[username] = ""
            preferences[profileRisk] = ""
            preferences[userImage] = ""
        }
    }

    fun checkUser():Flow<String>{
        return dataStore.data.map { preferences ->
            preferences[userId] ?: ""
        }
    }

    fun getName():Flow<String>{
        return dataStore.data.map { preferences ->
            preferences[username] ?: ""
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