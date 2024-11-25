package com.capstone.finsight.data

import com.capstone.finsight.network.ApiService

class SettingRepo(private val apiService: ApiService, private val settingPref: SettingPref) {

    suspend fun login(){}
    suspend fun register(){}

    suspend fun checkUser(){}
    companion object {
        @Volatile
        private var instance: SettingRepo? = null
        fun getInstance(apiService: ApiService, settingPref: SettingPref
        ): SettingRepo =
            instance ?: synchronized(this) {
                instance ?: SettingRepo(apiService, settingPref)
            }.also { instance = it }
    }

}