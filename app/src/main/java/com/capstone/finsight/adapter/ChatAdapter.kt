package com.capstone.finsight.adapter

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capstone.finsight.R
import com.capstone.finsight.databinding.CardChatBinding
import com.capstone.finsight.databinding.CardCommentBinding
import com.capstone.finsight.dataclass.ChatsItem
import com.capstone.finsight.dataclass.CommentsItem
import com.capstone.finsight.utils.TextFormatter
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(private val chat : List<ChatsItem>, val uid : String) : RecyclerView.Adapter<ChatAdapter.ListViewHolder>() {
    class ListViewHolder(var binding : CardChatBinding) : RecyclerView.ViewHolder(binding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return chat.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding){
            txtChatBox.text = chat[position].message
            txtTime.text = TextFormatter.getChatTime(chat[position].createdAt?.seconds ?: 0,chat[position].createdAt?.nanoseconds ?: 0   )
            Glide.with(holder.itemView.context)
                .load(chat[position].senderUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgUser)

            if (chat[position].uidSender == uid) {
                imgUser.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        startToStart = ConstraintLayout.LayoutParams.UNSET
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        startToEnd = R.id.txtChatBox
                        marginEnd = (24 * holder.itemView.context.resources.displayMetrics.density).toInt()
                        marginStart = (8 * holder.itemView.context.resources.displayMetrics.density).toInt()
                    }

                txtChatBox.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    endToEnd = ConstraintLayout.LayoutParams.UNSET
                    startToEnd = R.id.txtTime
                    endToStart = R.id.imgUser
                    marginStart = (8 * holder.itemView.context.resources.displayMetrics.density).toInt()
                    marginEnd = (8 * holder.itemView.context.resources.displayMetrics.density).toInt()
                }

                txtTime.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    endToEnd = ConstraintLayout.LayoutParams.UNSET
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    marginStart = (32 * holder.itemView.context.resources.displayMetrics.density).toInt()
                }
            }
        }
    }
}