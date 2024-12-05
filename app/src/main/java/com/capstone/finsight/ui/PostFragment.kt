package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.finsight.R
import com.capstone.finsight.adapter.FeedAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.databinding.FragmentPostBinding
import com.google.android.material.tabs.TabLayoutMediator

class PostFragment : Fragment() {
    private lateinit var binding : FragmentPostBinding
    private val postVM by viewModels<PostViewModel>{
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
    ): View{
        binding = FragmentPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewpager = binding.viewPager2
        viewpager.adapter = FeedAdapter(requireActivity() as AppCompatActivity)
        TabLayoutMediator(binding.tabLayout, viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "All Posts"
                1 -> tab.text = "Following"
                else -> tab.text = "Unknown"
            }
        }.attach()

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_itemFeed_to_addPostFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                   
                }
            }
    }
}