package com.capstone.finsight.network

import com.capstone.finsight.dataclass.ChatResponse
import com.capstone.finsight.dataclass.GetPostResponse
import com.capstone.finsight.dataclass.GetProfileResponse
import com.capstone.finsight.dataclass.PostCommentResponse
import com.capstone.finsight.dataclass.PostFollowingResponse
import com.capstone.finsight.dataclass.RegisterRequest
import com.capstone.finsight.dataclass.GenericResponse
import com.capstone.finsight.dataclass.NewsResponse
import com.capstone.finsight.dataclass.ResponseLogin
import com.capstone.finsight.dataclass.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //auth
    @POST("auth/login")
    suspend fun login(
        @Body login : Map<String,String>
    ): Response<ResponseLogin>

    @POST("auth/register")
    suspend fun register(
        @Body register : RegisterRequest
    ): ResponseRegister

//    profile
    @GET("users/profile/{uid}")
    suspend fun getProfile(
        @Path("uid") uid: String
    ): GetProfileResponse

    @GET("users/profile/{uid}/{followingUid}")
    suspend fun getProfileOther(
        @Path("uid") uid: String,
        @Path("followingUid") folUid: String
    ): GetProfileResponse
    @POST("users/follow")
    suspend fun followUser(
        @Body userFollow : Map<String,String>
    ): GenericResponse
    @PUT("users/update")
    suspend fun updateUser(
        @Body update : Map<String,String>,
    ): GenericResponse

    @Multipart
    @PUT("users/addphoto")
    suspend fun updatePhoto(
        @Part("uid") uid: RequestBody,
        @Part image: MultipartBody.Part
    ): GenericResponse

    //post
    @GET("posts/all/{uid}")
    suspend fun getAllPost(
        @Path("uid") userId: String
    ): GetPostResponse

    @GET("posts/followings/{uid}")
    suspend fun getFollowingPost(
        @Path("uid") userId: String
    ): PostFollowingResponse
    @GET("posts/comments/{postId}")
    suspend fun getComment(
        @Path("postId") id: String
    ): PostCommentResponse

    @Multipart
    @POST("posts/create")
    suspend fun createPost(
        @Part("uid") uid: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): GenericResponse

    @POST("posts/comments")
    suspend fun createComment(
        @Body comment : Map<String,String>
    ): GenericResponse

    @POST("posts/likes")
    suspend fun like(
        @Body like : Map<String,String>
    ): GenericResponse


    //news
    @GET("news/{date}")
    suspend fun getNews(
        @Path("date") userId: String
    ) : NewsResponse

    //chat
    @GET("users/chat/{sender}/{receiver}")
    suspend fun getChat(
        @Path("sender") userId: String,
        @Path("receiver") receiverID: String
    ) : ChatResponse

    @POST("users/chat")
    suspend fun postChat(
        @Body chat : Map<String,String>
    )
    :GenericResponse
}