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
    private val token = stringPreferencesKey("token")
    private val username = stringPreferencesKey("username")
    private val userImage = stringPreferencesKey("imagePath")
    private val theme = booleanPreferencesKey("theme")
    private val profileRisk = stringPreferencesKey("risk")

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

    suspend fun saveImageURL(url: String) {
        dataStore.edit { preferences ->
            preferences[userImage] = url
        }
    }


    suspend fun loggedIn(user : String, name : String, tokens: String){
        dataStore.edit { preferences ->
            preferences[userId] = user
            preferences[token] = tokens
            preferences[username] = name
            Log.d("logged in", name + "" + tokens)
        }
    }

    suspend fun loggedOut(){
        dataStore.edit { preferences ->
            preferences[userId] = ""
            preferences[token] = ""
            preferences[username] = ""
            preferences[profileRisk] = ""
        }
    }

    fun checkUser():Flow<String>{
        return dataStore.data.map { preferences ->
            preferences[userId] ?: ""
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