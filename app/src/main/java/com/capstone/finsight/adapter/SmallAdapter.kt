package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.finsight.databinding.CardSmallBinding

class SmallAdapter() : RecyclerView.Adapter<SmallAdapter.ListViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    class ListViewHolder(var binding : CardSmallBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallAdapter.ListViewHolder {
        val binding = CardSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    interface OnItemClickListener  {
        fun onItemClick()
    }
}