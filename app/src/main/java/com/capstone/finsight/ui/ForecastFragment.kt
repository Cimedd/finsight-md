package com.capstone.finsight.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.capstone.finsight.R
import com.capstone.finsight.data.MLVMF
import com.capstone.finsight.data.MLViewModel
import com.capstone.finsight.databinding.FragmentForecastBinding
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlin.math.pow

class ForecastFragment : Fragment() {
    private lateinit var binding : FragmentForecastBinding

    private var percentage = 0f
    private var stock = ""
    private var step = 1
    private val MLVM by viewModels<MLViewModel> {
        MLVMF.getInstance(requireActivity())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chart2.visibility = View.GONE
        stock =  arguments?.getString("STOCK") ?: ""
        val stockDesc =  arguments?.getString("DESC") ?: ""
        binding.txtDesc.text = stockDesc
        setChoose()
        MLVM.getForecast(stock, step)
        binding.textView14.text = stock

        MLVM.plot.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {
                    binding.txtDataStatus.text = "Failled to get data"
                    binding.progressBar3.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(requireActivity(), "SUCCESS", Toast.LENGTH_SHORT).show()
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
                        setCircleColor(ContextCompat.getColor(requireActivity(), R.color.blue_main3))
                        lineWidth = 1f
                        circleRadius = 1f
                        setDrawCircleHole(false)
                        valueTextSize = 6f
                        setDrawFilled(true)
                        fillColor = ContextCompat.getColor(requireActivity(), R.color.blue_main3)
                        valueTextColor = ContextCompat.getColor(requireActivity(), R.color.BlackWhite)
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
                            axisLineColor = ContextCompat.getColor(requireActivity(), R.color.BlackWhite)
                            textColor = ContextCompat.getColor(requireActivity(), R.color.BlackWhite)

                            valueFormatter = IndexAxisValueFormatter(parsedDate)
                        }

                        axisLeft.apply {
                            setDrawGridLines(true)
                            gridColor = Color.LTGRAY
                            axisLineColor = ContextCompat.getColor(requireActivity(), R.color.BlackWhite)
                            textColor = ContextCompat.getColor(requireActivity(), R.color.BlackWhite)
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
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
            }
            else{
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
                setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.WhiteBlack))
            }
        }

        binding.btn2.apply {
            if(step == 2){
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
            }
            else{
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
                setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.WhiteBlack))
            }
        }

        binding.btn3.apply {
            if(step == 3){
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
            }
            else{
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
                setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.WhiteBlack))
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
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
            }
            binding.txtProfit.apply {
                text = "$formatDif"
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
            }
        }
        else{
            binding.txtReturn.apply {
                text = TextFormatter.formatToRupiah(calc)
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.green))
            }
            binding.txtProfit.apply {
                text = "+$formatDif"
                setTextColor(ContextCompat.getColor(requireActivity(), R.color.green))
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForecastFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}