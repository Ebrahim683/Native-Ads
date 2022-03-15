package com.example.nativeads.data.remote

import com.example.nativeads.model.PostModelItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
	
	@GET("/posts")
	suspend fun getPost(): Response<List<PostModelItem>>
	
}