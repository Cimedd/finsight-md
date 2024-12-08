package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardNewsBinding
import com.capstone.finsight.dataclass.NewsItem

class NewsAdapter(private val list : List<NewsItem>) : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding : CardNewsBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(holder.itemView.context)
                .load(list[position].dateText)
                .transform(RoundedCorners(8))
                .into(imageView3)
            imageView3.setImageResource(R.drawable.example1)
            txtNewsTitle.text = list[position].title
            txtCode.text = list[position].code

        }
    }
}