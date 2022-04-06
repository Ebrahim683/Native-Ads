package com.example.nativeads.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nativeads.data.model.PostModelItem

@Dao
interface PostsDao {
	
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun addPosts(postModelItem: List<PostModelItem>)
	
	@Query("select * from posts_table")
	suspend fun getPostsDao(): List<PostModelItem>?
	
}