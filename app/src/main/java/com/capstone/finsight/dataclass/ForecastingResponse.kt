package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class ForecastingResponse(

	@field:SerializedName("year_from")
	val yearFrom: String? = null,

	@field:SerializedName("prediction")
	val prediction: Prediction? = null,

	@field:SerializedName("year_to")
	val yearTo: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("percentage_change")
	val change: Float? = null
)

data class Prediction(

	@field:SerializedName("times")
	val times: List<String>? = null,

	@field:SerializedName("prices")
	val prices: List<Float>? = null
)
