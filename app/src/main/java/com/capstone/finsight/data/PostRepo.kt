package com.capstone.finsight.data

import com.capstone.finsight.dataclass.GetPostResponse
import com.capstone.finsight.dataclass.PostCommentResponse
import com.capstone.finsight.dataclass.PostFollowingResponse
import com.capstone.finsight.dataclass.GenericResponse
import com.capstone.finsight.network.ApiService

class PostRepo(private val api : ApiService) {

    suspend fun getAllPost(uid : String): GetPostResponse {
        return api.getAllPost(uid)
    }
    suspend fun getFollowPost(uid:String): PostFollowingResponse{
        return api.getFollowingPost(uid)
    }
    suspend fun getPostComment(id:String): PostCommentResponse{
        return api.getComment(id)
    }
    suspend fun createPost(uid:String, title: String, content:String): GenericResponse{
        val body = mapOf("uid" to uid, "title" to title, "content" to content)
        return api.createPost(body)
    }
    suspend fun createComment(id: String, postID:String, content: String):GenericResponse{
        val body = mapOf("postId" to postID, "uid" to id, "content" to content)
        return api.createComment(body)
    }
    suspend fun  likePost(id: String,postID: String): GenericResponse {
        val body = mapOf("uid" to id, "postId" to postID)
        return api.like(body)
    }
}