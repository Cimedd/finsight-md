package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardCommentBinding
import com.capstone.finsight.dataclass.CommentsItem
import com.capstone.finsight.utils.TextFormatter

class CommentAdapter(private val comments : List<CommentsItem>) : RecyclerView.Adapter<CommentAdapter.ListViewHolder>() {

    class ListViewHolder(var binding : CardCommentBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            txtComment.text = comments[position].content
            txtCommentUser.text= comments[position].username
            txtCommentDate.text = TextFormatter.getPostInterval(
                comments[position].createdAt?.seconds ?: 0, comments[position].createdAt?.nanoseconds ?: 0)
            val profile = if(comments[position].profileUrl.isNullOrBlank()) R.drawable.example1 else comments[position].profileUrl
            Glide.with(holder.itemView.context)
                .load(profile)
                .into(imgUser)
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}