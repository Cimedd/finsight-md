package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.NewsAdapter
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.databinding.ActivityMainBinding
import com.capstone.finsight.databinding.FragmentInsightBinding
import com.capstone.finsight.dataclass.FAQ
import com.capstone.finsight.dataclass.NewsItem
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter

class InsightFragment : Fragment() {
    private lateinit var binding : FragmentInsightBinding
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
        binding = FragmentInsightBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcNews.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcNews.setHasFixedSize(true)

        postVM.news.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    val newsAdapter = NewsAdapter(it.data)
                    binding.rcNews.adapter = newsAdapter
                    newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickListener{
                        override fun onItemClick(news: NewsItem) {
                            val bundle = Bundle()
                            bundle.putParcelable("NEWS", news)
                            findNavController().navigate(R.id.action_itemInsight_to_detailNewsFragment, bundle)
                        }
                    })
                }
            }
        }

        postVM.getNews(TextFormatter.getTodayDate())


        val question = resources.getStringArray(R.array.riskQuestion)
        val ans = resources.getStringArray(R.array.riskQuestion)
        var listFAQ : MutableList<FAQ> = mutableListOf()

        question.forEachIndexed { index, s ->
            listFAQ.add(FAQ(s, ans[index]))
        }

        val smallAdapt = SmallAdapter(listFAQ)

        binding.rcFaq.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcFaq.setHasFixedSize(true)
        binding.rcFaq.adapter = smallAdapt

        smallAdapt.setOnItemClickCallback(object : SmallAdapter.OnItemClickListener{
            override fun onItemClick() {
                val educateFrag = EducateFragment().apply {  }
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