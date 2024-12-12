package com.capstone.finsight.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capstone.finsight.databinding.CardSmallBinding
import com.capstone.finsight.databinding.CardStockBinding
import com.capstone.finsight.dataclass.Recommendation

class StockAdapter(private val list: List<Recommendation>) : RecyclerView.Adapter<StockAdapter.ListViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    class ListViewHolder(var binding : CardStockBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockAdapter.ListViewHolder {
        val binding = CardStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            txtNames.text = list[position].ticker
            txtStockName.text = if(list[position].price != null) list[position].price.toString() + " IDR" else "-"
            Glide.with(holder.itemView.context)
                .load(list[position].imageUrl)
                .override(50,50)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgUser)
        }
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(list[position].ticker.toString(), list[position].desc.toString())
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener  {
        fun onItemClick(stock : String , desc : String)
    }
}