package com.capstone.finsight.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.capstone.finsight.R
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentAddPostBinding
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding
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
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitlePost.focusable
        lifecycleScope.launch {
            val id = settingVM.getUser()
            binding.btnCreate.setOnClickListener {
                val title = binding.txtTitlePost.text.toString()
                val content = binding.txtPostContent.text.toString()
                Log.d("COntent", id + title + content)
                postVM.createPost(id, title, content).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Error -> {}
                        Result.Loading -> {}
                        is Result.Success -> {
                            Toast.makeText(requireActivity(), "ADDED", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }
    companion object {
    }
}