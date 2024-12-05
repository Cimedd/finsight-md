package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.capstone.finsight.network.Result
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
        binding.imageView2.setImageResource(R.drawable.login_gradient)
        binding.rcProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcProfile.setHasFixedSize(true)
        lifecycleScope.launch {
            val uid = settingVM.getUser()
            val isUser = arguments?.getString("UID") ?: uid
            setUI(isUser == uid)
            profileVM.getProfile(isUser).observe(requireActivity()){
                when(it){
                    is Result.Error -> {

                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        binding.imageView2.setImageResource(R.drawable.login_gradient)
                        binding.txtProfileUname.text = it.data.user?.username ?: "User not found"
                        binding.txtProfileRisk.text = it.data.user?.profileRisk ?: "-"
                    }
                }
            }
            postVM.post.observe(requireActivity()){
                when(it){
                    is Result.Error -> {

                    }
                    Result.Loading -> {

                    }
                    is Result.Success ->{
                        binding.rcProfile.adapter = PostAdapter(it.data)
                    }
                }
            }
            postVM.getAllPost(isUser)
        }
    }

    private fun setUI(isUser : Boolean){
        if(!isUser){
            binding.btnEditProfile.visibility = View.GONE
            binding.btnFollow.setOnClickListener {
                profileVM.followUser("","").observe(requireActivity()){
                    when(it){
                        is Result.Error -> TODO()
                        Result.Loading -> TODO()
                        is Result.Success -> TODO()
                    }
                }
            }
        }
        else{
            binding.btnFollow.visibility = View.GONE
            binding.btnEditProfile.setOnClickListener {
                Toast.makeText(requireActivity(), "FOLLOW", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_itemProfile_to_itemSetting)
            }
        }
    }

    private fun setData(){

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