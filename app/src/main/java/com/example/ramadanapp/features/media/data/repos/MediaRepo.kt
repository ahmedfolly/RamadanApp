package com.example.ramadanapp.features.media.data.repos

import android.util.Log
import com.example.ramadanapp.features.media.domain.model.Playlist
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import com.example.ramadanapp.features.media.data.mappers.MediaMapper.convertDtoToDomains
import com.example.ramadanapp.features.media.data.mappers.MediaMapper.fromDtoToDomain
import com.example.ramadanapp.features.media.data.model.dtos.PlaylistDto
import com.example.ramadanapp.features.media.domain.repos.remote.IMediaRemote

class MediaRepo(private val remoteIMediaRepo: IMediaRemote): IMediaRepo {
	override suspend fun getMediaData(): Playlist {
		val playListDto = remoteIMediaRepo.getMedia()
		Log.d("TAG", "getMediaData: ${playListDto.videos}")
		return playListDto.fromDtoToDomain()
	}

}