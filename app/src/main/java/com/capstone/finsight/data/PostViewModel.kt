package com.capstone.finsight.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.finsight.dataclass.CommentsItem
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class PostViewModel(private val repo : PostRepo): ViewModel() {

    private val _post = MutableLiveData<Result<List<PostsItem>>>()
    val post: LiveData<Result<List<PostsItem>>> = _post

    private val _postFol = MutableLiveData<Result<List<PostsItem>>>()
    val postFol : LiveData<Result<List<PostsItem>>> = _postFol

    private val _comment = MutableLiveData<Result<List<CommentsItem>>>()
    val comment : LiveData<Result<List<CommentsItem>>> = _comment
    suspend fun getAllPost(uid: String){
        _post.value = Result.Loading
        try
        {
            val response = repo.getAllPost(uid)
            val posts = response.posts?.filterNotNull() ?: emptyList()
            _post.value = Result.Success(posts)
        }
        catch (e:Exception){
            _post.value = Result.Error( e.message ?: "Unknown error")
        }
    }

   suspend fun getFollowingPost(uid: String){
        _postFol.value = Result.Loading
        try
        {
            val response = repo.getFollowPost(uid)
            val posts = response.posts?.filterNotNull() ?: emptyList()
            _postFol.value = Result.Success(posts)
        }
        catch (e:Exception){
            _postFol.value = Result.Error( e.message ?: "Unknown error")
        }
    }

    fun getPostComments(postId: String){
        viewModelScope.launch {
            _comment.value = Result.Loading
            try
            {
                val response = repo.getPostComment(postId)
                val posts = response.comments?.filterNotNull() ?: emptyList()
                _comment.value = Result.Success(posts)
            }
            catch (e:Exception){
                _comment.value = Result.Error( e.message ?: "Unknown error")
            }
        }
    }

    fun createPost(id:String, title:String, content:String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.createPost(id,title, content)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun createComment(id:String, postId:String, content:String ) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.createComment(id, postId, content)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

    fun postLike(uid: String, postId : String) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.likePost(uid, postId)
            if(response.status == "success"){
                emit(Result.Success(response))
            }
        }
        catch (e : Exception){
            emit(Result.Error( e.message ?: "Unknown error"))
        }
    }

}