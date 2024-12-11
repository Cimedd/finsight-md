package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class ChatResponse(

	@field:SerializedName("chats")
	val chats: List<ChatsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ChatsItem(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@field:SerializedName("uidSender")
	val uidSender: String? = null,

	@field:SerializedName("uidReceiver")
	val uidReceiver: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("senderUsername")
	val senderUsername: String? = null,

	@field:SerializedName("receiverUsername")
	val receiverUsername: String? = null
)
