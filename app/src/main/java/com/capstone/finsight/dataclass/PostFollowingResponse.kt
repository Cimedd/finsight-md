package com.capstone.finsight.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PostFollowingResponse(

	@field:SerializedName("posts")
	val posts: List<PostsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class GetPostResponse(

	@field:SerializedName("posts")
	val posts: List<PostsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)
@Parcelize
data class CreatedAt(

	@field:SerializedName("seconds")
	val seconds: Long? = null,

	@field:SerializedName("nanoseconds")
	val nanoseconds: Int? = null
): Parcelable

@Parcelize
data class PostsItem(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@field:SerializedName("authorUid")
	val authorUid: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("likes")
	val likes: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("liked")
	var liked: Boolean = false,

	@field:SerializedName("postUrl")
	var postUrl: String? = null,

	@field:SerializedName("profileUrl")
	var profileUrl: String? = null,

): Parcelable
