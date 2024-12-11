package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.finsight.R
import com.capstone.finsight.databinding.FragmentDetailNewsBinding
import com.capstone.finsight.dataclass.NewsItem
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.utils.TextFormatter

class DetailNewsFragment : Fragment() {
    private lateinit var binding : FragmentDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val news: NewsItem? = arguments?.getParcelable("NEWS")
        binding.txtDetailNewsTitl.text = news?.title
        binding.txtNewsAuthor.text = news?.publisherAuthor
        binding.txtDateNews.text = news?.dateText
        val profile = if (news?.imgUrl.isNullOrBlank()) R.drawable.example1 else news?.imgUrl
        binding.ttxtContentNews.text = TextFormatter.splitIntoParagraphs(news?.content ?: "")
        Glide.with(requireActivity())
            .load(profile)
            .transform(RoundedCorners(20))
            .into(binding.imgNews)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailNewsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}