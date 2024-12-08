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

class ForecastActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForecastBinding

    private var percentage = 0f
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
        val stock = intent.getStringExtra("STOCK")
        Log.d("stock", stock.toString())

        if (stock != null) {
            MLVM.getForecast("ADRO.JK", 1)
            binding.textView14.text = stock
        }

        MLVM.plot.observe(this){
            when(it){
                is Result.Error -> {
                    binding.txtDataStatus.text = "Failled to get data"
                    binding.progressBar3.visibility = View.GONE
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> {
                    binding.txtDataStatus.text = "Fetching Stocks Data"
                    binding.chart2.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar3.visibility = View.GONE
                    binding.txtDataStatus.visibility = View.GONE
                    binding.chart2.visibility = View.VISIBLE
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
                        // Customization options
                        color = Color.BLUE
                        setCircleColor(Color.RED)
                        lineWidth = 1f
                        circleRadius = 1f
                        setDrawCircleHole(false)
                        valueTextSize = 6f
                        setDrawFilled(true)
                        fillColor = Color.LTGRAY
                    }

                    val lineData = LineData(dataSet)

                    // Customize chart
                    lineChart.apply {
                        // Disable description
                        description.isEnabled = false

                        // Enable touch gestures
                        setTouchEnabled(true)

                        // Enable scaling and dragging
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)

                        // X-axis configuration
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            axisLineColor = Color.BLACK
                            textColor = Color.BLACK

                            valueFormatter = IndexAxisValueFormatter(parsedDate)
                        }

                        // Y-axis configuration
                        axisLeft.apply {
                            setDrawGridLines(true)
                            gridColor = Color.LTGRAY
                            axisLineColor = Color.BLACK
                            textColor = Color.BLACK
                        }

                        // Hide right Y-axis
                        axisRight.isEnabled = false

                        // Set the data
                        data = lineData

                        // Animate the chart (optional)
                        animateX(1000)

                        // Refresh the chart
                        invalidate()
                    }
                }
            }
        }
    }

    private fun setChart(prediction: Prediction){
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()

    }
}