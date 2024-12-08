package com.capstone.finsight.data

import com.capstone.finsight.dataclass.ForecastingResponse
import com.capstone.finsight.dataclass.RecommendResponse
import com.capstone.finsight.network.ApiMLService
import com.capstone.finsight.network.Forecast

class MLRepo(private val api : ApiMLService) {

    suspend fun getRecommend(risk : String) : RecommendResponse{
        val body = mapOf("riskProfile" to risk)
        return api.riskProfile(body)
    }

    suspend fun getForecast(stock : String, step : Int) : ForecastingResponse{
        val body = Forecast(stock,step)
        return api.getForecast(body)
    }

}