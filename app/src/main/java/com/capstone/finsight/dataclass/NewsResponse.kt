package com.capstone.finsight.dataclass

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("allNewsContent")
	val allNewsContent: AllNewsContent? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class NewsItem(

	@field:SerializedName("dateText")
	val dateText: String? = null,

	@field:SerializedName("timeAgo")
	val timeAgo: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("nanoSeconds")
	val nanoSeconds: String? = null,

	@field:SerializedName("percentageChange")
	val percentageChange: String? = null,

	@field:SerializedName("publisherAuthor")
	val publisherAuthor: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("dateAttr")
	val dateAttr: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)

data class AllNewsContent(

	@field:SerializedName("AMZN")
	val aMZN: List<NewsItem?>? = null
)
