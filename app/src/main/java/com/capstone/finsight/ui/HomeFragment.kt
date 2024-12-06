package com.capstone.finsight.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.adapter.NewsAdapter
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentHomeBinding
import com.capstone.finsight.network.Result

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(requireActivity())
    }
    private val postVM by viewModels<PostViewModel> {
        PostVMF.getInstance(requireActivity())
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcHomeNews.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcHomeNews.setHasFixedSize(true)
        postVM.news.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    binding.rcHomeNews.adapter = NewsAdapter(it.data)
                }
            }
        }

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