package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class RecommendResponse(

	@field:SerializedName("recommendations")
	val recommendations: List<Recommendation>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Recommendation(
	@field:SerializedName("current_price")
	val price : Int,
	@field:SerializedName("image_url")
	val imageUrl : String,
	@field:SerializedName("ticker")
	val ticker : String
)
