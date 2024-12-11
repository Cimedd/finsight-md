package com.capstone.finsight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardChatBinding
import com.capstone.finsight.databinding.CardCommentBinding
import com.capstone.finsight.dataclass.ChatsItem
import com.capstone.finsight.dataclass.CommentsItem
import com.capstone.finsight.utils.TextFormatter
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(private val chat : List<ChatsItem>, val uid : String) : RecyclerView.Adapter<ChatAdapter.ListViewHolder>() {

    class ListViewHolder(var binding : CardChatBinding) : RecyclerView.ViewHolder(binding.root){
        val imgUser: CircleImageView = itemView.findViewById(R.id.imgUser)
        val txtChatBox: TextView = itemView.findViewById(R.id.txtChatBox)
        val layoutParams = txtChatBox.layoutParams as ConstraintLayout.LayoutParams
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            txtChatBox.text = chat[position].message
            Glide.with(holder.itemView.context)
                .load(R.drawable.example1)
                .into(imgUser)
        }

//        if (chat[position].uidSender == uid) {
//
//            holder.layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
//            holder.layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
//
//            // Move the TextView to the left
//            holder.layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
//            holder.layoutParams.endToStart = R.id.imgUser
//
//            // Adjust horizontal bias to 1.0 for right alignment
//            holder.layoutParams.horizontalBias = 1.0f
//
//            // Apply the updated constraints
//            holder.txtChatBox.layoutParams = holder.layoutParams
//
//
//        } else {
//            // Default alignment (TextView on the right of the ImageView)
//
//            // Reset constraints to default
//            holder.layoutParams.startToEnd = R.id.imgUser
//            holder.layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
//
//            // Align TextView to left
//            holder.layoutParams.horizontalBias = 0.0f
//
//            // Apply the updated constraints
//            holder.txtChatBox.layoutParams = holder.layoutParams
//        }
    }

    override fun getItemCount(): Int {
        return chat.size
    }
}