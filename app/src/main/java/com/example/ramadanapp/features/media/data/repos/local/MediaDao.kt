package com.example.ramadanapp.features.media.data.repos.local

import androidx.room.Dao
import androidx.room.Query
import com.example.ramadanapp.common.data.repos.local.BaseDao
import com.example.ramadanapp.features.media.data.model.entities.VideoEntity

@Dao
interface MediaDao : BaseDao<VideoEntity> {
	@Query("SELECT * FROM saved_videos WHERE :videoId LIKE videoId")
	suspend fun getVideoById(videoId:String): VideoEntity
	@Query("SELECT * FROM saved_videos")
	suspend fun getSavedVideos(): List<VideoEntity>
	@Query("DELETE FROM saved_videos WHERE :videoId LIKE videoId")
	suspend fun deleteVideoById(videoId:String)
}