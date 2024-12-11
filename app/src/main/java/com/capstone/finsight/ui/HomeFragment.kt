package com.capstone.finsight.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.NewsAdapter
import com.capstone.finsight.adapter.ReccomAdapter
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.adapter.StockAdapter
import com.capstone.finsight.data.MLVMF
import com.capstone.finsight.data.MLViewModel
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentHomeBinding
import com.capstone.finsight.dataclass.NewsItem
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(requireActivity())
    }
    private val postVM by viewModels<PostViewModel> {
        PostVMF.getInstance(requireActivity())
    }

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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcHomeNews.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcHomeNews.setHasFixedSize(true)

        binding.rcSuggestion.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcSuggestion.setHasFixedSize(true)

        binding.rcMarket.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcMarket.setHasFixedSize(true)
        postVM.news.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    val newsAdapter = NewsAdapter(it.data)
                    binding.rcHomeNews.adapter = newsAdapter
                    newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickListener{
                        override fun onItemClick(news: NewsItem) {
                            val bundle = Bundle()
                            bundle.putParcelable("NEWS", news)
                            findNavController().navigate(R.id.action_itemHome_to_detailNewsFragment, bundle)
                        }
                    })
                }
            }
        }
        postVM.getNews(TextFormatter.getTodayDate())

        MLVM.getRecommend("Moderate").observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    val smallAdapt = ReccomAdapter(it.data.recommendations!!)
                    binding.rcSuggestion.adapter = smallAdapt
                    smallAdapt.setOnItemClickCallback(object : ReccomAdapter.OnItemClickListener{
                        override fun onItemClick(stock: String) {
                            forecast(stock)
                        }
                    })
                }
            }
        }

        MLVM.getAll().observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> { Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()}
                Result.Loading -> { }
                is Result.Success -> {
                    val adapter = StockAdapter(it.data.recommendations!!)
                    binding.rcMarket.adapter = adapter
                    adapter.setOnItemClickCallback(object : StockAdapter.OnItemClickListener{
                        override fun onItemClick(stock: String) {
                            forecast(stock)
                        }
                    })
                }
            }
        }
    }

    private fun forecast(stock:String){
        val intent = Intent(requireActivity(), ForecastActivity::class.java)
        intent.putExtra("STOCK", stock )
        requireActivity().startActivity(intent)
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