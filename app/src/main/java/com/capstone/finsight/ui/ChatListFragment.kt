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
import com.capstone.finsight.adapter.ChatLogAdapter
import com.capstone.finsight.data.ProfileVMF
import com.capstone.finsight.data.ProfileViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentChatListBinding
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class ChatListFragment : Fragment() {
    private lateinit var binding : FragmentChatListBinding

    private val settingVM by viewModels<SettingViewModel> {
        SettingVMF.getInstance(requireActivity())
    }
    private val profileVM by viewModels<ProfileViewModel> {
        ProfileVMF.getInstance(requireActivity())
    }
    private var uid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentChatListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcChatList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rcChatList.setHasFixedSize(true)
        lifecycleScope.launch {
            uid = settingVM.getUser()
            profileVM.getChatLog(uid).observe(viewLifecycleOwner){
                when(it){
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        val data = it.data.chats ?: listOf()
                        val adapter = ChatLogAdapter(data)
                        binding.rcChatList.adapter = adapter
                        adapter.setOnItemClickCallback(object : ChatLogAdapter.OnItemClickListener {
                            override fun onItemClick(id: String) {
                                val bundle = Bundle()
                                bundle.putString("USERID", uid)
                                bundle.putString("RECEIVED", id)
                                findNavController().navigate(R.id.action_chatListFragment_to_chatFragment, bundle)
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
            ChatListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}