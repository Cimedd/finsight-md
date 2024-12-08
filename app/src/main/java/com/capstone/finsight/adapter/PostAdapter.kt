package com.capstone.finsight.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
                btnLike.setImageResource(R.drawable.baseline_favorite_24)
            }
            else{
                btnLike.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            txtPostUser.setOnClickListener{onProfileClickListener.onItemClick(list[position])}

            txtPostTime.text = TextFormatter.getPostInterval(
                list[position].createdAt?.seconds ?: 0, list[position].createdAt?.nanoseconds ?: 0
            )

            val profile = if (list[position].profileUrl.isNullOrBlank()) R.drawable.example1 else list[position].profileUrl
            Glide.with(holder.itemView.context)
                .load(profile)
                .into(imgUser)

            Log.d("URL", list[position].profileUrl.toString())
            Log.d("URL", list[position].postUrl.toString())
            if(list[position].postUrl.isNullOrBlank()){
                imgPost.visibility = View.GONE
            }
            else{
                Glide.with(holder.itemView.context)
                    .load(list[position].postUrl)
                    .override(360,220)
                    .transform(RoundedCorners(10))
                    .into(imgPost)
            }

            txtNumLike.text = TextFormatter.formatNumber(list[position].likes ?: 0)

            btnComment.setOnClickListener{onCommentClickListener.onItemClick(list[position])}

            btnLike.setOnClickListener{
                list[position].liked = !list[position].liked
                if(list[position].liked)
                {
                    val num = list[position].likes?.plus(1)
                    txtNumLike.text = TextFormatter.formatNumber(num ?: 0)
                    btnLike.setImageResource(R.drawable.baseline_favorite_24)
                }
                else{
                    val num = txtNumLike.text.toString().toInt() - 1
                    txtNumLike.text = TextFormatter.formatNumber(num )
                    btnLike.setImageResource(R.drawable.baseline_favorite_border_24)
                }
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
        this.onCommentClickListener = onItemClickListener
    }
    fun setOnLikeClickCallback(onItemClickListener: OnItemClickListener) {
         this.onLikeClickListener  = onItemClickListener
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