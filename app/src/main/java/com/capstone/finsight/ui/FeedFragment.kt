package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.FeedAdapter
import com.capstone.finsight.adapter.PostAdapter
import com.capstone.finsight.adapter.SmallAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentFeedBinding
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class FeedFragment : Fragment() {
    private lateinit var binding : FragmentFeedBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(requireActivity())
    }

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
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcFeed.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcFeed.setHasFixedSize(true)

        lifecycleScope.launch{
            val uid = settingVM.getUser()
            postVM.post.observe(requireActivity()){
                when(it){
                    is Result.Error -> {

                    }
                    Result.Loading -> {

                    }
                    is Result.Success -> {
                        val adapter = PostAdapter(it.data)
                        binding.rcFeed.adapter = adapter
                        adapter.setOnProfileClickCallback(object : PostAdapter.OnItemClickListener{
                            override fun onItemClick(postItem: PostsItem) {
                                val bundle = Bundle()
                                bundle.putString(uid, postItem.authorUid)
                                findNavController().navigate(R.id.action_itemFeed_to_itemProfile, bundle)
                            }
                        })
                        adapter.setOnCommentClickCallback(object : PostAdapter.OnItemClickListener{
                            override fun onItemClick(postItem: PostsItem) {
                                val bundle = Bundle()
                                bundle.putParcelable("PostItem", postItem)
                                findNavController().navigate(R.id.action_itemFeed_to_detailPostFragment, bundle)
                            }
                        })
                        adapter.setOnLikeClickCallback(object : PostAdapter.OnItemClickListener{
                            override fun onItemClick(postItem: PostsItem) {
                                postVM.postLike(uid,postItem.id?: "")
                            }
                        })
                        adapter.setOnItemClickCallback(object : PostAdapter.OnItemClickListener{
                            override fun onItemClick(postItem: PostsItem) {
                                val bundle = Bundle()
                                bundle.putParcelable("PostItem", postItem)
                                findNavController().navigate(R.id.action_itemFeed_to_detailPostFragment, bundle)
                            }
                        })
                    }
                }
            }
        }

        val type = arguments?.getInt(Type, 0)
        lifecycleScope.launch {
            val uid = settingVM.getUser()
            if(type == 0){
                postVM.getAllPost(uid)
            }
            else{
                postVM.getFollowingPost(uid)
            }
        }
    }
    companion object {

        const val Type = "post_type"
        @JvmStatic
        fun newInstance() =
            FeedFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}