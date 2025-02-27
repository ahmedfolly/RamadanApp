package com.da3wa.ramadanapp.features.media.domain.repos.local

import com.da3wa.ramadanapp.features.media.data.model.entities.VideoEntity

interface IMediaLocal {
	suspend fun saveVideo(videoEntity: VideoEntity)
	suspend fun getVideoById(videoId:String): VideoEntity
	suspend fun getVideos(): List<VideoEntity>
	suspend fun deleteVideo(video: VideoEntity)
	suspend fun saveLastSeenVideo(video: VideoEntity)
	suspend fun getLastSeenVideo(): VideoEntity?
}