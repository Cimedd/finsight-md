package com.capstone.finsight.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.finsight.network.Result

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

    fun followUser(uid : String, followUid : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.followUser(uid, followUid)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun setProfileRisk(uid : String, risk : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.setProfileRisk(uid,risk)
            if(response.status != "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }
}