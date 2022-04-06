package com.example.nativeads.data.local.room.repository

import com.example.nativeads.data.local.room.PostsDao
import com.example.nativeads.data.model.PostModelItem

class PostsRepository(var postsDao: PostsDao) {
	
	suspend fun addPosts(postModelItem: List<PostModelItem>) =
		postsDao.addPosts(postModelItem = postModelItem)
	
	suspend fun getPosts() = postsDao.getPostsDao()
	
}