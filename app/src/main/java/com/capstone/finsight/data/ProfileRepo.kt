package com.capstone.finsight.data

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

}