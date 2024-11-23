package com.capstone.finsight.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingVMF(private val setting : SettingRepo): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(setting) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SettingVMF? = null
        fun getInstance(context: Context): SettingVMF =
            instance ?: synchronized(this) {
                instance ?: SettingVMF(SettingRepo())
            }.also { instance = it }
    }
}