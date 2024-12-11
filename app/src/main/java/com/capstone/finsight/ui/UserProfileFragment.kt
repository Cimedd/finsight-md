package com.capstone.finsight.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.finsight.R
import com.capstone.finsight.adapter.PostAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.ProfileVMF
import com.capstone.finsight.data.ProfileViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentUserProfileBinding
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter
import kotlinx.coroutines.launch

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding

    private val profileVM by viewModels<ProfileViewModel> {
        ProfileVMF.getInstance(requireActivity())
    }
    private val postVM by viewModels<PostViewModel> {
        PostVMF.getInstance(requireActivity())
    }
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(requireActivity())
    }

    private var receiveID = ""
    private var userID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveID = arguments?.getString("Profile") ?: ""
        Log.d("id", id.toString())
        binding.rcUserProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcUserProfile.setHasFixedSize(true)
        lifecycleScope.launch {
            userID = settingVM.getUser()
            if(userID == receiveID){
                disable()
            }
            profileVM.getProfileOther(userID, receiveID).observe(viewLifecycleOwner){
                when(it){
                    is Result.Error -> {
                        binding.imgUser.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.login_gradient))
                        binding.txtUserProfileUname.text = "User not found"
                        binding.txtUserProfileRisk.text =  "-"
                        binding.txtUserFollowingCount.text = "0"
                        binding.txtUserFollowerCount.text = "0"
                    }
                    Result.Loading -> {

                    }
                    is Result.Success -> {
                        val data = it.data
                        binding.txtUserProfileUname.text = data.user?.username ?: ""
                        binding.txtUserProfileRisk.text =  data.user?.profileRisk ?: "-"
                        binding.txtUserFollowingCount.text = TextFormatter.formatNumber(data.user?.followings ?: 0)
                        binding.txtUserFollowerCount.text = TextFormatter.formatNumber(data.user?.followers ?: 0)

                        val profile = if (data.user?.profileURL.isNullOrBlank()) R.drawable.example1 else data.user?.profileURL
                        Glide.with(requireActivity())
                            .load(profile)
                            .into(binding.imgUser)

                        if(data.isFollow){
                            binding.btnFollow.apply {
                                text = "Followed"
                                setBackgroundColor(ContextCompat.getColor(context, R.color.blue_main_1))
                            }
                        }

                        binding.btnFollow.setOnClickListener {
                            data.isFollow = !data.isFollow
                            profileVM.followUser(userID, receiveID)
                            if(data.isFollow){
                                binding.txtUserFollowerCount.text = TextFormatter.formatNumber(binding.txtUserFollowerCount.text.toString().toInt() + 1)
                                binding.btnFollow.text = "Followed"
                                binding.btnFollow.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_1))
                            }
                            else{
                                binding.txtUserFollowerCount.text = TextFormatter.formatNumber(binding.txtUserFollowerCount.text.toString().toInt() - 1)
                                binding.btnFollow.text = "Follow"
                                binding.btnFollow.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.blue_main_2))
                            }
                        }
                        postVM.getUserPost(it.data.post!!)
                    }
                }
            }
        }

        binding.btnChat.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("RECEIVED", receiveID)
            bundle.putString("USERID", userID)
            findNavController().navigate(R.id.action_userProfileFragment_to_chatFragment, bundle)
        }

        postVM.post.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {
                }
                Result.Loading -> {
                }
                is Result.Success -> {
                    binding.rcUserProfile.adapter = PostAdapter(it.data)
                }
            }
        }
    }

    private fun disable(){
        binding.btnFollow.visibility = View.GONE
        binding.btnChat.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}