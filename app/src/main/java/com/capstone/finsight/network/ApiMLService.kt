package com.capstone.finsight.network

import com.capstone.finsight.dataclass.ForecastingResponse
import com.capstone.finsight.dataclass.RecommendResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiMLService {
    @POST("riskprofile")
    suspend fun riskProfile(
        @Body profile : Map<String,String>
    ) : RecommendResponse

    @POST("predict")
    suspend fun getForecast(
        @Body stocks : Forecast
    ): ForecastingResponse
}

data class Forecast(
    val stock : String,
    val steps : Int
)