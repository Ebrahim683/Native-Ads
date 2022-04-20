package com.example.nativeads.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativeads.data.local.remote.RetrofitBuilder
import com.example.nativeads.data.local.room.repository.PostsRepository
import com.example.nativeads.data.model.PostModelItem
import com.example.nativeads.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel(val repository: PostsRepository) : ViewModel() {
    suspend fun getPosts() = flow {
        emit(Response.loading(null))

        try {
            val retrofit = RetrofitBuilder().getApiService()
            val response = retrofit.getPost()
            when (response.code()) {
                200 -> {
                    val data = response.body()
                    data?.let { savePosts(it) }
                    emit(Response.success(data))
                }
                else -> {
                    emit(Response.error(response.message()))
                }
            }
        } catch (e: Exception) {
            emit(Response.error(e.localizedMessage))
        } as Unit
    }

    fun savePosts(postModelItem: List<PostModelItem>) = viewModelScope.launch(Dispatchers.Default) {
        repository.addPosts(postModelItem = postModelItem)
    }

    suspend fun showPosts() = flow {
        emit(repository.getPosts())
    }

}