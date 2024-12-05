package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    class ListViewHolder(var binding : CardNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            imageView3.setImageResource(R.drawable.example1)
        }
    }
}