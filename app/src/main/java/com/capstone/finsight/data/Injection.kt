package com.capstone.finsight.data

import android.content.Context
import com.capstone.finsight.network.ApiConfig

object Injection {
    fun provideSetting(context : Context) : SettingRepo{
        val pref = SettingPref.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return SettingRepo(apiService,pref)
    }

    fun providePost(context : Context) : PostRepo{
        val apiService = ApiConfig.getApiService()
        return PostRepo(apiService)
    }

    fun provideML(context : Context) : MLRepo{
        val apiService = ApiConfig.getApiMLService()
        return MLRepo(apiService)
    }

    fun provideProfile(context : Context) : ProfileRepo{
        val apiService = ApiConfig.getApiService()
        return ProfileRepo(apiService)
    }
}