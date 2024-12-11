package com.capstone.finsight.data

import com.capstone.finsight.dataclass.ChatResponse
import com.capstone.finsight.dataclass.GetProfileResponse
import com.capstone.finsight.dataclass.GenericResponse
import com.capstone.finsight.network.ApiService
import kotlinx.coroutines.flow.Flow


class ProfileRepo(private val api : ApiService) {
    suspend fun getProfile(uid: String): GetProfileResponse {
        return api.getProfile(uid)
    }
    suspend fun followUser(uid: String, followUid : String): GenericResponse {
        val body = mapOf("uid" to uid, "followingUid" to followUid)
        return api.followUser(body)
    }

    suspend fun getProfileOther(uid: String, followUid : String) : GetProfileResponse{
        return api.getProfileOther(uid, followUid)
    }

    suspend fun sendChat(sender : String, receiver : String, message : String):GenericResponse{
        val body = mapOf("uidSender" to sender, "uidReceiver" to receiver, "message" to message)
        return api.postChat(body)
    }

    suspend fun getChat(sender : String, receiver : String): ChatResponse{
        return api.getChat(sender,receiver)
    }

}