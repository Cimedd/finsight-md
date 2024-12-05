package com.capstone.finsight.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileVMF(private val profileRepo: ProfileRepo ): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(profileRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ProfileVMF? = null
        fun getInstance(context: Context): ProfileVMF =
            instance ?: synchronized(this) {
                instance ?: ProfileVMF(Injection.provideProfile(context))
            }.also { instance = it }
    }
}