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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.finsight.R
import com.capstone.finsight.adapter.HomeNewsAdapter
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
import com.capstone.finsight.dataclass.Recommendation
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter
import kotlinx.coroutines.launch

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
    private lateinit var stockAdapter : StockAdapter

    private var stocksData : List<Recommendation> = listOf()
    private var stockAll = false
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

        binding.rcSuggestion.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcSuggestion.setHasFixedSize(true)

        binding.rcMarket.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcMarket.setHasFixedSize(true)
        postVM.news.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    val newsAdapter = HomeNewsAdapter(it.data)
                    binding.rcHomeNews.adapter = newsAdapter
                    newsAdapter.setOnItemClickCallback(object : HomeNewsAdapter.OnItemClickListener{
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

        lifecycleScope.launch {
            val risk = settingVM.getRisk()
            MLVM.getRecommend(risk).observe(viewLifecycleOwner){
                when(it){
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        val smallAdapt = ReccomAdapter(it.data.recommendations!!)
                        binding.rcSuggestion.adapter = smallAdapt
                        smallAdapt.setOnItemClickCallback(object : ReccomAdapter.OnItemClickListener{
                            override fun onItemClick(stock: String, desc : String) {
                                forecast(stock, desc)
                            }
                        })
                    }
                }
            }
        }

        MLVM.getAll().observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> { Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()}
                Result.Loading -> {}
                is Result.Success -> {
                    stocksData = it.data.recommendations!!
                    stockAdapter = StockAdapter(stocksData.take(3))
                    binding.rcMarket.adapter = stockAdapter
                    stockAdapter.setOnItemClickCallback(object : StockAdapter.OnItemClickListener{
                        override fun onItemClick(stock: String, desc : String) {
                            forecast(stock, desc)
                        }
                    })
                }
            }
        }

        binding.txtNewsAll.setOnClickListener {
            findNavController().navigate(
                R.id.action_itemHome_to_itemInsight,  // Your navigation action ID
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.itemHome, true)  // Clears 'itemHome' from the back stack
                    .build()
            )
        }

        Glide.with(this)
            .load(R.drawable.example1)
            .into(binding.imgUser)

        binding.cardView3.setOnClickListener{
            findNavController().navigate(R.id.action_itemHome_to_itemProfile)
        }

        binding.txtStocksAll.setOnClickListener {
            if(!stockAll){
                binding.txtStocksAll.text = "See Less"
                stockAdapter = StockAdapter(stocksData)
                binding.rcMarket.adapter = stockAdapter
                stockAll = true
            }
            else{
                binding.txtStocksAll.text = "See More"
                stockAdapter = StockAdapter(stocksData.take(3))
                binding.rcMarket.adapter = stockAdapter
                stockAll = false
            }
        }
    }

    private fun forecast(stock:String, desc : String){
        val bundle = Bundle()
        bundle.putString("STOCK", stock)
        bundle.putString("DESC", stock)
        findNavController().navigate(R.id.action_itemHome_to_forecastFragment, bundle)
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