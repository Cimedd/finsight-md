package com.capstone.finsight.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponse(

	@field:SerializedName("news")
	val news: List<NewsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class NewsItem(

	@field:SerializedName("dateText")
	val dateText: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("imgUrl")
	val imgUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("publisherAuthor")
	val publisherAuthor: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
