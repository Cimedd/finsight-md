package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var insightMore = false
    private var listFAQ : MutableList<FAQ> = mutableListOf()
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
                is Result.Error -> {
                    Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
                Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
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


        val question = resources.getStringArray(R.array.faq_questions)
        val ans = resources.getStringArray(R.array.faq_answers)
        binding.rcFaq.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcFaq.setHasFixedSize(true)

        if (listFAQ.isEmpty()){
            question.forEachIndexed { index, s ->
                listFAQ.add(FAQ(s, ans[index]))
            }
        }
        seeInsight()

        binding.txtMoreIIns.setOnClickListener{
           seeInsight()
        }
    }

    private fun seeInsight(){
        val smallAdapt: SmallAdapter
        if(!insightMore){
            smallAdapt = SmallAdapter(listFAQ.take(4))
            binding.rcFaq.adapter = smallAdapt
            insightMore = true
            binding.txtMoreIIns.text = getString(R.string.see_more)
        }
        else{
            smallAdapt = SmallAdapter(listFAQ)
            binding.rcFaq.adapter = smallAdapt
            insightMore = false
            binding.txtMoreIIns.text = getString(R.string.see_less)
        }
        smallAdapt.setOnItemClickCallback(object : SmallAdapter.OnItemClickListener{
            override fun onItemClick(faq : FAQ) {
                val educateFrag = EducateFragment.newInstance(faq)
                educateFrag.show(parentFragmentManager,"Educate")
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