package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(

	@field:SerializedName("comments")
	val comments: List<CommentsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CommentsItem(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@field:SerializedName("authorUid")
	val authorUid: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("postId")
	val postId: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
