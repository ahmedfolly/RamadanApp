package com.example.ramadanapp.features.media.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_videos")
data class VideoEntity(
	@PrimaryKey
	val videoId: String,
	val category: String,
	val subCategory: String,
	val title: String,
	val url: String
)
