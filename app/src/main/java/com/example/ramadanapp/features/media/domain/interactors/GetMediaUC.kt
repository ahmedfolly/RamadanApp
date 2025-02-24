package com.example.ramadanapp.features.media.domain.interactors

import com.example.ramadanapp.common.domain.interactors.BaseUC
import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.features.media.domain.model.Playlist
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMediaUC(private val mediaRepo: IMediaRepo) : BaseUC<String, Playlist>() {
	override suspend fun execute(param: String?): Playlist {
		//filter videos returned by category name
		val playListResponse =
			mediaRepo.getMediaData().videos.filter { video -> video.category == param }
		return mediaRepo.getMediaData().copy(videos = playListResponse)
	}
}