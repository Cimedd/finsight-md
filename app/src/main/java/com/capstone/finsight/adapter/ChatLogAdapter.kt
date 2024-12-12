package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardStockBinding
import com.capstone.finsight.dataclass.ChatUser

class ChatLogAdapter(private val list: List<ChatUser>) : RecyclerView.Adapter<ChatLogAdapter.ListViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    class ListViewHolder(var binding : CardStockBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLogAdapter.ListViewHolder {
        val binding = CardStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            txtNames.text = list[position].username
            txtStockName.visibility = View.GONE
            Glide.with(holder.itemView.context)
                .load(list[position].imageUrl ?: R.drawable.example1)
                .override(50,50)
                .into(imgUser)
        }
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(list[position].uid.toString())
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener  {
        fun onItemClick(id : String)
    }
}