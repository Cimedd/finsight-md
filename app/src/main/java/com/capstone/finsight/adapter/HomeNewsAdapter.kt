package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardNewsBinding
import com.capstone.finsight.databinding.NewsHomeBinding
import com.capstone.finsight.dataclass.NewsItem

class HomeNewsAdapter(private val list : List<NewsItem>) : RecyclerView.Adapter<HomeNewsAdapter.ListViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    class ListViewHolder(var binding : NewsHomeBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = NewsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            val image = list[position].imgUrl ?: R.drawable.example1
            Glide.with(holder.itemView.context)
                .load(image)
                .transform(RoundedCorners(20))
                .into(imageView7)
            txtNewsTitleHome.text = list[position].title ?: "NEWS TITLE"
            txtCodeHome.text = list[position].code ?: ""
            txtNewsDateHome.text = list[position].dateText ?: "00-00-0000"
        }

        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(list[position])
        }
    }

    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    interface OnItemClickListener  {
        fun onItemClick(news : NewsItem)
    }
}