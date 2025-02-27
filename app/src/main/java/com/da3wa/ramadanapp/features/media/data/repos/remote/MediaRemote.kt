package com.da3wa.ramadanapp.features.media.data.repos.remote

import android.util.Log
import com.da3wa.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.da3wa.ramadanapp.features.media.data.model.dtos.PlaylistDto
import com.da3wa.ramadanapp.features.media.domain.repos.remote.IMediaRemote

class MediaRemote(private val remoteProvider: IRemoteProvider): IMediaRemote {
	override suspend fun getMedia(): PlaylistDto {
		val mediaResponse = remoteProvider.get<PlaylistDto>(endPoint =MEDIA_END_POINT, returnType = PlaylistDto::class.java)
		mediaResponse.videos?.forEach {
			Log.d("TAG", "getMedia: ${it.title}")
		}
		return mediaResponse
	}
	companion object{
		const val MEDIA_END_POINT = "MahmoudMabrok/MyDataCenter/main/ramadan.json"
	}
}