package com.capstone.finsight.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.finsight.dataclass.CommentsItem
import com.capstone.finsight.dataclass.NewsItem
import com.capstone.finsight.dataclass.PostsItem
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch
import java.io.File

class PostViewModel(private val repo : PostRepo): ViewModel() {

    private val _post = MutableLiveData<Result<List<PostsItem>>>()
    val post: LiveData<Result<List<PostsItem>>> = _post

    private val _news = MutableLiveData<Result<List<NewsItem>>>()
    val news : LiveData<Result<List<NewsItem>>> = _news

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
       _post.value = Result.Loading
        try
        {
            val response = repo.getFollowPost(uid)
            val posts = response.posts?.filterNotNull() ?: emptyList()
            _post.value = Result.Success(posts)
        }
        catch (e:Exception){
            _post.value = Result.Error( e.message ?: "Unknown error")
        }
    }

    fun getUserPost(posts: List<PostsItem>){
        _post.value = Result.Loading
        try
        {
            _post.value = Result.Success(posts)
        }
        catch (e:Exception){
            _post.value = Result.Error( e.message ?: "Unknown error")
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

    fun getNews(date: String){
        viewModelScope.launch {
            _news.value = Result.Loading
            try {
                val response = repo.getNews(date)
                val news = response.news?.filterNotNull() ?: emptyList()
                _news.value = Result.Success(news)
            } catch (e: Exception) {
                _news.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun createPost(id:String, title:String, content:String, image : File? = null) = liveData{
        emit(Result.Loading)
        try {
            val response = repo.createPost(id,title, content, image)
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

    fun postLike(uid: String, postId : String){
        viewModelScope.launch {
            repo.likePost(uid, postId)
        }
    }

}