package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class GenericResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class FAQ(val question : String, val answer : String)
data class ResponseLogin(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("username")
	val user: String? = null,

	@field:SerializedName("token")
	val token: String? = null
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