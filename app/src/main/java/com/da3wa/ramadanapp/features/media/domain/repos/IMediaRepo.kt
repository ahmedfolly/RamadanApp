package com.da3wa.ramadanapp.features.media.domain.repos

import com.da3wa.ramadanapp.features.media.domain.model.Playlist
import com.da3wa.ramadanapp.features.media.domain.model.Video

interface IMediaRepo {
	suspend fun getMediaData(): Playlist
	suspend fun getSavedVideoById(videoId:String): Video
	suspend fun saveVideo(video: Video)
	suspend fun getSavedVideos(): List<Video>
	suspend fun deleteVideo(video: Video)
	suspend fun getLastSeenVideo(): Video
	suspend fun saveLastSeenVideo(video: Video)
}