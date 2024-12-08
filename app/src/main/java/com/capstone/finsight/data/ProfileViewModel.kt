package com.capstone.finsight.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: ProfileRepo) : ViewModel(){

    fun getProfile(uid : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.getProfile(uid)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun getProfileOther(uid : String, followUid : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.getProfileOther(uid, followUid)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun followUser(uid : String, followUid : String){
        viewModelScope.launch {
            repo.followUser(uid, followUid)
        }
    }


}