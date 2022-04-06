package com.example.nativeads.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nativeads.data.local.room.repository.PostsRepository
import com.example.nativeads.viewmodel.MainViewModel

class PostsViewModelFactory(private val repository: PostsRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return (MainViewModel(repository)) as T
	}
}