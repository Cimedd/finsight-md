package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.finsight.databinding.CardSmallBinding

class SmallAdapter() : RecyclerView.Adapter<SmallAdapter.ListViewHolder>() {

    class ListViewHolder(var binding : CardSmallBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallAdapter.ListViewHolder {
        val binding = CardSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 5
    }

}