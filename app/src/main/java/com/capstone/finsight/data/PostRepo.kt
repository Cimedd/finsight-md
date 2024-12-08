package com.capstone.finsight.data

import com.capstone.finsight.dataclass.GetPostResponse
import com.capstone.finsight.dataclass.PostCommentResponse
import com.capstone.finsight.dataclass.PostFollowingResponse
import com.capstone.finsight.dataclass.GenericResponse
import com.capstone.finsight.dataclass.NewsResponse
import com.capstone.finsight.network.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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
    suspend fun getNews() : NewsResponse {
        return api.getNews()
    }
    suspend fun createPost(uid:String, title: String, content:String, image: File? = null): GenericResponse{
        val bodyUid = uid.toRequestBody("text/plain".toMediaType())
        val titleBody = title.toRequestBody("text/plain".toMediaType())
        val contentBody =  content.toRequestBody("text/plain".toMediaType())
        val imageBody = image?.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = if(imageBody!= null)
            MultipartBody.Part.createFormData(
            "image",
            image.name,
            imageBody
        ) else null
        return api.createPost(bodyUid, titleBody, contentBody, multipartBody)
    }
    suspend fun createComment(id: String, postID:String, content: String):GenericResponse{
        val body = mapOf("postId" to postID, "uid" to id, "content" to content)
        return api.createComment(body)
    }
    suspend fun likePost(id: String,postID: String): GenericResponse {
        val body = mapOf("uid" to id, "postId" to postID)
        return api.like(body)
    }

    suspend fun addPhoto(){

    }
}