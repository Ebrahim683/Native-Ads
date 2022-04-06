package com.example.nativeads

import android.app.Application
import com.example.nativeads.data.local.room.PostsDatabase
import com.example.nativeads.data.local.room.repository.PostsRepository

class PostsApplication : Application() {
	
	override fun onCreate() {
		super.onCreate()
		
	}
	
	val database by lazy {
		PostsDatabase.DatabaseBuilder.getDatabase(this)
	}
	
	val repository by lazy {
		PostsRepository(database.postsDao())
	}
	
}