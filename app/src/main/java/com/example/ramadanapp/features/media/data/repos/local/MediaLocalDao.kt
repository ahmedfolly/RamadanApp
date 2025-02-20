package com.example.ramadanapp.features.media.data.repos.local

import com.example.ramadanapp.features.media.data.model.entities.VideoEntity
import com.example.ramadanapp.features.media.domain.repos.local.IMediaLocal

class MediaLocalDao(private val mediaDao: MediaDao): IMediaLocal {
	override suspend fun saveVideo(videoEntity: VideoEntity) {
		mediaDao.insert(videoEntity)
	}

	override suspend fun getVideoById(videoId: String): VideoEntity {
		return mediaDao.getVideoById(videoId)
	}

	override suspend fun getVideos(): List<VideoEntity> {
		return mediaDao.getSavedVideos()
	}

	override suspend fun deleteVideo(video: VideoEntity) {
		mediaDao.deleteVideoById(video.videoId)
	}
}