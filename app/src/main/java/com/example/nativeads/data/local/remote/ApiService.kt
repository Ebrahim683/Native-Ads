package com.example.nativeads.data.local.remote

import com.example.nativeads.data.model.PostModelItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
	
	@GET("/posts")
	suspend fun getPost(): Response<List<PostModelItem>>
	
}