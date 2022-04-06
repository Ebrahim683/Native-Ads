package com.example.nativeads.data.local.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
	
	val baseUrl = "https://jsonplaceholder.typicode.com"
	
	fun getRetrofit(): Retrofit {
		val retrofit = Retrofit.Builder()
			.baseUrl(baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
		
		return retrofit.build()
	}
	
	fun getApiService(): ApiService = getRetrofit().create(ApiService::class.java)
	
}