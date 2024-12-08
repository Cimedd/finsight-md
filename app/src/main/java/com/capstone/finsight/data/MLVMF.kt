package com.capstone.finsight.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MLVMF(private val mlrepo: MLRepo ): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MLViewModel::class.java)) {
            return MLViewModel(mlrepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: MLVMF? = null
        fun getInstance(context: Context): MLVMF =
            instance ?: synchronized(this) {
                instance ?: MLVMF(Injection.provideML(context))
            }.also { instance = it }
    }
}