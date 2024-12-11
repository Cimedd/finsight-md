package com.capstone.finsight.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.finsight.dataclass.ForecastingResponse
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.dataclass.Prediction
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class MLViewModel(private val repo: MLRepo) : ViewModel() {

    private val _plot = MutableLiveData<Result<ForecastingResponse>>()
    val plot: LiveData<Result<ForecastingResponse>> = _plot

    fun getRecommend(risk : String) = liveData {
        emit(Result.Loading)
        try {
            val response = repo.getRecommend(risk)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun getAll() = liveData {
        emit(Result.Loading)
        try {
            val response = repo.getAll()
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun getForecast(stock : String, steps : Int){
        viewModelScope.launch {
            _plot.value = Result.Loading
            try {
                val response = repo.getForecast(stock,steps)
                if(response.prediction != null){
                    _plot.value = Result.Success(response)
                }
                else{
                    _plot.value = Result.Error(response.status?: "Unknown error")
                }

            } catch (e: Exception) {
                _plot.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }
}