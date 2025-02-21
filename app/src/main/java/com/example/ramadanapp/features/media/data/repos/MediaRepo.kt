package com.example.ramadanapp.features.media.data.repos

import com.example.ramadanapp.features.media.data.mappers.MediaMapper
import com.example.ramadanapp.features.media.data.mappers.VideoMapper
import com.example.ramadanapp.features.media.domain.model.Playlist
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import com.example.ramadanapp.features.media.domain.repos.local.IMediaLocal
import com.example.ramadanapp.features.media.domain.repos.remote.IMediaRemote

class MediaRepo(private val remoteIMedia: IMediaRemote, private val localIMedia: IMediaLocal): IMediaRepo {
	override suspend fun getMediaData(): Playlist {
		val playListDto = remoteIMedia.getMedia()
		return MediaMapper.fromDtoToDomain(playListDto)
	}

	override suspend fun getSavedVideoById(videoId: String): Video {
		val videoEntity = localIMedia.getVideoById(videoId)
		return VideoMapper.fromEntityToDomain(videoEntity)
	}

	override suspend fun saveVideo(video: Video) {
		val videoToSave = VideoMapper.fromDomainToEntity(video)
		localIMedia.saveVideo(videoToSave)
	}

	override suspend fun getSavedVideos(): List<Video> {
		val savedVideos = localIMedia.getVideos()
		return VideoMapper.convertEntitiesToDomains(savedVideos)
	}

	override suspend fun deleteVideo(video: Video) {
		localIMedia.deleteVideo(VideoMapper.fromDomainToEntity(video))
	}

}