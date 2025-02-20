package com.example.ramadanapp.features.media.domain.repos.local

import com.example.ramadanapp.features.media.data.model.entities.VideoEntity

interface IMediaLocal {
	suspend fun saveVideo(videoEntity: VideoEntity)
	suspend fun getVideoById(videoId:String): VideoEntity
	suspend fun getVideos(): List<VideoEntity>
	suspend fun deleteVideo(video: VideoEntity)
}