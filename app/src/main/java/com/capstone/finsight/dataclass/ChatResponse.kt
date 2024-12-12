package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class ChatResponse(

	@field:SerializedName("chats")
	val chats: List<ChatsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class UserChatResponse(

	@field:SerializedName("users")
	val chats: List<ChatUser>? = null,

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
	val receiverUsername: String? = null,

	@field:SerializedName("receiverProfileUrl")
	val receiverUrl: String? = null,

	@field:SerializedName("senderProfileUrl")
	val senderUrl: String? = null

)

data class ChatUser(
	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("profileUrl")
	val imageUrl: String? = null,
	)
