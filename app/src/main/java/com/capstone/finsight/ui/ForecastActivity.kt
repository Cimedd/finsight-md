package com.capstone.finsight.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.capstone.finsight.R
import com.capstone.finsight.data.MLVMF
import com.capstone.finsight.data.MLViewModel
import com.capstone.finsight.databinding.ActivityForecastBinding
import com.capstone.finsight.dataclass.Prediction
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.math.pow

class ForecastActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForecastBinding

    private var percentage = 0f
    private var stock = ""
    private var step = 1
    private val MLVM by viewModels<MLViewModel> {
        MLVMF.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.chart2.visibility = View.GONE
        stock = intent.getStringExtra("STOCK").toString()
        Log.d("stock", stock)
        setChoose()
        MLVM.getForecast(stock, step)
        binding.textView14.text = stock

        MLVM.plot.observe(this){
            when(it){
                is Result.Error -> {
                    binding.txtDataStatus.text = "Failled to get data"
                    binding.progressBar3.visibility = View.GONE
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    binding.btn1.isEnabled = true
                    binding.btn2.isEnabled = true
                    binding.btn3.isEnabled = true
                }
                Result.Loading -> {
                    binding.txtDataStatus.text = "Fetching Stocks Data"
                    binding.btn1.isEnabled = false
                    binding.btn2.isEnabled = false
                    binding.btn3.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar3.visibility = View.GONE
                    binding.txtDataStatus.visibility = View.GONE
                    binding.chart2.visibility = View.VISIBLE
                    binding.btn1.isEnabled = true
                    binding.btn2.isEnabled = true
                    binding.btn3.isEnabled = true
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
                    val lineChart: LineChart = binding.chart2
                    val prediction = it.data.prediction
                    percentage = it.data.change!!
                    val parsedDate : MutableList<String> = mutableListOf()
                    val entries = prediction?.times?.mapIndexed { index, time ->
                        parsedDate.add(TextFormatter.parseTime(time))
                        Entry(index.toFloat(), prediction.prices!![index])
                    }

                    val dataSet = LineDataSet(entries, "Price Prediction").apply {
                        color = Color.BLUE
                        setCircleColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main3))
                        lineWidth = 1f
                        circleRadius = 1f
                        setDrawCircleHole(false)
                        valueTextSize = 6f
                        setDrawFilled(true)
                        fillColor = ContextCompat.getColor(this@ForecastActivity, R.color.blue_main3)
                        valueTextColor = ContextCompat.getColor(this@ForecastActivity, R.color.BlackWhite)
                    }

                    val lineData = LineData(dataSet)
                    lineChart.apply {
                        description.isEnabled = false

                        setTouchEnabled(true)

                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            axisLineColor = ContextCompat.getColor(this@ForecastActivity, R.color.BlackWhite)
                            textColor = ContextCompat.getColor(this@ForecastActivity, R.color.BlackWhite)

                            valueFormatter = IndexAxisValueFormatter(parsedDate)
                        }

                        axisLeft.apply {
                            setDrawGridLines(true)
                            gridColor = Color.LTGRAY
                            axisLineColor = ContextCompat.getColor(this@ForecastActivity, R.color.BlackWhite)
                            textColor = ContextCompat.getColor(this@ForecastActivity, R.color.BlackWhite)
                        }

                        axisRight.isEnabled = false
                        data = lineData

                        animateX(1000)
                        invalidate()
                    }
                }
            }
        }

        binding.btn1.setOnClickListener {
            step = 1
            reForecast()
        }

        binding.btn2.setOnClickListener {
            step = 2
            reForecast()
        }

        binding.btn3.setOnClickListener {
            step = 3
            reForecast()
        }

        binding.btnCalculate.setOnClickListener {
            predictStock()
        }
    }

    private fun reForecast(){
        setChoose()
        MLVM.getForecast(stock,step)
        binding.progressBar3.visibility = View.VISIBLE
        binding.txtDataStatus.visibility = View.VISIBLE
        binding.btn1.isEnabled = false
        binding.btn2.isEnabled = false
        binding.btn3.isEnabled = false
    }
    private fun setChoose(){
       binding.btn1.apply {
           if(step == 1){
               setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.white))
               setBackgroundColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main_2))
           }
           else{
               setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main_2))
               setBackgroundColor(ContextCompat.getColor(this@ForecastActivity, R.color.WhiteBlack))
           }
       }

        binding.btn2.apply {
            if(step == 2){
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.white))
                setBackgroundColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main_2))
            }
            else{
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main_2))
                setBackgroundColor(ContextCompat.getColor(this@ForecastActivity, R.color.WhiteBlack))
            }
        }

        binding.btn3.apply {
            if(step == 3){
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.white))
                setBackgroundColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main_2))
            }
            else{
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.blue_main_2))
                setBackgroundColor(ContextCompat.getColor(this@ForecastActivity, R.color.WhiteBlack))
            }
        }
    }
    private fun predictStock(){
        val initialAmount = binding.txtInvest.text.toString().toDouble()
        val calc = initialAmount * (1 + (percentage / 100).toDouble()).pow(1 / step.toDouble())
        val difference = calc - initialAmount
        Log.d("CALC", difference.toString())
        val formatDif = TextFormatter.formatToRupiah(difference)
        if(difference < 0){
            binding.txtReturn.apply {
                text = TextFormatter.formatToRupiah(calc)
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.red))
            }
            binding.txtProfit.apply {
                text = "$formatDif"
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.red))
            }
        }
        else{
            binding.txtReturn.apply {
                text = TextFormatter.formatToRupiah(calc)
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.green))
            }
            binding.txtProfit.apply {
                text = "+$formatDif"
                setTextColor(ContextCompat.getColor(this@ForecastActivity, R.color.green))
            }
        }
    }
}