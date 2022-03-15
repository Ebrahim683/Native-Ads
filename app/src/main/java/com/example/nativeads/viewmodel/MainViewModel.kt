package com.example.nativeads.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nativeads.data.remote.RetrofitBuilder
import com.example.nativeads.utils.Response
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {
	
	fun getPosts() = flow {
		emit(Response.loading(null))
		
		try {
			val retrofit = RetrofitBuilder().getApiService()
			val response = retrofit.getPost()
			when (response.code()) {
				200 -> {
					emit(Response.success(response.body()))
				}
				else -> {
					emit(Response.error(response.message()))
				}
			}
		} catch (e: Exception) {
			emit(Response.error(e.localizedMessage))
		}
	}
	
}