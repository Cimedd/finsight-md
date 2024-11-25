package com.capstone.finsight.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.adapter.NewsAdapter
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcHomeNews.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcHomeNews.setHasFixedSize(true)
        binding.rcHomeNews.adapter = NewsAdapter()
        val smallAdapt = SmallAdapter()

        binding.rcSuggestion.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcSuggestion.setHasFixedSize(true)
        binding.rcSuggestion.adapter = smallAdapt

        smallAdapt.setOnItemClickCallback(object : SmallAdapter.OnItemClickListener{
            override fun onItemClick() {
                val intent = Intent(requireActivity(), ForecastActivity::class.java)
                requireActivity().startActivity(intent)
            }
        })

        binding.btnGoals.setOnClickListener{
            val intent = Intent(requireActivity(), RiskActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}