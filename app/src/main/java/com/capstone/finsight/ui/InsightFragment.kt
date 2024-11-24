package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.NewsAdapter
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.databinding.ActivityMainBinding
import com.capstone.finsight.databinding.FragmentInsightBinding

class InsightFragment : Fragment() {
    private lateinit var binding : FragmentInsightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcNews.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcNews.setHasFixedSize(true)
        binding.rcNews.adapter = NewsAdapter()
        val smallAdapt = SmallAdapter()

        binding.rcFaq.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcFaq.setHasFixedSize(true)
        binding.rcFaq.adapter = smallAdapt

        smallAdapt.setOnItemClickCallback(object : SmallAdapter.OnItemClickListener{
            override fun onItemClick() {
                EducateFragment().show(parentFragmentManager,"Educate")
            }
        })

    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsightFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}