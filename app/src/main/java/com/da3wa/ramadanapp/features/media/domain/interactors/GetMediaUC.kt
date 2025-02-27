package com.da3wa.ramadanapp.features.media.domain.interactors

import com.da3wa.ramadanapp.common.domain.interactors.BaseUC
import com.da3wa.ramadanapp.features.media.domain.model.Playlist
import com.da3wa.ramadanapp.features.media.domain.repos.IMediaRepo

class GetMediaUC(private val mediaRepo: IMediaRepo) : BaseUC<String, Playlist>() {
	override suspend fun execute(param: String?): Playlist {
		//filter videos returned by category name
		val playListResponse =
			mediaRepo.getMediaData().videos.filter { video -> video.category == param }
		return mediaRepo.getMediaData().copy(videos = playListResponse)
	}
}