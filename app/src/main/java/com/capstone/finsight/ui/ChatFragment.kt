package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.finsight.R
import com.capstone.finsight.adapter.ChatAdapter
import com.capstone.finsight.data.ProfileVMF
import com.capstone.finsight.data.ProfileViewModel
import com.capstone.finsight.databinding.CardChatBinding
import com.capstone.finsight.databinding.FragmentChatBinding
import com.capstone.finsight.network.Result

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    private val profileVM by viewModels<ProfileViewModel> {
        ProfileVMF.getInstance(requireActivity())
    }
    private var sender = ""
    private var receiver = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sender = arguments?.getString("USERID") ?: ""
        receiver = arguments?.getString("RECEIVED") ?: ""
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.setHasFixedSize(true)

        profileVM.chat.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    binding.recyclerView.adapter = ChatAdapter(it.data, sender)
                }
            }
        }
        profileVM.getChat(sender,receiver)

        binding.txtSendChat.setOnClickListener {
            val msg = binding.txtChat.text.toString()
            profileVM.postChat(sender,receiver,msg).observe(viewLifecycleOwner){
                when(it){
                    is Result.Error -> {
                        Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
                        binding.txtSendChat.isEnabled = true
                    }
                    Result.Loading -> {
                        binding.txtSendChat.isEnabled = false
                    }
                    is Result.Success -> {
                        profileVM.getChat(sender,receiver)
                        binding.txtChat.text = null
                        binding.txtSendChat.isEnabled = true
                    }
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}