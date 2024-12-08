package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.databinding.ActivityMainBinding
import com.capstone.finsight.databinding.FragmentPortofolioBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PortfolioFragment : Fragment() {
    private lateinit var binding : FragmentPortofolioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortofolioBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.rcPorto.layoutManager = LinearLayoutManager(requireActivity())
//        binding.rcPorto.setHasFixedSize(true)
//        binding.rcPorto.adapter = SmallAdapter()
//
//        val pieChart: PieChart = binding.chart2
//
//        // Create data entries for the pie chart
//        val pieEntries = listOf(
//            PieEntry(30f, "Category A"),
//            PieEntry(50f, "Category B"),
//            PieEntry(20f, "Category C")
//        )
//
//        // Create a PieDataSet
//        val pieDataSet = PieDataSet(pieEntries, "Category Breakdown")
//        pieDataSet.colors = listOf(
//            requireContext().getColor(R.color.purple_500),
//            requireContext().getColor(R.color.teal_200),
//            requireContext().getColor(R.color.white)
//        )
//        // Create PieData and set it to the PieChart
//        val pieData = PieData(pieDataSet)
//        pieChart.data = pieData
//
//        // Customize the chart
//        pieChart.animateY(1000)
//        pieChart.isDrawHoleEnabled = true
//        pieChart.holeRadius = 40f


    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PortfolioFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}