package com.capstone.finsight.data

import com.capstone.finsight.dataclass.RegisterRequest
import com.capstone.finsight.dataclass.ResponseLogin
import com.capstone.finsight.dataclass.ResponseRegister
import com.capstone.finsight.network.ApiService
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response

class SettingRepo(private val apiService: ApiService, private val settingPref: SettingPref) {

    suspend fun login(email: String, password: String): Response<ResponseLogin> {
        val body = mapOf("email" to email, "password" to password)
        return apiService.login(body)
    }

    suspend fun saveToDataStore(uid: String, name: String, token: String){
        settingPref.loggedIn(uid, name, token)
    }
    suspend fun register(email: String,username:String, password : String): ResponseRegister{
        val register = RegisterRequest(email,username,password)
        return  apiService.register(register)
    }
    suspend fun checkUser(): Boolean {
        val token = settingPref.checkUser().firstOrNull()
        return token != ""
    }

    suspend fun getUser(): String {
        return settingPref.checkUser().firstOrNull() ?: ""
    }

    suspend fun getUserLocal(){

    }
    companion object {
        @Volatile
        private var instance: SettingRepo? = null
        fun getInstance(apiService: ApiService, settingPref: SettingPref
        ): SettingRepo =
            instance ?: synchronized(this) {
                instance ?: SettingRepo(apiService, settingPref)
            }.also { instance = it }
    }

}