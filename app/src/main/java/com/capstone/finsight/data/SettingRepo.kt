package com.capstone.finsight.data

import com.capstone.finsight.dataclass.GenericResponse
import com.capstone.finsight.dataclass.RegisterRequest
import com.capstone.finsight.dataclass.ResponseLogin
import com.capstone.finsight.dataclass.ResponseRegister
import com.capstone.finsight.network.ApiService
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

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

    suspend fun getRisk() : String{
        return settingPref.getRiskSetting().firstOrNull() ?: ""
    }

    suspend fun logout(){
        settingPref.loggedOut()
    }
    suspend fun setProfileRisk(uid: String, risk : String): GenericResponse {
        settingPref.saveRiskSetting(risk)
        val body = mapOf("uid" to uid, "profileRisk" to risk)
        return apiService.updateUser(body)
    }

    suspend fun setImage(file : File) : GenericResponse{
        val uid = settingPref.checkUser().firstOrNull() ?: ""
        val bodyUid = uid.toRequestBody("text/plain".toMediaType())
        val imageBody = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
                "image",
                file.name,
                imageBody
            )
        return apiService.updatePhoto(bodyUid, multipartBody)
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