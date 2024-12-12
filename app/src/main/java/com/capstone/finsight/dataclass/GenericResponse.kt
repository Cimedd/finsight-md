package com.capstone.finsight.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GenericResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class FAQ(val question : String, val answer : String) :Parcelable
data class ResponseLogin(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("username")
	val user: String? = null,

	@field:SerializedName("profileUrl")
	val profileUrl: String? = null
)

data class ResponseRegister(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("uid")
	val user: String? = null,
)

data class RegisterRequest(
	val email: String,
	val username: String,
	val password: String
)