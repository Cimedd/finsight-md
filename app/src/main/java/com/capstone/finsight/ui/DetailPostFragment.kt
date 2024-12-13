package com.capstone.finsight.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.finsight.R
import com.capstone.finsight.adapter.CommentAdapter
import com.capstone.finsight.data.PostVMF
import com.capstone.finsight.data.PostViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.FragmentDetailPostBinding
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.TextFormatter
import kotlinx.coroutines.launch


class DetailPostFragment : Fragment() {
    private lateinit var binding: FragmentDetailPostBinding
    private val postVM by viewModels<PostViewModel>{
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
    ): View {
        binding = FragmentDetailPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcComment.layoutManager = LinearLayoutManager(requireActivity())
        val postItem: PostsItem? = arguments?.getParcelable("PostItem")
        val postID = postItem?.id ?: ""
        var userID = ""

        binding.txtDetailTitle.text = postItem?.title
        binding.txtDetailUser.text = postItem?.username
        binding.txtDetailContent.text = postItem?.content
        binding.txtPostedOn.text = TextFormatter.getDateTime(postItem?.createdAt?.seconds!!, postItem.createdAt.nanoseconds!! )

        if(postItem.postUrl.isNullOrBlank()){
            binding.imageView5.visibility = View.GONE
        }
        else{
            Glide.with(requireActivity())
                .load(postItem.postUrl)
                .override(360,220)
                .transform(RoundedCorners(10))
                .into(binding.imageView5)
        }

        val profile = if (postItem.profileUrl.isNullOrBlank()) R.drawable.example1 else postItem.profileUrl
        Glide.with(requireActivity())
            .load(profile)
            .transform(RoundedCorners(10))
            .into(binding.imgUser2)

        Glide.with(requireActivity())
            .load(profile)
            .transform(RoundedCorners(10))
            .into(binding.imgUser)

        postVM.comment.observe(viewLifecycleOwner){
            when(it){
                is Result.Error ->{
                }
                Result.Loading ->{
                    binding.txtSendChat.isEnabled = false
                }
                is Result.Success ->{
                    binding.txtSendChat.isEnabled = true
                    binding.txtAddComments.setText("")
                    binding.rcComment.adapter = CommentAdapter(it.data)
                }
            }
        }

        postVM.getPostComments(postID)

        lifecycleScope.launch {
            userID = settingVM.getUser()
        }

        binding.txtAddComments.setOnClickListener {
            postVM.createComment(userID, postID, binding.txtAddComments.text.toString()).observe(viewLifecycleOwner){
                when(it){
                    is Result.Error ->{
                        binding.rcComment.alpha = 1f
                        binding.rcComment.isEnabled = true
                    }
                    Result.Loading ->{
                        binding.rcComment.alpha = 0.5f
                        binding.rcComment.isEnabled = false
                    }
                    is Result.Success ->{
                        binding.rcComment.alpha = 1f
                        binding.rcComment.isEnabled = true
                        postVM.getPostComments(postID)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailPostFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}