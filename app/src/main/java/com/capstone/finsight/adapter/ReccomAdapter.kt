package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.finsight.databinding.CardReccomendBinding
import com.capstone.finsight.databinding.CardSmallBinding
import com.capstone.finsight.dataclass.Recommendation

class ReccomAdapter(private val list: List<Recommendation>) : RecyclerView.Adapter<ReccomAdapter.ListViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    class ListViewHolder(var binding : CardReccomendBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReccomAdapter.ListViewHolder {
        val binding = CardReccomendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            textView8.text = list[0].ticker
            Glide.with(holder.itemView.context)
                .load(list[0].imageUrl)
                .into(imageView6)
        }
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(list[0].ticker)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    interface OnItemClickListener  {
        fun onItemClick(stock : String)
    }
}