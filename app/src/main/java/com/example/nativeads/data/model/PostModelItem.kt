package com.example.nativeads.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts_table")
data class PostModelItem(
	@PrimaryKey
	@ColumnInfo(name = "id")
	@SerializedName("id")
	val id: Int?, // 100
	@ColumnInfo(name = "body")
	@SerializedName("body")
	val body: String?, // cupiditate quo est a modi nesciunt solutaipsa voluptas error itaque dicta inautem qui minus magnam et distinctio eumaccusamus ratione error aut
	@ColumnInfo(name = "title")
	@SerializedName("title")
	val title: String?, // at nam consequatur ea labore ea harum
	@ColumnInfo(name = "userId")
	@SerializedName("userId")
	val userId: Int? // 10
)