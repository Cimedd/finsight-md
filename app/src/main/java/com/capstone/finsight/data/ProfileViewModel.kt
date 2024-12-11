package com.capstone.finsight.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.finsight.dataclass.ChatsItem
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: ProfileRepo) : ViewModel(){

    private val _chat = MutableLiveData<Result<List<ChatsItem>>>()
    val chat: LiveData<Result<List<ChatsItem>>> = _chat
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

    fun getChat(sender : String, receiver : String){
        viewModelScope.launch {
            _chat.value = Result.Loading
            try {
                val response = repo.getChat(sender,receiver)
                val posts = response.chats?.filterNotNull() ?: emptyList()
                if(response.status == "success"){
                    _chat.value = Result.Success(posts)
                }
            }
            catch (e : Exception){
                _chat.value = Result.Error( e.message ?: "Unknown error")
            }
        }

    }

    fun postChat(sender : String, receiver : String, message : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.sendChat(sender,receiver, message)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }


}