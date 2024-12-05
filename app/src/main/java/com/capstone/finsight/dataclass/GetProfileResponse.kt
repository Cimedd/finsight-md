package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: String? = null
)
data class User(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@field:SerializedName("profileRisk")
	val profileRisk: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
