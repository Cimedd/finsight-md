package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.finsight.databinding.CardCommentBinding
import com.capstone.finsight.dataclass.CommentsItem
import com.capstone.finsight.utils.TextFormatter

class CommentAdapter(private val comments : List<CommentsItem>) : RecyclerView.Adapter<CommentAdapter.ListViewHolder>() {

    class ListViewHolder(var binding : CardCommentBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ListViewHolder {
        val binding = CardCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentAdapter.ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ListViewHolder, position: Int) {
        with(holder.binding){
            txtComment.text = comments[position].content
            txtCommentUser.text= comments[position].username
            txtCommentDate.text = TextFormatter.getPostInterval(
                comments[position].createdAt?.seconds ?: 0, comments[position].createdAt?.nanoseconds ?: 0)
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}