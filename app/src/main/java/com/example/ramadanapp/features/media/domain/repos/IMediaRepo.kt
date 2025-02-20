package com.example.ramadanapp.features.media.domain.repos

import com.example.ramadanapp.features.media.domain.model.Playlist
import com.example.ramadanapp.features.media.domain.model.Video

interface IMediaRepo {
	suspend fun getMediaData(): Playlist
	suspend fun getSavedVideoById(videoId:String): Video
	suspend fun saveVideo(video: Video)
	suspend fun getSavedVideos(): List<Video>
	suspend fun deleteVideo(video: Video)
}