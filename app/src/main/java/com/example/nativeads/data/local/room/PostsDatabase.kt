package com.example.nativeads.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nativeads.data.model.PostModelItem

private const val DATABASE_VERSION = 1

@Database(entities = [PostModelItem::class], version = DATABASE_VERSION, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {
	
	abstract fun postsDao(): PostsDao
	
	object DatabaseBuilder {
		var INSTANSE: PostsDatabase? = null
		
		fun getDatabase(context: Context): PostsDatabase {
			if (INSTANSE == null) {
				synchronized(PostsDatabase::class) {
					INSTANSE = buildRoomDb(context)
				}
			}
			return INSTANSE!!
		}
		
		private fun buildRoomDb(context: Context) =
			Room.databaseBuilder(
				context,
				PostsDatabase::class.java,
				"Posts Table"
			)
				.build()
	}
	
}