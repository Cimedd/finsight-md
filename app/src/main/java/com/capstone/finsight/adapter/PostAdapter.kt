package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.finsight.R
import com.capstone.finsight.databinding.PostDesignBinding
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.utils.TextFormatter

class PostAdapter(private val list : List<PostsItem>) : RecyclerView.Adapter<PostAdapter.ListViewHolder>() {
    private lateinit var onLikeClickListener: OnItemClickListener
    private lateinit var onCommentClickListener: OnItemClickListener
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onProfileClickListener: OnItemClickListener
    class ListViewHolder(var binding : PostDesignBinding ) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ListViewHolder {
        val binding = PostDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            txtPostTitle.text = list[position].title
            txtPostUser.text = list[position].username
            txtDetailCard.text= list[position].content

            if(list[position].liked){
                btnLike.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            else{
                btnLike.setImageResource(R.drawable.baseline_favorite_24)
            }

            txtPostUser.setOnClickListener{onProfileClickListener.onItemClick(list[position])}

            txtPostTime.text = TextFormatter.getPostInterval(
                list[position].createdAt?.seconds ?: 0, list[position].createdAt?.nanoseconds ?: 0
            )

            txtNumLike.text = TextFormatter.formatNumber(list[position].likes ?: 0)

            btnComment.setOnClickListener{onCommentClickListener.onItemClick(list[position])}

            btnLike.setOnClickListener{
                list[position].liked = !list[position].liked
                btnLike.setImageResource(
                    if (list[position].liked) R.drawable.baseline_favorite_24
                    else R.drawable.baseline_favorite_border_24
                )
                onLikeClickListener.onItemClick(list[position])}

            holder.itemView.setOnClickListener{
                onItemClickListener.onItemClick(list[position])
            }
        }
    }

    fun setOnProfileClickCallback(onItemClickListener: OnItemClickListener) {
        this.onProfileClickListener = onItemClickListener
    }
    fun setOnCommentClickCallback(onItemClickListener: OnItemClickListener) {
        this.onLikeClickListener = onItemClickListener
    }
    fun setOnLikeClickCallback(onItemClickListener: OnItemClickListener) {
        this.onCommentClickListener = onItemClickListener
    }
    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener  {
        fun onItemClick(postItem: PostsItem)
    }
}