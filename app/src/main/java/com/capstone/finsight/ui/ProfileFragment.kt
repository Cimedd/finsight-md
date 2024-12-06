package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.PostAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.ProfileVMF
import com.capstone.finsight.data.ProfileViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentProfileBinding
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private val profileVM by viewModels<ProfileViewModel> {
        ProfileVMF.getInstance(requireActivity())
    }
    private val postVM by viewModels<PostViewModel> {
        PostVMF.getInstance(requireActivity())
    }
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(requireActivity())
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
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgUser.setImageResource(R.drawable.login_gradient)
        binding.rcProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcProfile.setHasFixedSize(true)

        lifecycleScope.launch {
            val uid = settingVM.getUser()
            profileVM.getProfile(uid).observe(requireActivity()){
                when(it){
                    is Result.Error -> {
                        binding.imgUser.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.login_gradient))
                        binding.txtProfileUname.text = "User not found"
                        binding.txtProfileRisk.text =  "-"
                        binding.txtFollowingCount.text = "0"
                        binding.txtFollowerCount.text = "0"
                    }
                    Result.Loading -> {
                        binding.imgUser.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.login_gradient))
                    }
                    is Result.Success ->{
                        binding.imgUser.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.login_gradient))
                        binding.txtProfileUname.text = it.data.user?.username ?: "User not found"
                        binding.txtProfileRisk.text = it.data.user?.profileRisk ?: "-"
                        binding.txtFollowingCount.text = TextFormatter.formatNumber(it.data.user?.followings ?: 0)
                        binding.txtFollowerCount.text = TextFormatter.formatNumber(it.data.user?.followers ?: 0)
                        if(it.data.post?.isNotEmpty() == true){
                            postVM.getUserPost(it.data.post)
                        }
                        else{
                            binding.txtPostResult.text = "User Hasn't Post Anything"
                        }
                    }
                }
            }

            binding.btnEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_itemProfile_to_itemSetting)
            }

            postVM.post.observe(requireActivity()){
                when(it){
                    is Result.Error -> {
                        binding.txtPostResult.text = "Failed to get Posts"
                    }
                    Result.Loading -> {
                        binding.txtPostResult.text = "Loading Posts"
                    }
                    is Result.Success ->{
                        binding.txtPostResult.visibility = View.GONE
                        val adapter = PostAdapter(it.data)
                        binding.rcProfile.adapter = adapter
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
                                Toast.makeText(requireActivity(), "LIKED", Toast.LENGTH_SHORT).show()
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
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}