package com.capstone.finsight.data

import android.content.Context
import com.capstone.finsight.network.ApiConfig

object Injection {
    fun provideSetting(context : Context) : SettingRepo{
        val pref = SettingPref.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return SettingRepo(apiService,pref)
    }
}