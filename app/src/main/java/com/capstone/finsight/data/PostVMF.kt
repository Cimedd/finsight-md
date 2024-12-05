package com.capstone.finsight.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostVMF(private val postRepo: PostRepo ): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(postRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: PostVMF? = null
        fun getInstance(context: Context): PostVMF =
            instance ?: synchronized(this) {
                instance ?: PostVMF(Injection.providePost(context))
            }.also { instance = it }
    }
}