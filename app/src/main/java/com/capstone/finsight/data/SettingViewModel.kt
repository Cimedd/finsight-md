package com.capstone.finsight.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch
import java.io.File

class SettingViewModel(private val repo : SettingRepo) : ViewModel() {
    fun login(username : String, password : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.login(username,password)
            if(response.isSuccessful){
                val body = response.body()
                if(body?.status == "success"){
                    repo.saveToDataStore(body.uid ?: "", body.user?:"", body.token?:"")
                    emit(Result.Success(response))
                }
                else{
                    emit(Result.Error( body?.message ?: "Unknown error"))
                }
            }else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                emit(Result.Error(errorMessage))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }
    fun register(email : String, username : String, password : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.register(email,username,password)
            if(response.status.toString() == "success"){
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
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun setImage(file : File) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.setImage(file)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }
    suspend fun checkUser(): Boolean{
        return repo.checkUser()
    }

    suspend fun getUser() : String{
        return repo.getUser()
    }

    suspend fun getRisk() : String{
        return repo.getRisk()
    }

    suspend fun logout(){
        repo.logout()
    }


}